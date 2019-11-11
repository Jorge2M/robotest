package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.StepTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageResultPago;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageAccesoMisCompras.TypeBlock;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisCompras.TypeCompra;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.PageListPedidos;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
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
        if (dCtxPago.getFTCkout().loyaltyPoints) {
        	validateBlockNewLoyaltyPoints(driver);
        }
    }
    
    @Validation
    public static ChecksResult validateTextConfirmacionPago(Channel channel, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	    int maxSecondsWait1 = 10;
	    boolean isVisibleTextConfirmacion = PageResultPago.isVisibleTextoConfirmacionPago(driver, channel, maxSecondsWait1);
       	validations.add(
    		"Aparece un texto de confirmación del pago (lo esperamos hasta " + maxSecondsWait1 + " segundos)",
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
    public static ChecksResult validateDataPedido(DataCtxPago dCtxPago, DataCtxShop dCtxSh, WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
        String importeTotal = "";
        DataBag dataBag = dCtxPago.getDataPedido().getDataBag(); 
        if (dataBag!=null && "".compareTo(dataBag.getImporteTotal())!=0) {
            importeTotal = dataBag.getImporteTotal();
        } else {
            importeTotal = dCtxPago.getDataPedido().getImporteTotal();
        }
      	validations.add(
      		"Aparece el importe " + importeTotal + " de la operación",
      		ImporteScreen.isPresentImporteInScreen(importeTotal, dCtxSh.pais.getCodigo_pais(), driver), State.Warn);
        
	    if (dCtxSh.channel==Channel.desktop) {
	        if (dCtxSh.pais.isPaisWithMisCompras() && dCtxSh.appE==AppEcom.shop) {
	        	validations.add(
              		"Aparece el link hacia las compras",
              		PageResultPago.isLinkMisComprasDesktop(driver), State.Warn);
	        } else {
	        	validations.add(
              		"Aparece el link hacia los pedidos",
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
    
    @Validation (
    	description="Aparece el bloque informando que se han generado nuevos Loyalty Points",
    	level=State.Defect)
    public static boolean validateBlockNewLoyaltyPoints(WebDriver driver) {
    	return (PageResultPago.isVisibleBlockNewLoyaltyPoints(driver));
    }
    
    @Step (
    	description="Seleccionar el link \"Mis pedidos\"", 
        expected="Apareca la página de identificación del pedido")
    public static void selectMisPedidos(DataPedido dataPedido, WebDriver driver) throws Exception {
        PageResultPago.clickMisPedidos(driver);      
                                
        //Validations. Puede aparecer la página con la lista de pedidos o la de introducción de los datos del pedido
        if (PageListPedidos.isPage(driver)) {
        	PageListPedidosStpV.validateIsPage(dataPedido.getCodpedido(), driver);
        } else {
        	PageInputPedidoStpV.validateIsPage(driver);
        }
    }    
    
    @Step (
    	description="Seleccionar el link \"Mis Compras\"",
    	expected="Aparece la página de \"Mis compras\" o la de \"Acceso a Mis compras\" según si el usuario está o no loginado")
    public static void selectMisCompras(boolean userRegistered, Channel channel, WebDriver driver) throws Exception {
        PageResultPago.clickMisCompras(driver);     
        if (userRegistered) {
        	PageMisComprasStpV pageMisComprasStpV = PageMisComprasStpV.getNew(channel, driver);
        	pageMisComprasStpV.validateIsPage();
            
            StdValidationFlags flagsVal = StdValidationFlags.newOne();
            flagsVal.validaSEO = true;
            flagsVal.validaJS = true;
            flagsVal.validaImgBroken = false;
            AllPagesStpV.validacionesEstandar(flagsVal, driver);
        } else {
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
        } else {
            SecCabecera.getNew(channel, app, driver).clickLogoMango();
        }
    }
    
    public static void selectLinkPedidoAndValidatePedido(DataPedido dataPedido, WebDriver driver) 
    throws Exception {
        PageResultPagoStpV.selectMisPedidos(dataPedido, driver);
        StepTM StepTestMaker = TestMaker.getLastStep();
        if (StepTestMaker.getResultSteps()==State.Ok) {
            if (PageListPedidos.isPage(driver)) {
                PageListPedidosStpV.selectPedido(dataPedido.getCodpedido(), driver);
            } else {
                PageInputPedidoStpV.inputPedidoAndSubmit(dataPedido, driver);
            }
        }
    }
    
    public static void selectLinkMisComprasAndValidateCompra(DataCtxPago dCtxPago, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {    	
        PageResultPagoStpV.selectMisCompras(dCtxSh.userRegistered, dCtxSh.channel, driver);
        DataPedido dataPedido = dCtxPago.getDataPedido();
        if (dCtxSh.userRegistered) {
        	PageMisComprasStpV pageMisComprasStpV = PageMisComprasStpV.getNew(dCtxSh.channel, driver);
        	pageMisComprasStpV.selectBlock(TypeCompra.Online, true);
        	pageMisComprasStpV.validateIsCompraOnlineVisible(dataPedido.getCodpedido(), dCtxPago.getFTCkout().isChequeRegalo);
        } else {
            PageAccesoMisComprasStpV.clickBlock(TypeBlock.NoRegistrado, driver);
            PageAccesoMisComprasStpV.buscarPedidoForNoRegistrado(dCtxPago.getDataPedido(), driver);
        }
    }
}
