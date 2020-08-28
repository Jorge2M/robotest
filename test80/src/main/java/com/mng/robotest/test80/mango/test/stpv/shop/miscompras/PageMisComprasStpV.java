package com.mng.robotest.test80.mango.test.stpv.shop.miscompras;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.Ticket;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.ModalDetalleMisComprasDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageMisCompras;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageMisCompras.TypeTicket;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageMisComprasDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageMisComprasMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.SecDetalleCompraTiendaDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.SecQuickViewArticuloDesktop;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.stpv.shop.pedidos.PageDetallePedidoStpV;

public class PageMisComprasStpV {
	
	private final WebDriver driver;
	private final Channel channel;
    private final PageMisCompras pageMisCompras;
    private final SecDetalleCompraTiendaStpV secDetalleCompraTiendaStpV; 
    private final SecQuickViewArticuloStpV secQuickViewArticuloStpV;
    private final ModalDetalleMisComprasStpV modalDetalleMisComprasStpV;
    
    private PageMisComprasStpV(Channel channel, WebDriver driver) {
    	this.driver = driver;
    	this.channel = channel;
    	this.pageMisCompras = PageMisCompras.make(channel, driver);
    	SecDetalleCompraTiendaDesktop secDetalle = pageMisCompras.getSecDetalleCompraTienda();
    	SecQuickViewArticuloDesktop secQuickView = pageMisCompras.getSecQuickViewArticulo();
    	ModalDetalleMisComprasDesktop modalDetalle = pageMisCompras.getModalDetalleMisCompras();
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
    
    public void validateIsPage(Pais pais) {
        if (pais.isTicketStoreEnabled()) {
            validateIsPage();
        } else {
        	validateIsPage_WhenOnlyOnlineTickets();
            StdValidationFlags flagsVal = StdValidationFlags.newOne();
            flagsVal.validaSEO = true;
            flagsVal.validaJS = true;
            flagsVal.validaImgBroken = false;
            AllPagesStpV.validacionesEstandar(flagsVal, driver);
        }
    }

	@Validation
	private ChecksTM validateIsPage_WhenOnlyOnlineTickets() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSecondsToWait = 2;
		validations.add(
			"Aparece la página de \"Mis Compras\" (la esperamos hasta " + maxSecondsToWait + " segundos)",
			pageMisCompras.isPageUntil(maxSecondsToWait), State.Warn);
		
		if (channel==Channel.desktop) {
			PageMisComprasDesktop pageMisComprasDesktop = (PageMisComprasDesktop)pageMisCompras;
			validations.add(
				"No aparece el bloque de \"Tienda\"",
				!pageMisComprasDesktop.isPresentBlockUntil(0, TypeTicket.Tienda), State.Warn);
			validations.add(
				"No aparece el bloque de \"Online\"",
				!pageMisComprasDesktop.isPresentBlockUntil(0, TypeTicket.Online), State.Warn);
		} 
		else {
			PageMisComprasMobil pageMisComprasMobil = (PageMisComprasMobil)pageMisCompras;
			validations.add(
				"Existe algún tícket de tipo \"Online\"",
				!pageMisComprasMobil.getTickets(TypeTicket.Tienda).size()>0, State.Warn);
			validations.add(
				"No hay ningún tícket de tipo \"Tienda\"",
				!pageMisComprasMobil.getTickets(TypeTicket.Online).size()==0, State.Warn);
		}
			
		return validations;
	}

    @Validation
    public ChecksTM validateIsPage() {
    	ChecksTM validations = ChecksTM.getNew();
    	int maxSeconds = 2;
      	validations.add(
    		"Aparece la página de \"Mis Compras\" (la esperamos hasta " + maxSeconds + " segundos)",
    		pageMisCompras.isPageUntil(maxSeconds), State.Warn);
      	validations.add(
    		"Aparece el bloque de \"Tienda\"",
    		pageMisCompras.isPresentBlockUntil(0, TypeTicket.Tienda), State.Warn);
      	validations.add(
      		"Aparece el bloque de \"Online\"",
      		pageMisCompras.isPresentBlockUntil(0, TypeTicket.Online), State.Warn);
      	return validations;
    }
    
    @Step (
    	description="Seleccionar el bloque <b>#{typeCompra}<b>", 
        expected="Se hace visible el bloque #{typeCompra}")
    public void selectBlock(TypeTicket typeCompra, boolean ordersExpected) {
    	pageMisCompras.clickBlock(typeCompra);
        int maxSeconds = 2;
    	checkBlockSelected(typeCompra, maxSeconds);
        if (ordersExpected) {
        	checkArticlesInList(typeCompra);
        } else {
        	checkListArticlesVoid(typeCompra);
        }
    }

	@Validation (
		description="Queda seleccionado el bloque de <b>#{typeCompra}</b> (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	private boolean checkBlockSelected(TypeTicket typeCompra, int maxSeconds) {
		return (pageMisCompras.isSelectedBlockUntil(maxSeconds, typeCompra));
	}
	
	@Validation
	private ChecksTM checkArticlesInList(TypeTicket typeCompra) {
    	ChecksTM validations = ChecksTM.getNew();
    	int maxSeconds = 2;
    	boolean isVisibleAnyCompra = pageMisCompras.isVisibleAnyCompraUntil(maxSeconds); 
      	validations.add(
    		"Aparece una lista con algún artículo (lo esperamos hasta " + maxSeconds + " segundos)",
    		isVisibleAnyCompra, State.Warn);
      	if (isVisibleAnyCompra) {
          	validations.add(
        		"El 1er artículo es de tipo <b>" + typeCompra + "</b>",
        		pageMisCompras.getTypeCompra(1)==typeCompra, State.Warn);
      	}
		return validations;      
	}
		
	@Validation
	private ChecksTM checkListArticlesVoid(TypeTicket typeCompra) {
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
        	"Es visible la compra " + TypeTicket.Online + " asociada al pedido <b>" + codPedido + "</b>",
        	pageMisCompras.isVisibleCompraOnline(codPedido), stateVal, avoidEvidences);
        return validations;
    }
    
    @Step (
    	description="Seleccionamos la #{posInLista}a compra (tipo Online) de la lista", 
        expected="Aparece la página con los detalles del pedido",
        saveHtmlPage=SaveWhen.IfProblem)
    public void selectCompraOnline(int posInLista, String codPais) {
    	Ticket compraOnline = pageMisCompras.getDataCompraOnline(posInLista);
    	pageMisCompras.clickCompra(posInLista);       
        PageDetallePedidoStpV pageDetPedidoStpV = new PageDetallePedidoStpV(driver);
        pageDetPedidoStpV.validateIsPageOk(compraOnline, codPais, driver);       
    }
    
    @SuppressWarnings("static-access")
    @Step (
    	description="Seleccionamos la #{posInLista}a compra (tipo Tienda) de la lista", 
        expected="Aparece una sección con los detalles de la Compra")
    public void selectCompraTienda(int posInLista) {
    	TicketOnline compraTienda = pageMisCompras.getDataCompraTienda(posInLista);
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
