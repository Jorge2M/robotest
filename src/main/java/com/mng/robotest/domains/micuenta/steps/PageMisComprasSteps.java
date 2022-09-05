package com.mng.robotest.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.micuenta.beans.Ticket;
import com.mng.robotest.domains.micuenta.pageobjects.PageMisCompras;
import com.mng.robotest.domains.micuenta.pageobjects.PageMisCompras.TypeTicket;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.steps.shop.pedidos.PageDetallePedidoSteps;

public class PageMisComprasSteps extends StepBase {
	
	private final PageMisCompras pageMisCompras = new PageMisCompras(channel, app);
	private final ModalDetalleCompraSteps modalDetalleCompraSteps = 
			ModalDetalleCompraSteps.getNew(pageMisCompras.getModalDetalleCompra());

	public ModalDetalleCompraSteps getModalDetalleCompra() {
		return this.modalDetalleCompraSteps;
	}
	
	@Validation
	public ChecksTM validateIsPage() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSecondsToWait = 5;
		checks.add(
			"Aparece la página de \"Mis Compras\" (la esperamos hasta " + maxSecondsToWait + " segundos)",
			pageMisCompras.isPageUntil(maxSecondsToWait), State.Warn);
		
		return checks;
	}
		
	@Validation
	private ChecksTM checkListArticlesVoid(TypeTicket typeCompra) {
		ChecksTM checks = ChecksTM.getNew();
	  	checks.add(
			"No aparece ningún tícket",
			!pageMisCompras.areTickets(), State.Warn);
	  	return checks;
	}
	
	@Validation
	public ChecksTM validateIsCompraOnline(String codPedido, boolean isChequeRegalo) {
		ChecksTM checks = ChecksTM.getNew();
		State stateVal = State.Warn;
		StoreType store = StoreType.Evidences;
		if (isChequeRegalo) {
			stateVal = State.Info;
			store = StoreType.None;
		}
		checks.add(
		    Check.make(
			    "Es visible la compra " + TypeTicket.Online + " asociada al pedido <b>" + codPedido + "</b>",
			    pageMisCompras.isTicketOnline(codPedido), stateVal)
		    .store(store).build());
		
		return checks;
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
		PageDetallePedidoSteps pageDetPedidoSteps = new PageDetallePedidoSteps(channel, app);
		pageDetPedidoSteps.validateIsPageOk(ticket, codPais);	   
	}
	
	@SuppressWarnings("static-access")
	@Step (
		description="Seleccionamos la #{posInLista}a compra (tipo Tienda) de la lista", 
		expected="Aparece una sección con los detalles de la Compra")
	public void selectCompraTienda(int posInLista) {
		Ticket ticket = pageMisCompras.selectTicket(TypeTicket.Tienda, posInLista);	 
		modalDetalleCompraSteps.validateIsOk(ticket);	  
	}

	public void clickDetalleArticulo(int posArticulo) {
		modalDetalleCompraSteps.selectArticulo(posArticulo);
	}
//	public void clickBuscarTiendaArticulo_Desktop() {
//		modalDetalleCompraSteps.getModalDetalleArticulo().clickBuscarTiendaButton_Desktop();
//	}
//	public void clickCloseBuscarTiendaArticulo_Desktop() throws Exception {
//		modalDetalleCompraSteps.getModalDetalleArticulo().clickCloseModalBuscadorTiendas_Desktop();
//	}
	public void closeArticuloModal() {
		
	}
	
	public void gotoMisComprasFromDetalleCompra() {
		modalDetalleCompraSteps.gotoListaMisCompras();
	}
 
}
