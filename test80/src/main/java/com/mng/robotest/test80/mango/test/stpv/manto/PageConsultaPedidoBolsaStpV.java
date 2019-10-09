package com.mng.robotest.test80.mango.test.stpv.manto;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageDetallePedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PagePedidos;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PagePedidos.TypeDetalle;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Pedidos/Bolsas en Manto
 * @author jorge.munoz
 *
 */

public class PageConsultaPedidoBolsaStpV {

	@Step (
		description="Seleccionamos el código de pedido para acceder al Detalle", 
        expected="Aparece la página de detalle de #{typeDetalle} correcta",
        saveImagePage=SaveWhen.Always,
        saveErrorData=SaveWhen.Never)
    public static void detalleFromListaPedBol(DataPedido dataPedido, TypeDetalle typeDetalle, AppEcom appE, WebDriver driver) 
    throws Exception {
        PagePedidos.clickLinkPedidoInLineas(driver, dataPedido.getCodigoPedidoManto(), typeDetalle);
        validacionesTotalesPedido(dataPedido, typeDetalle, appE, driver);
    }
    
    public static void validacionesTotalesPedido(DataPedido dataPedido, TypeDetalle typeDetalle, AppEcom appE, WebDriver driver) {
        validaDatosGeneralesPedido(dataPedido, appE, driver);
        validaDatosEnvioPedido(dataPedido, typeDetalle, appE, driver);
    }
    
    public static ChecksResult validaDatosEnvioPedido(DataPedido dataPedido, TypeDetalle typeDetalle, AppEcom appE, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
        TipoTransporte tipoTransporte = dataPedido.getPago().getTipoEnvioType(appE);
	 	validations.add(
			"El campo \"tipo servicio\" contiene el valor <b>" + tipoTransporte.getCodigoIntercambio() + "</b> (asociado al tipo de envío " + tipoTransporte + ")",
			PageDetallePedido.getTipoServicio(driver).compareTo(tipoTransporte.getCodigoIntercambio())==0, State.Info);
	 	
	 	if (typeDetalle==TypeDetalle.pedido && 
	        dataPedido.getTypeEnvio()==TipoTransporte.TIENDA && 
	        dataPedido.getDataDeliveryPoint()!=null) {
	        String textEnvioTienda = dataPedido.getDataDeliveryPoint().getCodigo();
		 	validations.add(
				"En los datos de envío aparece el texto <b>ENVIO A TIENDA " + textEnvioTienda + "</b>",
				PageDetallePedido.get1rstLineDatosEnvioText(driver).contains(textEnvioTienda), State.Defect);
	 	}
	 	
	 	return validations;     
    }
    
    @Validation
    public static ChecksResult validaDatosGeneralesPedido(DataPedido dataPedido, AppEcom appE, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Aparece la pantalla de detalle del pedido",
			PageDetallePedido.isPage(driver), State.Warn);
	 	validations.add(
			"Aparece un TOTAL de: " + dataPedido.getImporteTotalManto(),
			ImporteScreen.isPresentImporteInElements(dataPedido.getImporteTotalManto(), dataPedido.getCodigoPais(), PageDetallePedido.XPathImporteTotal, driver), 
			State.Warn);
	 	validations.add(
			"Las 3 líneas de la dirección de envío figuran en la dirección del pedido (" + dataPedido.getDireccionEnvio() +")",
			PageDetallePedido.isDireccionPedido(driver, dataPedido.getDireccionEnvio()), State.Warn);
	 	validations.add(
			"Figura el código de país (" + dataPedido.getCodigoPais() + ")",
			PageDetallePedido.isCodPaisPedido(driver, dataPedido.getCodigoPais()), State.Warn);
	 	
        Pago pago = dataPedido.getPago();
	 	if (pago.getTpv().getEstado()!=null &&
	        pago.getTpv().getEstado().compareTo("")!=0 &&
	        appE!=AppEcom.votf) {
	 		State stateVal = State.Warn;
	 		if (PageDetallePedido.isPedidoInStateMenos1NULL(driver)) {
        		stateVal = State.Defect;
        	}
		 	validations.add(
				"Aparece uno de los resultados posibles según el TPV: " + pago.getTpv().getEstado(),
				PageDetallePedido.isStateInTpvStates(driver, dataPedido), stateVal);
	 	}
	 	
	 	return validations;
    }
}
