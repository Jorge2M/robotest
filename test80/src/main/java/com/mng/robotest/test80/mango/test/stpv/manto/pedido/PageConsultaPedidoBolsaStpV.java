package com.mng.robotest.test80.mango.test.stpv.manto.pedido;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageDetallePedido;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PagePedidos;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PageDetallePedido.RightButtons;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PagePedidos.TypeDetalle;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Pedidos/Bolsas en Manto
 * @author jorge.munoz
 *
 */

public class PageConsultaPedidoBolsaStpV extends ElementPageFunctions {

    /**
     * Se accede al detalle de un pedido desde la lista de pedidos o bolsas
     */
    public static DatosStep detalleFromListaPedBol(DataPedido dataPedido, TypeDetalle typeDetalle, AppEcom appE, DataFmwkTest dFTest) 
    throws Exception {
        //Step. Accedemos al detalle del pedido desde la lista de pedidos
        DatosStep datosStep = new DatosStep   (
            "Seleccionamos el código de pedido para acceder al Detalle", 
            "Aparece la página de detalle de " + typeDetalle + " correcta");
        datosStep.setSaveImagePage(SaveWhen.Always);
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
            PagePedidos.clickLinkPedidoInLineas(dFTest.driver, dataPedido.getCodigoPedidoManto(), typeDetalle);
                                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
                                    
        //Validaciones
        validacionesTotalesPedido(dataPedido, typeDetalle, appE, datosStep, dFTest);
        
        return datosStep;
    }
    
    public static void validacionesTotalesPedido(DataPedido dataPedido, TypeDetalle typeDetalle, AppEcom appE, DatosStep datosStep, DataFmwkTest dFTest) {
        validaDatosGeneralesPedido(dataPedido, appE, datosStep, dFTest);
        validaDatosEnvioPedido(dataPedido, typeDetalle, appE, datosStep, dFTest);
    }
    
    public static void validaDatosEnvioPedido(DataPedido dataPedido, TypeDetalle typeDetalle, AppEcom appE, DatosStep datosStep, DataFmwkTest dFTest) {
        TipoTransporte tipoTransporte = dataPedido.getPago().getTipoEnvioType(appE);
        String validacion2 = "";
        String textEnvioTienda = "";
        if (typeDetalle==TypeDetalle.pedido && 
            dataPedido.getTypeEnvio()==TipoTransporte.TIENDA && 
            dataPedido.getDataDeliveryPoint()!=null) {
            textEnvioTienda = dataPedido.getDataDeliveryPoint().getCodigo();
            validacion2 = "<br>" +
            "2) En los datos de envío aparece el texto <b>ENVIO A TIENDA " + textEnvioTienda + "</b>";
        }
            
        String descripValidac = 
            "1) El campo \"tipo servicio\" contiene el valor <b>" + tipoTransporte.getCodigoIntercambio() + "</b> (asociado al tipo de envío " + tipoTransporte + ")" + 
            validacion2;
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (PageDetallePedido.getTipoServicio(dFTest.driver).compareTo(tipoTransporte.getCodigoIntercambio())!=0) {
                listVals.add(1, State.Info);
            }
            if ("".compareTo(validacion2)!=0) { 
                if (!PageDetallePedido.get1rstLineDatosEnvioText(dFTest.driver).contains(textEnvioTienda)) {
                    listVals.add(2, State.Defect);
                }
            }
                                            
            datosStep.setListResultValidations(listVals);
        }  
        finally { listVals.checkAndStoreValidations(descripValidac); }        
    }
    
    public static void validaDatosGeneralesPedido(DataPedido dataPedido, AppEcom appE, DatosStep datosStep, DataFmwkTest dFTest) {
        String validacion5 = "";
        Pago pago = dataPedido.getPago();
        if (pago.getTpv().getEstado()!=null &&
            pago.getTpv().getEstado().compareTo("")!=0 &&
            appE!=AppEcom.votf) {
            validacion5 = "<br>5) Aparece uno de los resultados posibles según el TPV: " + pago.getTpv().getEstado(); 
        }
                                            
        String descripValidac = 
            "1) Aparece la pantalla de detalle del pedido<br>" +
            "2) Aparece un TOTAL de: " + dataPedido.getImporteTotalManto() + "<br>" +
            "3) Las 3 líneas de la dirección de envío figuran en la dirección del pedido (" + dataPedido.getDireccionEnvio() +")<br>" +
            "4) Figura el código de país (" + dataPedido.getCodigoPais() + ")" +
            validacion5;
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageDetallePedido.isPage(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!ImporteScreen.isPresentImporteInElements(dataPedido.getImporteTotalManto(), dataPedido.getCodigoPais(), PageDetallePedido.XPathImporteTotal, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            if (!PageDetallePedido.isDireccionPedido(dFTest.driver, dataPedido.getDireccionEnvio())) {
                listVals.add(3, State.Warn);
            }
            if (!PageDetallePedido.isCodPaisPedido(dFTest.driver, dataPedido.getCodigoPais())) {
                listVals.add(4, State.Warn);
            }
            if (pago.getTpv().getEstado()!=null &&
                pago.getTpv().getEstado().compareTo("")!=0 &&
                appE!=AppEcom.votf) {
                if (!PageDetallePedido.isStateInTpvStates(dFTest.driver, dataPedido)) {
                	if (PageDetallePedido.isPedidoInStateMenos1NULL(dFTest.driver)) {
                		listVals.add(5, State.Defect);
                	}
                	else {
                		listVals.add(5, State.Warn);
                	}
                }
            }
                                            
            datosStep.setListResultValidations(listVals);
        }  
        finally { listVals.checkAndStoreValidations(descripValidac); }        
    }
    
    public static DatosStep clickButtonIrAGenerar(String idPedido, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
            "Seleccionamos el botón " + RightButtons.IrAGenerar, 
            "Aparece la página de generación del pedido");
        datosStep.setSaveImagePage(SaveWhen.Always);
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
            clickAndWait(RightButtons.IrAGenerar, dFTest.driver);
                                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
                                    
        //Validaciones
        PageGenerarPedidoStpV.validateIsPage(idPedido, datosStep, dFTest);
        
        return datosStep;
    }
}
