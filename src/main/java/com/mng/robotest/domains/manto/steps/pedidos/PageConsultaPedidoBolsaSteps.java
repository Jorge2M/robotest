package com.mng.robotest.domains.manto.steps.pedidos;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.StepMantoBase;
import com.mng.robotest.domains.compra.pageobjects.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.domains.manto.pageobjects.PageDetallePedido;
import com.mng.robotest.domains.manto.pageobjects.PagePedidos;
import com.mng.robotest.domains.manto.pageobjects.PagePedidos.TypeDetalle;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageConsultaPedidoBolsaSteps extends StepMantoBase {

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
			pageDetallePedido.getTipoServicio().compareTo(tipoTransporte.getCodigoIntercambio())==0, Info);		
		
		if (typeDetalle==TypeDetalle.PEDIDO && 
			dataPedido.getTypeEnvio()==TipoTransporte.TIENDA && 
			dataPedido.getDataDeliveryPoint()!=null) {
			String textEnvioTienda = dataPedido.getDataDeliveryPoint().getCodigo();
			checks.add(
				"En los datos de envío aparece el texto <b>ENVIO A TIENDA " + textEnvioTienda + "</b>",
				pageDetallePedido.get1rstLineDatosEnvioText().contains(textEnvioTienda), Defect);			  
		}
		return checks;
	}
	
	@Validation
	public ChecksTM validaDatosGeneralesPedido(DataPedido dataPedido) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la pantalla de detalle del pedido",
			pageDetallePedido.isPage(), Warn);
		
		checks.add(
			"Aparece un TOTAL de: " + dataPedido.getImporteTotalManto(),
			ImporteScreen.isPresentImporteInElements(dataPedido.getImporteTotalManto(), dataPedido.getCodigoPais(), PageDetallePedido.XPATH_IMNPORTE_TOTAL, driver), Warn);
		
		checks.add(
			"Las 3 líneas de la dirección de envío figuran en la dirección del pedido (" + dataPedido.getDireccionEnvio() +")",
			pageDetallePedido.isDireccionPedido(dataPedido.getDireccionEnvio()), Warn);
		
		checks.add(
			"Figura el código de país (" + dataPedido.getCodigoPais() + ")",
			pageDetallePedido.isCodPaisPedido(dataPedido.getCodigoPais()), Warn);
		
		Pago pago = dataPedido.getPago();
		if (pago.getTpv().getEstado()!=null &&
			pago.getTpv().getEstado().compareTo("")!=0 &&
			app!=AppEcom.votf) {
			boolean isPedidoInStateTpv = pageDetallePedido.isStateInTpvStates(dataPedido);
			boolean pedidoInStateMenos1Null = pageDetallePedido.isPedidoInStateMenos1NULL();
			checks.add(
				"Aparece uno de los resultados posibles según el TPV: " + pago.getTpv().getEstado(),
				isPedidoInStateTpv, Warn);
			
			if (!isPedidoInStateTpv) {
				checks.add(
					"El pedido no está en estado -1-NULL",
					!pedidoInStateMenos1Null, Defect);
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
