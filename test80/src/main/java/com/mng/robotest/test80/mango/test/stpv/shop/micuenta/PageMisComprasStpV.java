package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.CompraOnline;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.CompraTienda;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisCompras;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisCompras.TypeCompra;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.SecDetalleCompraTienda;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.SecQuickViewArticulo;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalDetalleMisCompras;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.stpv.shop.modales.ModalDetalleMisComprasStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.pedidos.PageDetallePedidoStpV;

public class PageMisComprasStpV {
	
	private final WebDriver driver;
    private final PageMisCompras pageMisCompras;
    private final SecDetalleCompraTiendaStpV secDetalleCompraTiendaStpV; 
    private final SecQuickViewArticuloStpV secQuickViewArticuloStpV;
    private final ModalDetalleMisComprasStpV modalDetalleMisComprasStpV;
    
    private PageMisComprasStpV(Channel channel, WebDriver driver) {
    	this.driver = driver;
    	this.pageMisCompras = PageMisCompras.getNew(channel, driver);
    	SecDetalleCompraTienda secDetalle = pageMisCompras.getSecDetalleCompraTienda();
    	SecQuickViewArticulo secQuickView = pageMisCompras.getSecQuickViewArticulo();
    	ModalDetalleMisCompras modalDetalle = pageMisCompras.getModalDetalleMisCompras();
    	this.secDetalleCompraTiendaStpV = SecDetalleCompraTiendaStpV.getNew(secDetalle, channel);
    	this.secQuickViewArticuloStpV = SecQuickViewArticuloStpV.getNew(secQuickView);
    	this.modalDetalleMisComprasStpV = ModalDetalleMisComprasStpV.getNew(modalDetalle, driver);
    }
    public static PageMisComprasStpV getNew(Channel channel, WebDriver driver) {
    	return new PageMisComprasStpV(channel, driver);
    }
    
    public SecDetalleCompraTiendaStpV getSecDetalleCompraTienda() {
    	return this.secDetalleCompraTiendaStpV;
    }
    
    public SecQuickViewArticuloStpV getSecQuickViewArticulo() {
    	return this.secQuickViewArticuloStpV;
    }
    public ModalDetalleMisComprasStpV getModalDetalleMisCompras() {
    	return this.modalDetalleMisComprasStpV;
    }
    
    public void validateIsPage(Pais pais) throws Exception {
        if (pais.isTicketStoreEnabled()) {
            validateIsPage();
        } else {
            validateIsPageWhenNotExistTabs();
            StdValidationFlags flagsVal = StdValidationFlags.newOne();
            flagsVal.validaSEO = true;
            flagsVal.validaJS = true;
            flagsVal.validaImgBroken = false;
            AllPagesStpV.validacionesEstandar(flagsVal, driver);
        }
    }

    @Validation
    private ChecksTM validateIsPageWhenNotExistTabs() throws Exception {
    	ChecksTM validations = ChecksTM.getNew();
        int maxSecondsToWait = 2;
      	validations.add(
    		"Aparece la página de \"Mis Compras\" (la esperamos hasta " + maxSecondsToWait + " segundos)",
    		pageMisCompras.isPageUntil(maxSecondsToWait), State.Warn);
      	validations.add(
    		"No aparece el bloque de \"Tienda\"",
    		!pageMisCompras.isPresentBlockUntil(0, TypeCompra.Tienda), State.Warn);
      	validations.add(
    		"No aparece el bloque de \"Online\"",
    		!pageMisCompras.isPresentBlockUntil(0, TypeCompra.Online), State.Warn);
      	return validations;
    }

    @Validation
    public ChecksTM validateIsPage() throws Exception {
    	ChecksTM validations = ChecksTM.getNew();
    	int maxSecondsWait = 2;
      	validations.add(
    		"Aparece la página de \"Mis Compras\" (la esperamos hasta " + maxSecondsWait + " segundos)",
    		pageMisCompras.isPageUntil(maxSecondsWait), State.Warn);
      	validations.add(
    		"Aparece el bloque de \"Tienda\"",
    		pageMisCompras.isPresentBlockUntil(0, TypeCompra.Tienda), State.Warn);
      	validations.add(
      		"Aparece el bloque de \"Online\"",
      		pageMisCompras.isPresentBlockUntil(0, TypeCompra.Online), State.Warn);
      	return validations;
    }
    
