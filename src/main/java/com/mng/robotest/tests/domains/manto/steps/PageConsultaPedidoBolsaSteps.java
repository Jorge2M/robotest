package com.mng.robotest.tests.domains.manto.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepMantoBase;
import com.mng.robotest.tests.domains.compra.pageobjects.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.tests.domains.manto.pageobjects.PageDetallePedido;
import com.mng.robotest.tests.domains.manto.pageobjects.PagePedidos;
import com.mng.robotest.tests.domains.manto.pageobjects.PagePedidos.TypeDetalle;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class PageConsultaPedidoBolsaSteps extends StepMantoBase {

	private final PageDetallePedido pgDetallePedido = new PageDetallePedido();
	
	@Step (
		description="Seleccionamos el código de pedido para acceder al Detalle", 
		expected="Aparece la página de detalle de #{typeDetalle} correcta",
		saveImagePage=ALWAYS, saveErrorData=NEVER)
	public void detalleFromListaPedBol(DataPedido dataPedido, TypeDetalle typeDetalle) {
		new PagePedidos().clickLinkPedidoInLineas(dataPedido.getCodigoPedidoManto(), typeDetalle);
		validacionesTotalesPedido(dataPedido, typeDetalle);
	}
	
	public void validacionesTotalesPedido(DataPedido dataPedido, TypeDetalle typeDetalle) {
		validaDatosGeneralesPedido(dataPedido);
		validaDatosEnvioPedido(dataPedido, typeDetalle);
	}
	
	public ChecksTM validaDatosEnvioPedido(DataPedido dataPedido, TypeDetalle typeDetalle) {
		var checks = ChecksTM.getNew();
		TipoTransporte tipoTransporte = dataPedido.getPago().getTipoEnvioType(app);
	 	checks.add(
			"El campo \"tipo servicio\" contiene el valor <b>" + tipoTransporte.getCodigoIntercambio() + "</b> (asociado al tipo de envío " + tipoTransporte + ")",
			pgDetallePedido.getTipoServicio().compareTo(tipoTransporte.getCodigoIntercambio())==0, INFO);
	 	
	 	if (typeDetalle==TypeDetalle.PEDIDO && 
			dataPedido.getTypeEnvio()==TipoTransporte.TIENDA && 
			dataPedido.getDataDeliveryPoint()!=null) {
			String textEnvioTienda = dataPedido.getDataDeliveryPoint().getCodigo();
		 	checks.add(
				"En los datos de envío aparece el texto <b>ENVIO A TIENDA " + textEnvioTienda + "</b>",
				pgDetallePedido.get1rstLineDatosEnvioText().contains(textEnvioTienda));
	 	}
	 	return checks;	 
	}
	
	@Validation
	public ChecksTM validaDatosGeneralesPedido(DataPedido dataPedido) {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la pantalla de detalle del pedido",
			pgDetallePedido.isPage(), WARN);
	 	
	 	checks.add(
			"Aparece un TOTAL de: " + dataPedido.getImporteTotalManto(),
			ImporteScreen.isPresentImporteInElements(dataPedido.getImporteTotalManto(), dataPedido.getCodigoPais(), PageDetallePedido.XP_IMNPORTE_TOTAL, driver), 
			WARN);
	 	
	 	checks.add(
			"Las 3 líneas de la dirección de envío figuran en la dirección del pedido (" + dataPedido.getDireccionEnvio() +")",
			pgDetallePedido.isDireccionPedido(dataPedido.getDireccionEnvio()), WARN);
	 	
	 	checks.add(
			"Figura el código de país (" + dataPedido.getCodigoPais() + ")",
			pgDetallePedido.isCodPaisPedido(dataPedido.getCodigoPais()), WARN);
	 	
		Pago pago = dataPedido.getPago();
	 	if (!pago.getEstados().isEmpty() && !isVotf()) {
	 		State stateVal = WARN;
	 		if (pgDetallePedido.isPedidoInStateMenos1NULL()) {
				stateVal = DEFECT;
			}
		 	checks.add(
				"Aparece uno de los resultados posibles del pago: " + pago.getEstados(),
				pgDetallePedido.isCorrectState(dataPedido), stateVal);
	 	}
	 	return checks;
	}
}
