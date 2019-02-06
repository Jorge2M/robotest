package com.mng.robotest.test80.mango.test.stpv.manto.pedido;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
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
@SuppressWarnings("javadoc")
public class PageConsultaPedidoBolsaStpV extends ElementPageFunctions {

    /**
     * Se accede al detalle de un pedido desde la lista de pedidos o bolsas
     */
    public static datosStep detalleFromListaPedBol(DataPedido dataPedido, TypeDetalle typeDetalle, AppEcom appE, DataFmwkTest dFTest) throws Exception {
        //Step. Accedemos al detalle del pedido desde la lista de pedidos
        datosStep datosStep = new datosStep   (
            "Seleccionamos el código de pedido para acceder al Detalle", 
            "Aparece la página de detalle de " + typeDetalle + " correcta");
        datosStep.setGrabImage(true);
        datosStep.setGrab_ErrorPageIfProblem(false);
        try {
            PagePedidos.clickLinkPedidoInLineas(dFTest.driver, dataPedido.getCodigoPedidoManto(), typeDetalle);
                                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                                    
        //Validaciones
        validacionesTotalesPedido(dataPedido, typeDetalle, appE, datosStep, dFTest);
        
        return datosStep;
    }
    
    public static void validacionesTotalesPedido(DataPedido dataPedido, TypeDetalle typeDetalle, AppEcom appE, datosStep datosStep, DataFmwkTest dFTest) {
        validaDatosGeneralesPedido(dataPedido, appE, datosStep, dFTest);
        validaDatosEnvioPedido(dataPedido, typeDetalle, appE, datosStep, dFTest);
    }
    
    public static void validaDatosEnvioPedido(DataPedido dataPedido, TypeDetalle typeDetalle, AppEcom appE, datosStep datosStep, DataFmwkTest dFTest) {
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
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (PageDetallePedido.getTipoServicio(dFTest.driver).compareTo(tipoTransporte.getCodigoIntercambio())!=0) 
                fmwkTest.addValidation(1, State.Info, listVals);
            //2)
            if ("".compareTo(validacion2)!=0) 
                if (!PageDetallePedido.get1rstLineDatosEnvioText(dFTest.driver).contains(textEnvioTienda))
                    fmwkTest.addValidation(2, State.Defect, listVals);
                                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
    }
    
    public static void validaDatosGeneralesPedido(DataPedido dataPedido, AppEcom appE, datosStep datosStep, DataFmwkTest dFTest) {
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
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageDetallePedido.isPage(dFTest.driver)) 
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInElements(dataPedido.getImporteTotalManto(), dataPedido.getCodigoPais(), PageDetallePedido.XPathImporteTotal, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (!PageDetallePedido.isDireccionPedido(dFTest.driver, dataPedido.getDireccionEnvio()))
                fmwkTest.addValidation(3, State.Warn, listVals);
            //4)
            if (!PageDetallePedido.isCodPaisPedido(dFTest.driver, dataPedido.getCodigoPais()))
                fmwkTest.addValidation(4, State.Warn, listVals);
            //5)
            if (pago.getTpv().getEstado()!=null &&
                pago.getTpv().getEstado().compareTo("")!=0 &&
                appE!=AppEcom.votf) {
                if (!PageDetallePedido.isStateInTpvStates(dFTest.driver, dataPedido)) {
                	if (PageDetallePedido.isPedidoInStateMenos1NULL(dFTest.driver))
                		fmwkTest.addValidation(5, State.Defect, listVals);
                	else
                		fmwkTest.addValidation(5, State.Warn, listVals);
                }
            }
                                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
    }
    
    public static datosStep clickButtonIrAGenerar(String idPedido, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep   (
            "Seleccionamos el botón " + RightButtons.IrAGenerar, 
            "Aparece la página de generación del pedido");
        datosStep.setGrabImage(true); datosStep.setGrab_ErrorPageIfProblem(false);
        try {
            clickAndWait(RightButtons.IrAGenerar, dFTest.driver);
                                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                                    
        //Validaciones
        PageGenerarPedidoStpV.validateIsPage(idPedido, datosStep, dFTest);
        
        return datosStep;
    }
}
