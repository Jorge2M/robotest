package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.CompraOnline;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.CompraTienda;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisCompras;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisCompras.TypeCompra;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalDetalleMisCompras;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.stpv.shop.pedidos.PageDetallePedidoStpV;

public class PageMisComprasStpV {

    public static SecDetalleCompraTiendaStpV SecDetalleCompraTienda; 
    public static SecQuickViewArticuloStpV SecQuickViewArticulo;
    public static void validateIsPage(DataCtxShop dataCtxShop, WebDriver driver) throws Exception {
        if (dataCtxShop.pais.isTicketStoreEnabled()) {
            validateIsPage(driver);
        } else {
            validateIsPageWhenNotExistTabs(driver);
            
            StdValidationFlags flagsVal = StdValidationFlags.newOne();
            flagsVal.validaSEO = true;
            flagsVal.validaJS = true;
            flagsVal.validaImgBroken = false;
            AllPagesStpV.validacionesEstandar(flagsVal, driver);
        }
    }

    @Validation
    private static ChecksResult validateIsPageWhenNotExistTabs(WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsToWait = 2;
      	validations.add(
    		"Aparece la página de \"Mis Compras\" (la esperamos hasta " + maxSecondsToWait + " segundos)<br>",
    		PageMisCompras.isPageUntil(maxSecondsToWait, driver), State.Warn);
      	validations.add(
    		"No aparece el bloque de \"Tienda\"<br>",
    		!PageMisCompras.isPresentBlockUntil(0, TypeCompra.Tienda, driver), State.Warn);
      	validations.add(
    		"No aparece el bloque de \"Online\"",
    		!PageMisCompras.isPresentBlockUntil(0, TypeCompra.Online, driver), State.Warn);
      	return validations;
    }

    @Validation
    public static ChecksResult validateIsPage(WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
    	int maxSecondsWait = 2;
      	validations.add(
    		"Aparece la página de \"Mis Compras\" (la esperamos hasta " + maxSecondsWait + " segundos)<br>",
    		PageMisCompras.isPageUntil(maxSecondsWait, driver), State.Warn);
      	validations.add(
    		"Aparece el bloque de \"Tienda\"<br>",
    		PageMisCompras.isPresentBlockUntil(0, TypeCompra.Tienda, driver), State.Warn);
      	validations.add(
      		"Aparece el bloque de \"Online\"",
      		PageMisCompras.isPresentBlockUntil(0, TypeCompra.Online, driver), State.Warn);
      	return validations;
    }
    
    @Step (
    	description="Seleccionar el bloque <b>#{typeCompra}<b>", 
        expected="Se hace visible el bloque #{typeCompra}")
    public static void selectBlock(TypeCompra typeCompra, boolean ordersExpected, WebDriver driver) {
    	PageMisCompras.clickBlock(typeCompra, driver);
        int maxSecondsWait = 2;
    	checkBlockSelected(typeCompra, maxSecondsWait, driver);
        if (ordersExpected) {
        	checkArticlesInList(typeCompra, driver);
        }
        else {
        	checkListArticlesVoid(typeCompra, driver);
        }
    }

