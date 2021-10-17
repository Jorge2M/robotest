package com.mng.robotest.test80.mango.test.stpv.shop.miscompras;


import org.openqa.selenium.WebDriver;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.Ticket;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageMisCompras;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageMisCompras.TypeTicket;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageDetalleCompra;
import com.mng.robotest.test80.mango.test.stpv.shop.pedidos.PageDetallePedidoStpV;

public class PageMisComprasStpV {
	
	private final WebDriver driver;
	private final Channel channel;
    private final PageMisCompras pageMisCompras;
    private final ModalDetalleCompraStpV modalDetalleCompraStpV; 

    private PageMisComprasStpV(Channel channel, AppEcom app, WebDriver driver) {
    	this.driver = driver;
    	this.channel = channel;
    	this.pageMisCompras = new PageMisCompras(channel, driver);
    	PageDetalleCompra secDetalle = pageMisCompras.getModalDetalleCompra();
    	this.modalDetalleCompraStpV = ModalDetalleCompraStpV.getNew(secDetalle);

    }
    public static PageMisComprasStpV getNew(Channel channel, AppEcom app, WebDriver driver) {
    	return new PageMisComprasStpV(channel, app, driver);
    }
    
    public ModalDetalleCompraStpV getModalDetalleCompra() {
    	return this.modalDetalleCompraStpV;
    }
    
    @Validation
    public ChecksTM validateIsPage(Pais pais) {
    	ChecksTM validations = ChecksTM.getNew();
		int maxSecondsToWait = 5;
		validations.add(
			"Aparece la página de \"Mis Compras\" (la esperamos hasta " + maxSecondsToWait + " segundos)",
			pageMisCompras.isPageUntil(maxSecondsToWait), State.Warn);
		
		return validations;
    }
		
	@Validation
	private ChecksTM checkListArticlesVoid(TypeTicket typeCompra) {
    	ChecksTM validations = ChecksTM.getNew();
      	validations.add(
    		"No aparece ningún tícket",
    		!pageMisCompras.areTickets(), State.Warn);
      	return validations;
	}
    
    @Validation
    public ChecksTM validateIsCompraOnline(String codPedido, boolean isChequeRegalo) {
        ChecksTM validations = ChecksTM.getNew();
        State stateVal = State.Warn;
        boolean avoidEvidences = false;
        if (isChequeRegalo) {
            stateVal = State.Info;
            avoidEvidences = true;
        }
        validations.add(
        	"Es visible la compra " + TypeTicket.Online + " asociada al pedido <b>" + codPedido + "</b>",
        	pageMisCompras.isTicketOnline(codPedido), stateVal, avoidEvidences);
        return validations;
    }
    
	@Validation (
		description="Es visible una compra de tipo #{typeTicket} (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
    public boolean validateIsCompraOfType(TypeTicket typeTicket, int maxSeconds) {
		return pageMisCompras.isTicket(typeTicket, maxSeconds);
    }
    
    @Step (
    	description="Seleccionamos la #{posInLista}a compra (tipo Online) de la lista", 
        expected="Aparece la página con los detalles del pedido",
        saveHtmlPage=SaveWhen.IfProblem)
    public void selectCompraOnline(int posInLista, String codPais) {
    	Ticket ticket = pageMisCompras.selectTicket(TypeTicket.Online, posInLista);       
        PageDetallePedidoStpV pageDetPedidoStpV = new PageDetallePedidoStpV(channel, driver);
        pageDetPedidoStpV.validateIsPageOk(ticket, codPais, driver);       
    }
    
    @SuppressWarnings("static-access")
    @Step (
    	description="Seleccionamos la #{posInLista}a compra (tipo Tienda) de la lista", 
        expected="Aparece una sección con los detalles de la Compra")
    public void selectCompraTienda(int posInLista) {
    	Ticket ticket = pageMisCompras.selectTicket(TypeTicket.Tienda, posInLista);     
    	modalDetalleCompraStpV.validateIsOk(ticket);      
    }

    public void clickDetalleArticulo(int posArticulo) {
    	modalDetalleCompraStpV.selectArticulo(posArticulo);
    }
//    public void clickBuscarTiendaArticulo_Desktop() {
//    	modalDetalleCompraStpV.getModalDetalleArticulo().clickBuscarTiendaButton_Desktop();
//    }
//    public void clickCloseBuscarTiendaArticulo_Desktop() throws Exception {
//    	modalDetalleCompraStpV.getModalDetalleArticulo().clickCloseModalBuscadorTiendas_Desktop();
//    }
    public void closeArticuloModal() {
    	
    }
    
    public void gotoMisComprasFromDetalleCompra() {
    	modalDetalleCompraStpV.gotoListaMisCompras();
    }
 
}
