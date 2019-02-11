package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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

    public static void validaIsPageUntil(int maxSecondsToWait, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Acaba apareciendo la página de la Shop de Mango de \"Ya has hecho tu compra\" (la esperamos hasta " + maxSecondsToWait + " segundos)";   
        datosStep.setNOKstateByDefault();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageResultPago.isVisibleTextoConfirmacionPago(dFTest.driver, channel, maxSecondsToWait)) {
                listVals.add(1, State.Defect);
            }
                                                                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static void validateIsPageOk(DataCtxPago dCtxPago, DataCtxShop dCtxSh, DatosStep datosStep, DataFmwkTest dFTest) 
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
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, dCtxSh.pais.getCodigo_pais(), dFTest.driver)) {
                listVals.add(1, State.Warn);   
            }
            if (dCtxSh.channel==Channel.desktop) {
                if (dCtxSh.pais.isPaisWithMisCompras() && dCtxSh.appE==AppEcom.shop) {
                    if (!PageResultPago.isLinkMisComprasDesktop(dFTest.driver)) {
                        listVals.add(2, State.Warn);
                    }
                }
                else {
                    if (!PageResultPago.isLinkPedidosDesktop(dFTest.driver)) {
                        listVals.add(2, State.Warn);
                    }
                }
            }
            codigoPed = PageResultPago.getCodigoPedido(dFTest.driver, dCtxSh.channel, 5/*secondsWait*/);
            if ("".compareTo(codigoPed)==0)
                listVals.add(3, State.Defect);
            else
                dataPedido.setResejecucion(State.Ok);            
                        
            descripValidac = descripValidac.replace(tagPedido, codigoPed);
            dataPedido.setCodpedido(codigoPed);
            
            datosStep.setListResultValidations(listVals); 
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static void validateTextConfirmacionPago(Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
	    int maxSecondsWait1 = 10;
	    int maxSecondsWait2 = 20;
	    String descripValidac = 
	        "1) Aparece un texto de confirmación del pago (lo esperamos hasta " + maxSecondsWait1 + " segundos)<br>" +
	    	"2) Si no aparece lo esperamos " + maxSecondsWait2 + " segundos";
	    datosStep.setNOKstateByDefault();
	    ListResultValidation listVals = ListResultValidation.getNew(datosStep);
	    try { 
	        if (!PageResultPago.isVisibleTextoConfirmacionPago(dFTest.driver, channel, maxSecondsWait1)) {
	            listVals.add(1, State.Warn);
	            if (!PageResultPago.isVisibleTextoConfirmacionPago(dFTest.driver, channel, maxSecondsWait2)) {
	            	listVals.add(2, State.Defect);
	            }
	        }
	        
	        datosStep.setListResultValidations(listVals); 
	    }
	    finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep selectMisPedidos(DataPedido dataPedido, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
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
    
    public static DatosStep selectMisCompras(boolean userRegistered, DataFmwkTest dFTest) throws Exception {
        //Step
        String resultadoEsperado = "";
        if (userRegistered)
            resultadoEsperado = "Aparece la página de \"Mis compras\"";
        else
            resultadoEsperado = "Aparece la página de acceso a \"Mis compras\"";
            
        DatosStep datosStep = new DatosStep (
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
    
    public static DatosStep selectSeguirDeShopping(Channel channel, AppEcom app, DataFmwkTest dFTest) throws Exception {  
        //Step
        DatosStep datosStep = new DatosStep   (
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
    
    public static DatosStep selectLinkPedidoAndValidatePedido(DataPedido dataPedido, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        DatosStep datosStep = PageResultPagoStpV.selectMisPedidos(dataPedido, dFTest);
        if (datosStep.getResultSteps()==State.Ok) {
            if (PageListPedidos.isPage(dFTest.driver))
                datosStep = PageListPedidosStpV.selectPedido(dataPedido.getCodpedido(), dFTest);
            else
                datosStep = PageInputPedidoStpV.inputPedidoAndSubmit(dataPedido, app, dFTest);
        }
        
        return datosStep;
    }
    
    public static DatosStep selectLinkMisComprasAndValidateCompra(DataCtxPago dCtxPago, DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = null;
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
