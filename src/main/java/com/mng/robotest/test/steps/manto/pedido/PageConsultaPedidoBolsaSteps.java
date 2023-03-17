package com.mng.robotest.test.steps.manto.pedido;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.pageobjects.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.domains.manto.pageobjects.PageDetallePedido;
import com.mng.robotest.domains.manto.pageobjects.PagePedidos;
import com.mng.robotest.domains.manto.pageobjects.PagePedidos.TypeDetalle;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.utils.ImporteScreen;


public class PageConsultaPedidoBolsaSteps extends StepBase {

	private final PageDetallePedido pageDetallePedido = new PageDetallePedido();
	
	@Step (
		description="Seleccionamos el código de pedido para acceder al Detalle", 
		expected="Aparece la página de detalle de #{typeDetalle} correcta",
		saveImagePage=SaveWhen.Always,
		saveErrorData=SaveWhen.Never)
	public void detalleFromListaPedBol(DataPedido dataPedido, TypeDetalle typeDetalle) {
		new PagePedidos().clickLinkPedidoInLineas(dataPedido.getCodigoPedidoManto(), typeDetalle);
		validacionesTotalesPedido(dataPedido, typeDetalle);
	}
	
	public void validacionesTotalesPedido(DataPedido dataPedido, TypeDetalle typeDetalle) {
		validaDatosGeneralesPedido(dataPedido);
		validaDatosEnvioPedido(dataPedido, typeDetalle);
	}
	
	@Validation
	public ChecksTM validaDatosEnvioPedido(DataPedido dataPedido, TypeDetalle typeDetalle) {
		var checks = ChecksTM.getNew();
		TipoTransporte tipoTransporte = dataPedido.getPago().getTipoEnvioType(app);
		checks.add(
			"El campo \"tipo servicio\" contiene el valor <b>" + tipoTransporte.getCodigoIntercambio() + "</b> (asociado al tipo de envío " + tipoTransporte + ")",
			pageDetallePedido.getTipoServicio().compareTo(tipoTransporte.getCodigoIntercambio())==0, State.Info);		
		
		if (typeDetalle==TypeDetalle.PEDIDO && 
			dataPedido.getTypeEnvio()==TipoTransporte.TIENDA && 
			dataPedido.getDataDeliveryPoint()!=null) {
			String textEnvioTienda = dataPedido.getDataDeliveryPoint().getCodigo();
			checks.add(
				"En los datos de envío aparece el texto <b>ENVIO A TIENDA " + textEnvioTienda + "</b>",
				pageDetallePedido.get1rstLineDatosEnvioText().contains(textEnvioTienda), State.Defect);			  
		}
		return checks;
	}
	
	@Validation
	public ChecksTM validaDatosGeneralesPedido(DataPedido dataPedido) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la pantalla de detalle del pedido",
			pageDetallePedido.isPage(), State.Warn);
		
		checks.add(
			"Aparece un TOTAL de: " + dataPedido.getImporteTotalManto(),
			ImporteScreen.isPresentImporteInElements(dataPedido.getImporteTotalManto(), dataPedido.getCodigoPais(), PageDetallePedido.XPATH_IMNPORTE_TOTAL, driver), State.Warn);
		
		checks.add(
			"Las 3 líneas de la dirección de envío figuran en la dirección del pedido (" + dataPedido.getDireccionEnvio() +")",
			pageDetallePedido.isDireccionPedido(dataPedido.getDireccionEnvio()), State.Warn);
		
		checks.add(
			"Figura el código de país (" + dataPedido.getCodigoPais() + ")",
			pageDetallePedido.isCodPaisPedido(dataPedido.getCodigoPais()), State.Warn);
		
		Pago pago = dataPedido.getPago();
		if (pago.getTpv().getEstado()!=null &&
			pago.getTpv().getEstado().compareTo("")!=0 &&
			app!=AppEcom.votf) {
			boolean isPedidoInStateTpv = pageDetallePedido.isStateInTpvStates(dataPedido);
			boolean pedidoInStateMenos1Null = pageDetallePedido.isPedidoInStateMenos1NULL();
			checks.add(
				"Aparece uno de los resultados posibles según el TPV: " + pago.getTpv().getEstado(),
				isPedidoInStateTpv, State.Warn);
			
			if (!isPedidoInStateTpv) {
				checks.add(
					"El pedido no está en estado -1-NULL",
					!pedidoInStateMenos1Null, State.Defect);
			}
		}  
		return checks;
	}
	
	@Step (
		description="Seleccionamos el botón \"Ir A Generar\"", 
		expected="Aparece la página de generación del pedido",
		saveImagePage=SaveWhen.Always,
		saveErrorData=SaveWhen.Never)
	public void clickButtonIrAGenerar(String idPedido) {
		pageDetallePedido.clickIrAGenerarButton();
		new PageGenerarPedidoSteps().validateIsPage(idPedido);
	}
}
