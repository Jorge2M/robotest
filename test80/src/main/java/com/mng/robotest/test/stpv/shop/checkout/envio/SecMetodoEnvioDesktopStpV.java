package com.mng.robotest.test.stpv.shop.checkout.envio;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test.pageobject.shop.checkout.envio.ModalDroppoints;
import com.mng.robotest.test.pageobject.shop.checkout.envio.SecMetodoEnvioDesktop;
import com.mng.robotest.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test.stpv.shop.checkout.Page1EnvioCheckoutMobilStpV;

public class SecMetodoEnvioDesktopStpV {

	private final SecMetodoEnvioDesktop secMetodoEnvioDesktop;
	public static ModalDroppointsStpV modalDroppoints;
	
	private final WebDriver driver;
	private final Channel channel;
	private final AppEcom app;
	
	public SecMetodoEnvioDesktopStpV(Channel channel, AppEcom app, WebDriver driver) {
		this.secMetodoEnvioDesktop = new SecMetodoEnvioDesktop(driver);
		this.driver = driver;
		this.channel = channel;
		this.app = app;
	}
	
	@SuppressWarnings({ "static-access", "unused" })
	@Step (
		description="<b style=\"color:blue;\">#{nombrePago}</b>:Seleccionamos el método de envío <b>#{tipoTransporte}</b>", 
		expected="Se selecciona el método de envío correctamente")
	public void selectMetodoEnvio(TipoTransporte tipoTransporte, String nombrePago, DataCtxPago dCtxPago) {
		secMetodoEnvioDesktop.selectMetodo(tipoTransporte);
		if (!tipoTransporte.isEntregaDomicilio()) {
			if (ModalDroppoints.isErrorMessageVisibleUntil(driver)) {
				ModalDroppoints.searchAgainByUserCp(dCtxPago.getDatosRegistro().get("cfCp"), driver);
			}
		}

		validaBlockSelectedDesktop(tipoTransporte);
		if (tipoTransporte.isEntregaDomicilio()) {
			modalDroppoints.validaIsNotVisible(Channel.desktop, driver);
		} else {
			modalDroppoints.validaIsVisible(Channel.desktop, driver);
		}
	}
	
	@Validation
	public ChecksTM validaBlockSelectedDesktop(TipoTransporte tipoTransporte) {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 5;
	  	validations.add(
			"Desaparece la capa de Loading  (lo esperamos hasta " + maxSeconds + " segundos)",
			new PageCheckoutWrapper(channel, app, driver).waitUntilNoDivLoading(maxSeconds), State.Warn);
	  	validations.add(
			"Queda seleccionado el bloque correspondiete a <b>" + tipoTransporte + "</b>",
			secMetodoEnvioDesktop.isBlockSelectedUntil(tipoTransporte, maxSeconds), State.Warn);
	  	return validations;
	}
	
	@Step (
		description="Seleccionamos la <b>#{posicion}a<b> franja horaria del envío \"Urgente - Horario personalizado\"</b>", 
		expected="La franja horaria se selecciona correctamente")
	public void selectFranjaHorariaUrgente(int posicion) {
		secMetodoEnvioDesktop.selectFranjaHorariaUrgente(posicion);
	}
	

	public void selectMetodoEnvio(DataCtxPago dCtxPago, String nombrePago) throws Exception {
		alterTypeEnviosAccordingContext(dCtxPago);
		Pago pago = dCtxPago.getDataPedido().getPago();
		TipoTransporte tipoTransporte = pago.getTipoEnvioType(app);
		switch (channel) {
		case desktop:
		case tablet:
			selectMetodoEnvio(tipoTransporte, nombrePago, dCtxPago);
			break;
		case mobile:
			new Page1EnvioCheckoutMobilStpV(driver).selectMetodoEnvio(tipoTransporte, nombrePago, dCtxPago);
			break;
		}
	}
	
	public boolean fluxSelectEnvio(DataCtxPago dCtxPago, DataCtxShop dCtxSh) throws Exception {
		boolean pagoPintado = false;
		Pago pago = dCtxPago.getDataPedido().getPago();
		if (pago.getTipoEnvio(dCtxSh.appE)!=null) {
			String nombrePago = dCtxPago.getDataPedido().getPago().getNombre(dCtxSh.channel, dCtxSh.appE);
			selectMetodoEnvio(dCtxPago, nombrePago);
			pagoPintado = true;
			TipoTransporte tipoEnvio = pago.getTipoEnvioType(dCtxSh.appE);
			if (tipoEnvio.isDroppoint()) {
				ModalDroppointsStpV.fluxSelectDroppoint(dCtxPago, dCtxSh, driver);
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
			new Page1EnvioCheckoutMobilStpV(driver).selectFranjaHorariaUrgente(1);
		}	
	}
	
	/**
	 * No tenemos posibilidad sencilla de determinar si nos aparecerá el envío de tipo "Urgente" o "SendayNextday" así que si no encontramos uno ejecutamos la prueba con el otro
	 */
	private void alterTypeEnviosAccordingContext(DataCtxPago dCtxPago) {
		alterTypeEnviosTiendaStandar(dCtxPago);
		Pago pago = dCtxPago.getDataPedido().getPago();
		alterTypeEnviosNextaySomedayUntilExists(pago);
	}
	
	private void alterTypeEnviosTiendaStandar(DataCtxPago dCtxPago) {
		//If employee and Spain not "Recogida en Tienda"
		Pago pago = dCtxPago.getDataPedido().getPago();
		if (dCtxPago.getFTCkout().isEmpl && 
			"001".compareTo(dCtxPago.getDataPedido().getCodigoPais())==0) {
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
				if (new PageCheckoutWrapper(channel, app, driver).isPresentBlockMetodo(pago.getTipoEnvioType(app))) {
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