    @Step (
    	description="Seleccionar el bloque <b>#{typeCompra}<b>", 
        expected="Se hace visible el bloque #{typeCompra}")
    public void selectBlock(TypeCompra typeCompra, boolean ordersExpected) {
    	pageMisCompras.clickBlock(typeCompra);
        int maxSecondsWait = 2;
    	checkBlockSelected(typeCompra, maxSecondsWait);
        if (ordersExpected) {
        	checkArticlesInList(typeCompra);
        } else {
        	checkListArticlesVoid(typeCompra);
        }
    }

	@Validation (
		description="Queda seleccionado el bloque de <b>#{typeCompra}</b> (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Warn)
	private boolean checkBlockSelected(TypeCompra typeCompra, int maxSecondsWait) {
		return (pageMisCompras.isSelectedBlockUntil(maxSecondsWait, typeCompra));
	}
	
	@Validation
	private ChecksTM checkArticlesInList(TypeCompra typeCompra) {
    	ChecksTM validations = ChecksTM.getNew();
    	int maxSecondsWait = 2;
    	boolean isVisibleAnyCompra = pageMisCompras.isVisibleAnyCompraUntil(maxSecondsWait); 
      	validations.add(
    		"Aparece una lista con algún artículo (lo esperamos hasta " + maxSecondsWait + " segundos)",
    		isVisibleAnyCompra, State.Warn);
      	if (isVisibleAnyCompra) {
          	validations.add(
        		"El 1er artículo es de tipo <b>" + typeCompra + "</b>",
        		pageMisCompras.getTypeCompra(1)==typeCompra, State.Warn);
      	}
		return validations;      
	}
		
	@Validation
	private ChecksTM checkListArticlesVoid(TypeCompra typeCompra) {
    	ChecksTM validations = ChecksTM.getNew();
    	boolean isVisibleAnyCompra = pageMisCompras.isVisibleAnyCompraUntil(0);
      	validations.add(
    		"No aparece ningún artículo",
    		!isVisibleAnyCompra, State.Warn);
      	validations.add(
    		"Es visible la imagen asociada a <b>Lista Vacía</b> para " + typeCompra,
    		pageMisCompras.isVisibleEmptyListImage(typeCompra), State.Warn);
      	return validations;
	}
    
    @Validation
    public ChecksTM validateIsCompraOnlineVisible(String codPedido, boolean isChequeRegalo) {
        ChecksTM validations = ChecksTM.getNew();
        State stateVal = State.Warn;
        boolean avoidEvidences = false;
        if (isChequeRegalo) {
            stateVal = State.Info;
            avoidEvidences = true;
        }
        validations.add(
        	"Es visible la compra " + TypeCompra.Online + " asociada al pedido <b>" + codPedido + "</b>",
        	pageMisCompras.isVisibleCompraOnline(codPedido), stateVal, avoidEvidences);
        return validations;
    }
    
    @Step (
    	description="Seleccionamos la #{posInLista}a compra (tipo Online) de la lista", 
        expected="Aparece la página con los detalles del pedido",
        saveHtmlPage=SaveWhen.IfProblem)
    public void selectCompraOnline(int posInLista, String codPais) throws Exception {
    	CompraOnline compraOnline = pageMisCompras.getDataCompraOnline(posInLista);
    	pageMisCompras.clickCompra(posInLista);       
        PageDetallePedidoStpV pageDetPedidoStpV = new PageDetallePedidoStpV(driver);
        pageDetPedidoStpV.validateIsPageOk(compraOnline, codPais, driver);       
    }
    
    @SuppressWarnings("static-access")
    @Step (
    	description="Seleccionamos la #{posInLista}a compra (tipo Tienda) de la lista", 
        expected="Aparece una sección con los detalles de la Compra")
    public void selectCompraTienda(int posInLista) throws Exception {
    	CompraTienda compraTienda = pageMisCompras.getDataCompraTienda(posInLista);
    	pageMisCompras.clickCompra(posInLista);       
    	secDetalleCompraTiendaStpV.validateIsOk(compraTienda);      
    }

    final static String tagReferencia = "@TagReferencia";
    @Step (
    	description="Seleccionamos link <b>Más Info</b> del 1er artículo <b>" + tagReferencia + "</b>", 
        expected="Aparece el modal con información del artículo")
	public void clickMoreInfo() {
    	String infoArticle = pageMisCompras.getReferenciaPrimerArticulo();
    	TestMaker.getCurrentStepInExecution().replaceInDescription(tagReferencia, infoArticle);
    	pageMisCompras.clickMasInfoArticulo();           
    	modalDetalleMisComprasStpV.checkModalArticle(infoArticle);
	}    
}
