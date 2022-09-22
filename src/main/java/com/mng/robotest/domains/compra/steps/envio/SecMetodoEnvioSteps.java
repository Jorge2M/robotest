package com.mng.robotest.domains.compra.steps.envio;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.compra.pageobject.PageCheckoutWrapper;
import com.mng.robotest.domains.compra.pageobject.envio.ModalDroppoints;
import com.mng.robotest.domains.compra.pageobject.envio.SecMetodoEnvioDesktop;
import com.mng.robotest.domains.compra.pageobject.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.domains.compra.steps.Page1EnvioCheckoutMobilSteps;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.datastored.DataPago;

public class SecMetodoEnvioSteps extends StepBase {

	private final SecMetodoEnvioDesktop secMetodoEnvioDesktop = new SecMetodoEnvioDesktop();
	private final ModalDroppoints modalDroppoints = new ModalDroppoints();
	
	private final ModalDroppointsSteps modalDroppointsSteps = new ModalDroppointsSteps();
	
	@Step (
		description="<b style=\"color:blue;\">#{nombrePago}</b>:Seleccionamos el método de envío <b>#{tipoTransporte}</b>", 
		expected="Se selecciona el método de envío correctamente")
	public void selectMetodoEnvio(TipoTransporte tipoTransporte, String nombrePago, DataPago dataPago) {
		secMetodoEnvioDesktop.selectMetodo(tipoTransporte);
		if (!tipoTransporte.isEntregaDomicilio()) {
			if (modalDroppoints.isErrorMessageVisibleUntil()) {
				modalDroppoints.searchAgainByUserCp(dataPago.getDatosRegistro().get("cfCp"));
			}
		}

		validaBlockSelectedDesktop(tipoTransporte);
		if (tipoTransporte.isEntregaDomicilio()) {
			modalDroppointsSteps.validaIsNotVisible();
		} else {
			modalDroppointsSteps.validaIsVisible();
		}
	}
	
	@Validation
	public ChecksTM validaBlockSelectedDesktop(TipoTransporte tipoTransporte) {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 5;
	  	checks.add(
			"Desaparece la capa de Loading  (lo esperamos hasta " + seconds + " segundos)",
			new PageCheckoutWrapper().waitUntilNoDivLoading(seconds), State.Warn);
	  	
	  	checks.add(
			"Queda seleccionado el bloque correspondiete a <b>" + tipoTransporte + "</b>",
			secMetodoEnvioDesktop.isBlockSelectedUntil(tipoTransporte, seconds), State.Warn);
	  	
	  	return checks;
	}
	
	@Step (
		description="Seleccionamos la <b>#{posicion}a<b> franja horaria del envío \"Urgente - Horario personalizado\"</b>", 
		expected="La franja horaria se selecciona correctamente")
	public void selectFranjaHorariaUrgente(int posicion) {
		secMetodoEnvioDesktop.selectFranjaHorariaUrgente(posicion);
	}
	

	public void selectMetodoEnvio(DataPago dataPago, String nombrePago) throws Exception {
		alterTypeEnviosAccordingContext(dataPago);
		Pago pago = dataPago.getDataPedido().getPago();
		TipoTransporte tipoTransporte = pago.getTipoEnvioType(app);
		switch (channel) {
		case desktop:
		case tablet:
			selectMetodoEnvio(tipoTransporte, nombrePago, dataPago);
			break;
		case mobile:
			new Page1EnvioCheckoutMobilSteps().selectMetodoEnvio(tipoTransporte, nombrePago, dataPago);
			break;
		}
	}
	
