package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageResultPago;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageAccesoMisCompras.TypeBlock;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisCompras.TypeCompra;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.PageListPedidos;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageAccesoMisComprasStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageMisComprasStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.pedidos.PageInputPedidoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.pedidos.PageListPedidosStpV;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageResultPagoStpV {

    public static void validaIsPageUntil(int maxSecondsToWait, Channel channel, datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Acaba apareciendo la página de la Shop de Mango de \"Ya has hecho tu compra\" (la esperamos hasta " + maxSecondsToWait + " segundos)";   
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);   
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            if (!PageResultPago.isVisibleTextoConfirmacionPago(dFTest.driver, channel, maxSecondsToWait))
                fmwkTest.addValidation(1, State.Defect, listVals);
                                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static void validateIsPageOk(DataCtxPago dCtxPago, DataCtxShop dCtxSh, datosStep datosStep, DataFmwkTest dFTest) 
    throws Exception {
        String importeTotal = "";
        DataBag dataBag = dCtxPago.getDataPedido().getDataBag(); 
        if (dataBag!=null && "".compareTo(dataBag.getImporteTotal())!=0)
            importeTotal = dataBag.getImporteTotal();
        else
            importeTotal = dCtxPago.getDataPedido().getImporteTotal();
        
        //Validations
        validateTextConfirmacionPago(dCtxSh.channel, datosStep, dFTest);
       
        String tagPedido = "[PEDIDO]";
        String codigoPed = "";
        String validacion2 = "";
        if (dCtxSh.channel==Channel.desktop) {
            if (dCtxSh.pais.isPaisWithMisCompras() && dCtxSh.appE==AppEcom.shop)
                validacion2 =  "2) Aparece el link hacia las compras<br>";
            else
                validacion2 = "2) Aparece el link hacia los pedidos<br>";
        }

        DataPedido dataPedido = dCtxPago.getDataPedido();
        String descripValidac = 
            "1) Aparece el importe " + importeTotal + " de la operación<br>" +
            validacion2 + 
            "3) Aparece el código de pedido (" + tagPedido + ") (lo esperamos 5 segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);             
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, dCtxSh.pais.getCodigo_pais(), dFTest.driver)) 
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)                                
            if (dCtxSh.channel==Channel.desktop) {
                if (dCtxSh.pais.isPaisWithMisCompras() && dCtxSh.appE==AppEcom.shop) {
                    if (!PageResultPago.isLinkMisComprasDesktop(dFTest.driver))
                        fmwkTest.addValidation(2, State.Warn, listVals);
                }
                else {
                    if (!PageResultPago.isLinkPedidosDesktop(dFTest.driver))
                        fmwkTest.addValidation(2, State.Warn, listVals);
                }
            }
            //3)
            codigoPed = PageResultPago.getCodigoPedido(dFTest.driver, dCtxSh.channel, 5/*secondsWait*/);
            if ("".compareTo(codigoPed)==0)
                fmwkTest.addValidation(3, State.Defect, listVals);
            else
                dataPedido.setResejecucion(State.Ok);            
                        
            descripValidac = descripValidac.replace(tagPedido, codigoPed);
            dataPedido.setCodpedido(codigoPed);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals); 
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static void validateTextConfirmacionPago(Channel channel, datosStep datosStep, DataFmwkTest dFTest) {
	    int maxSecondsWait1 = 10;
	    int maxSecondsWait2 = 20;
	    String descripValidac = 
	        "1) Aparece un texto de confirmación del pago (lo esperamos hasta " + maxSecondsWait1 + " segundos)<br>" +
	    	"2) Si no aparece lo esperamos " + maxSecondsWait2 + " segundos";
	    datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);             
	    try { 
	        List<SimpleValidation> listVals = new ArrayList<>();
	        //1)
	        if (!PageResultPago.isVisibleTextoConfirmacionPago(dFTest.driver, channel, maxSecondsWait1)) {
	            fmwkTest.addValidation(1, State.Warn, listVals);
	            //2)
	            if (!PageResultPago.isVisibleTextoConfirmacionPago(dFTest.driver, channel, maxSecondsWait2))
	            	fmwkTest.addValidation(2, State.Defect, listVals);
	        }
	        
	        datosStep.setExcepExists(false); datosStep.setResultSteps(listVals); 
	    }
	    finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep selectMisPedidos(DataPedido dataPedido, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep (
            "Seleccionar el link \"Mis pedidos\"", 
            "Apareca la página de identificación del pedido");
        try {
            PageResultPago.clickMisPedidos(dFTest.driver);
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }         
                                
        //Puede aparecer la página con la lista de pedidos o la de introducción de los datos del pedido
        if (PageListPedidos.isPage(dFTest.driver))
            PageListPedidosStpV.validateIsPage(dataPedido.getCodpedido(), datosStep, dFTest);
        else
            PageInputPedidoStpV.validateIsPage(datosStep, dFTest);
        
        return datosStep;
    }    
    
    public static datosStep selectMisCompras(boolean userRegistered, DataFmwkTest dFTest) throws Exception {
        //Step
        String resultadoEsperado = "";
        if (userRegistered)
            resultadoEsperado = "Aparece la página de \"Mis compras\"";
        else
            resultadoEsperado = "Aparece la página de acceso a \"Mis compras\"";
            
        datosStep datosStep = new datosStep (
            "Seleccionar el link \"Mis Compras\"", 
            resultadoEsperado);
        try {
            PageResultPago.clickMisCompras(dFTest.driver);
                                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }         
                                
        //Validations
        if (userRegistered)
            PageMisComprasStpV.validateIsPage(datosStep, dFTest);
        else
            PageAccesoMisComprasStpV.validateIsPage(datosStep, dFTest);
        
        return datosStep;
    }    
    
    public static datosStep selectSeguirDeShopping(Channel channel, AppEcom app, DataFmwkTest dFTest) throws Exception {  
        //Step
        datosStep datosStep = new datosStep   (
            "Seleccionar el link \"Seguir de shopping\" o el icono de Mango", 
            "Volvemos a la portada");
        try {
            //Si es clicable seleccionamos el link "Seguir de shopping", sinó seleccionamos el icono de Mango
            if (PageResultPago.isClickableSeguirDeShopping(dFTest.driver, channel)) {
                PageResultPago.clickSeguirDeShopping(dFTest.driver, channel);
            }
            else {
                SecCabecera.getNew(channel, app, dFTest.driver).
                	clickLogoMango();
            }
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
    
    public static datosStep selectLinkPedidoAndValidatePedido(DataPedido dataPedido, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        datosStep datosStep = PageResultPagoStpV.selectMisPedidos(dataPedido, dFTest);
        if (datosStep.getResultSteps()==State.Ok) {
            if (PageListPedidos.isPage(dFTest.driver))
                datosStep = PageListPedidosStpV.selectPedido(dataPedido.getCodpedido(), dFTest);
            else
                datosStep = PageInputPedidoStpV.inputPedidoAndSubmit(dataPedido, app, dFTest);
        }
        
        return datosStep;
    }
    
    public static datosStep selectLinkMisComprasAndValidateCompra(DataCtxPago dCtxPago, DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        datosStep datosStep = null;
        PageResultPagoStpV.selectMisCompras(dCtxSh.userRegistered, dFTest);
        DataPedido dataPedido = dCtxPago.getDataPedido();
        if (dCtxSh.userRegistered) {
            datosStep = PageMisComprasStpV.selectBlock(TypeCompra.Online, true/*ordersExpected*/, dFTest);
            PageMisComprasStpV.validateIsCompraOnlineVisible(dataPedido.getCodpedido(), dCtxPago.getFTCkout().isChequeRegalo, datosStep, dFTest);
        }
        else {
            PageAccesoMisComprasStpV.clickBlock(TypeBlock.NoRegistrado, dFTest);
            datosStep = PageAccesoMisComprasStpV.buscarPedidoForNoRegistrado(dCtxPago.getDataPedido(), dCtxSh.appE, dFTest);
        }
        
        return datosStep;
    }
}
