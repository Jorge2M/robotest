package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageAccesoMisComprasStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageMisComprasStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.pedidos.PageInputPedidoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.pedidos.PageListPedidosStpV;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageResultPagoStpV {

	@Validation (
		description="Acaba apareciendo la página de la Shop de Mango de \"Ya has hecho tu compra\" (la esperamos hasta #{maxSecondsToWait} segundos)",
		level=State.Defect)
    public static boolean validaIsPageUntil(int maxSecondsToWait, Channel channel, WebDriver driver) {
		return (PageResultPago.isVisibleTextoConfirmacionPago(driver, channel, maxSecondsToWait));
    }
    
    public static void validateIsPageOk(DataCtxPago dCtxPago, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        validateTextConfirmacionPago(dCtxSh.channel, driver);
        validateDataPedido(dCtxPago, dCtxSh, driver);
    }
    
    @Validation
    public static ListResultValidation validateTextConfirmacionPago(Channel channel, WebDriver driver) {
    	ListResultValidation validations = ListResultValidation.getNew();
	    int maxSecondsWait1 = 10;
	    boolean isVisibleTextConfirmacion = PageResultPago.isVisibleTextoConfirmacionPago(driver, channel, maxSecondsWait1);
       	validations.add(
    		"Aparece un texto de confirmación del pago (lo esperamos hasta " + maxSecondsWait1 + " segundos)<br>",
    		isVisibleTextConfirmacion, State.Warn);
       	if (!isVisibleTextConfirmacion) {
   		    int maxSecondsWait2 = 20;
           	validations.add(
        		"Si no aparece lo esperamos " + maxSecondsWait2 + " segundos",
        		PageResultPago.isVisibleTextoConfirmacionPago(driver, channel, maxSecondsWait2), State.Defect);
       	}
       	
       	return validations;
    }
    
    @Validation
    public static ListResultValidation validateDataPedido(DataCtxPago dCtxPago, DataCtxShop dCtxSh, WebDriver driver) throws Exception {
    	ListResultValidation validations = ListResultValidation.getNew();
        String importeTotal = "";
        DataBag dataBag = dCtxPago.getDataPedido().getDataBag(); 
        if (dataBag!=null && "".compareTo(dataBag.getImporteTotal())!=0) {
            importeTotal = dataBag.getImporteTotal();
        }
        else {
            importeTotal = dCtxPago.getDataPedido().getImporteTotal();
        }
      	validations.add(
      		"Aparece el importe " + importeTotal + " de la operación<br>",
      		ImporteScreen.isPresentImporteInScreen(importeTotal, dCtxSh.pais.getCodigo_pais(), driver), State.Warn);
        
	    if (dCtxSh.channel==Channel.desktop) {
	        if (dCtxSh.pais.isPaisWithMisCompras() && dCtxSh.appE==AppEcom.shop) {
	        	validations.add(
              		"Aparece el link hacia las compras<br>",
              		PageResultPago.isLinkMisComprasDesktop(driver), State.Warn);
	        }
	        else {
	        	validations.add(
              		"Aparece el link hacia los pedidos<br>",
              		PageResultPago.isLinkPedidosDesktop(driver), State.Warn);
	        }
	    }
	    
	    int maxSecondsWait = 5;
        String codigoPed = PageResultPago.getCodigoPedido(driver, dCtxSh.channel, maxSecondsWait);
        boolean isCodPedidoVisible = "".compareTo(codigoPed)!=0;
    	validations.add(
      		"Aparece el código de pedido (" + codigoPed + ") (lo esperamos hasta " + maxSecondsWait + " segundos)",
      		isCodPedidoVisible, State.Defect);
	    
		DataPedido dataPedido = dCtxPago.getDataPedido();
    	if (isCodPedidoVisible) {
            dataPedido.setResejecucion(State.Ok);
    	}
    	dataPedido.setCodpedido(codigoPed);
    	
    	return validations;
    }
    
    @Step (
    	description="Seleccionar el link \"Mis pedidos\"", 
        expected="Apareca la página de identificación del pedido")
    public static void selectMisPedidos(DataPedido dataPedido, WebDriver driver) throws Exception {
        PageResultPago.clickMisPedidos(driver);      
                                
        //Validations. Puede aparecer la página con la lista de pedidos o la de introducción de los datos del pedido
        if (PageListPedidos.isPage(driver)) {
        	PageListPedidosStpV.validateIsPage(dataPedido.getCodpedido(), driver);
        }
        else {
        	PageInputPedidoStpV.validateIsPage(driver);
        }
    }    
    
    @Step (
    	description="Seleccionar el link \"Mis Compras\"",
    	expected="Aparece la página de \"Mis compras\" o la de \"Acceso a Mis compras\" según si el usuario está o no loginado")
    public static void selectMisCompras(boolean userRegistered, WebDriver driver) throws Exception {
        PageResultPago.clickMisCompras(driver);     
                                
        //Validations
        if (userRegistered) {
            PageMisComprasStpV.validateIsPage(driver);
            AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/);
        }
        else {
            PageAccesoMisComprasStpV.validateIsPage(driver);
        }
    }    
    
    @Step (
    	description="Seleccionar el link \"Seguir de shopping\" o el icono de Mango", 
        expected="Volvemos a la portada")
    public static void selectSeguirDeShopping(Channel channel, AppEcom app, WebDriver driver) throws Exception {  
        //Si es clicable seleccionamos el link "Seguir de shopping", sinó seleccionamos el icono de Mango
        if (PageResultPago.isClickableSeguirDeShopping(driver, channel)) {
            PageResultPago.clickSeguirDeShopping(driver, channel);
        }
        else {
            SecCabecera.getNew(channel, app, driver).clickLogoMango();
        }
    }
    
    public static void selectLinkPedidoAndValidatePedido(DataPedido dataPedido, WebDriver driver) 
    throws Exception {
        PageResultPagoStpV.selectMisPedidos(dataPedido, driver);
        DatosStep datosStep = TestCaseData.getDatosLastStep();
        if (datosStep.getResultSteps()==State.Ok) {
            if (PageListPedidos.isPage(driver)) {
                PageListPedidosStpV.selectPedido(dataPedido.getCodpedido(), driver);
            }
            else {
                PageInputPedidoStpV.inputPedidoAndSubmit(dataPedido, driver);
            }
        }
    }
    
    public static DatosStep selectLinkMisComprasAndValidateCompra(DataCtxPago dCtxPago, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        DatosStep datosStep = null;
        PageResultPagoStpV.selectMisCompras(dCtxSh.userRegistered, dFTest.driver);
        DataPedido dataPedido = dCtxPago.getDataPedido();
        if (dCtxSh.userRegistered) {
            datosStep = PageMisComprasStpV.selectBlock(TypeCompra.Online, true/*ordersExpected*/, dFTest);
            PageMisComprasStpV.validateIsCompraOnlineVisible(dataPedido.getCodpedido(), dCtxPago.getFTCkout().isChequeRegalo, datosStep, dFTest);
        }
        else {
            PageAccesoMisComprasStpV.clickBlock(TypeBlock.NoRegistrado, dFTest);
            datosStep = PageAccesoMisComprasStpV.buscarPedidoForNoRegistrado(dCtxPago.getDataPedido(), dFTest);
        }
        
        return datosStep;
    }
}