	public boolean fluxSelectEnvio(DataPago dataPago) throws Exception {
		boolean pagoPintado = false;
		Pago pago = dataPago.getDataPedido().getPago();
		if (pago.getTipoEnvio(app)!=null) {
			String nombrePago = dataPago.getDataPedido().getPago().getNombre(channel, app);
			selectMetodoEnvio(dataPago, nombrePago);
			pagoPintado = true;
			TipoTransporte tipoEnvio = pago.getTipoEnvioType(app);
			if (tipoEnvio.isDroppoint()) {
				modalDroppointsSteps.fluxSelectDroppoint(dataPago);
			}
			if (tipoEnvio.isFranjaHoraria()) {
				selectFranjaHorariaUrgente();
			}
		}		
		
		return pagoPintado;
	}
	
	public void selectFranjaHorariaUrgente() {
		switch (channel) {
		case desktop:
		case tablet:
			selectFranjaHorariaUrgente(1);
			break;
		case mobile:
			new Page1EnvioCheckoutMobilSteps().selectFranjaHorariaUrgente(1);
		}	
	}
	
	/**
	 * No tenemos posibilidad sencilla de determinar si nos aparecerá el envío de tipo "Urgente" o "SendayNextday" así que si no encontramos uno ejecutamos la prueba con el otro
	 */
	private void alterTypeEnviosAccordingContext(DataPago dataPago) {
		alterTypeEnviosTiendaStandar(dataPago);
		Pago pago = dataPago.getDataPedido().getPago();
		alterTypeEnviosNextaySomedayUntilExists(pago);
	}
	
	private void alterTypeEnviosTiendaStandar(DataPago dataPago) {
		//If employee and Spain not "Recogida en Tienda"
		Pago pago = dataPago.getDataPedido().getPago();
		if (dataPago.getFTCkout().userIsEmployee && 
			"001".compareTo(dataPago.getDataPedido().getCodigoPais())==0) {
			if (pago.getTipoEnvioType(app)==TipoTransporte.TIENDA) {
				pago.setTipoEnvioShop(TipoTransporte.STANDARD);
				pago.setTipoEnvioOutlet(TipoTransporte.STANDARD);
				Log4jTM.getLogger().info("Modificado tipo de envío: " + pago.getTipoEnvioType(app) + " -> " + TipoTransporte.STANDARD);
			}
			
			//Esto no está muy claro si es correcto, pero la configuración en Manto de los transportes dice que en el caso de Outlet los 
			//empleados no tienen PickPoint así que nos ceñimos a ella.
			if (app==AppEcom.outlet &&
				pago.getTipoEnvioType(app)==TipoTransporte.ASM)
				pago.setTipoEnvioOutlet(TipoTransporte.STANDARD);
		}		
	}
	
	private void alterTypeEnviosNextaySomedayUntilExists(Pago pago) {
		//Estos tipos de pago se intercambian constantemente a nivel de configuración en la shop
		if (pago.getTipoEnvioType(app)==TipoTransporte.NEXTDAY ||
			pago.getTipoEnvioType(app)==TipoTransporte.NEXTDAY_FRANJAS ||
			pago.getTipoEnvioType(app)==TipoTransporte.SAMEDAY ||
			pago.getTipoEnvioType(app)==TipoTransporte.SAMEDAY_NEXTDAY_FRANJAS) {
			for (int i=0; i<4; i++) {
				if (new PageCheckoutWrapper().isPresentBlockMetodo(pago.getTipoEnvioType(app))) {
					break;
				}
				switch (pago.getTipoEnvioType(app)) {
				case NEXTDAY:
					pago.setTipoEnvioShop(TipoTransporte.NEXTDAY_FRANJAS);
					break;
				case NEXTDAY_FRANJAS:
					pago.setTipoEnvioShop(TipoTransporte.SAMEDAY_NEXTDAY_FRANJAS);
					break;
				case SAMEDAY_NEXTDAY_FRANJAS:
					pago.setTipoEnvioShop(TipoTransporte.SAMEDAY);
					break;
			   	case SAMEDAY:
			   	default:
					pago.setTipoEnvioShop(TipoTransporte.NEXTDAY_FRANJAS);
					break;					
				}
			}
		}
	}
}