	@Validation (
		description="Queda seleccionado el bloque de <b>#{typeCompra}</b> (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Warn)
	private static boolean checkBlockSelected(TypeCompra typeCompra, int maxSecondsWait, WebDriver driver) {
		return (PageMisCompras.isSelectedBlockUntil(maxSecondsWait, typeCompra, driver));
	}
	
	@Validation
	private static ChecksResult checkArticlesInList(TypeCompra typeCompra, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
    	int maxSecondsWait = 2;
    	boolean isVisibleAnyCompra = PageMisCompras.isVisibleAnyCompraUntil(maxSecondsWait, driver); 
      	validations.add(
    		"Aparece una lista con algún artículo (lo esperamos hasta " + maxSecondsWait + " segundos) <br>",
    		isVisibleAnyCompra, State.Warn);
      	if (isVisibleAnyCompra) {
          	validations.add(
        		"El 1er artículo es de tipo <b>" + typeCompra + "</b>",
        		PageMisCompras.getTypeCompra(1, driver)==typeCompra, State.Warn);
      	}
		return validations;      
	}
		
	@Validation
	private static ChecksResult checkListArticlesVoid(TypeCompra typeCompra, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
    	boolean isVisibleAnyCompra = PageMisCompras.isVisibleAnyCompraUntil(0, driver);
      	validations.add(
    		"No aparece ningún artículo<br>",
    		!isVisibleAnyCompra, State.Warn);
      	validations.add(
    		"Es visible la imagen asociada a <b>Lista Vacía</b> para " + typeCompra,
    		PageMisCompras.isVisibleEmptyListImage(typeCompra, driver), State.Warn);
      	return validations;
	}
    
    @Validation
    public static ChecksResult validateIsCompraOnlineVisible(String codPedido, boolean isChequeRegalo, WebDriver driver) {
        ChecksResult validations = ChecksResult.getNew();
        State stateVal = State.Warn;
        boolean avoidEvidences = false;
        if (isChequeRegalo) {
            stateVal = State.Info;
            avoidEvidences = true;
        }
        validations.add(
        	"Es visible la compra " + TypeCompra.Online + " asociada al pedido <b>" + codPedido + "</b>",
        	PageMisCompras.isVisibleCompraOnline(codPedido, driver), stateVal, avoidEvidences);
        return validations;
    }
    
    @Step (
    	description="Seleccionamos la #{posInLista}a compra (tipo Online) de la lista", 
        expected="Aparece la página con los detalles del pedido",
        saveHtmlPage=SaveWhen.IfProblem)
    public static void selectCompraOnline(int posInLista, String codPais, Channel channel, WebDriver driver) throws Exception {
    	CompraOnline compraOnline = PageMisCompras.getDataCompraOnline(posInLista, channel, driver);
        PageMisCompras.clickCompra(posInLista, driver);       
        
        //Validaciones
        PageDetallePedidoStpV pageDetPedidoStpV = new PageDetallePedidoStpV(driver);
        pageDetPedidoStpV.validateIsPageOk(compraOnline, codPais, driver);       
    }
    
    @SuppressWarnings("static-access")
    @Step (
    	description="Seleccionamos la #{posInLista}a compra (tipo Tienda) de la lista", 
        expected="Aparece una sección con los detalles de la Compra")
    public static void selectCompraTienda(int posInLista, Channel channel, WebDriver driver) throws Exception {
    	CompraTienda compraTienda = PageMisCompras.getDataCompraTienda(posInLista, channel, driver);
        PageMisCompras.clickCompra(posInLista, driver);       
        SecDetalleCompraTienda.validateIsOk(compraTienda, channel, driver);      
    }

    final static String tagReferencia = "@TagReferencia";
    @Step (
    	description="Seleccionamos link <b>Más Info</b> del 1er artículo <b>" + tagReferencia + "</b>", 
        expected="Aparece el modal con información del artículo")
	public static void clickMoreInfo(WebDriver driver) {
    	String infoArticle = PageMisCompras.getReferenciaPrimerArticulo(driver);
    	TestCaseData.getDatosCurrentStep().replaceInDescription(tagReferencia, infoArticle);
        PageMisCompras.clickMasInfoArticulo(driver);           
        checkModalArticle(infoArticle, driver);
	}    
    
    @Validation
    private static ChecksResult checkModalArticle(String infoArticle, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
    	validations.add(
    		"Aparece el modal con información del artículo<br>",
    		ModalDetalleMisCompras.isVisible(driver), State.Defect);
    	validations.add(
    		"La información del artículo que aparece en el modal es correcta " + infoArticle,
    		ModalDetalleMisCompras.isReferenciaValidaModal(infoArticle, driver), State.Defect);
    	return validations;
    }
}
