package com.mng.robotest.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.micuenta.beans.Ticket;
import com.mng.robotest.domains.micuenta.pageobjects.PageMisCompras;
import com.mng.robotest.domains.micuenta.pageobjects.PageMisCompras.TypeTicket;
import com.mng.robotest.test.utils.UtilsTest;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageMisComprasSteps extends StepBase {
	
	private final PageMisCompras pageMisCompras = new PageMisCompras();
	private final ModalDetalleCompraSteps modalDetalleCompraSteps = new ModalDetalleCompraSteps();

	@Validation
	public ChecksTM validateIsPage() {
		var checks = ChecksTM.getNew();
		int seconds = 5;
		checks.add(
			"Aparece la página de \"Mis Compras\" (la esperamos hasta " + seconds + " segundos)",
			pageMisCompras.isPageUntil(seconds), Warn);
		
		return checks;
	}
		
	@Validation
	private ChecksTM checkListArticlesVoid(TypeTicket typeCompra) {
		var checks = ChecksTM.getNew();
	  	checks.add(
			"No aparece ningún tícket",
			!pageMisCompras.areTickets(), Warn);
	  	return checks;
	}
	
	public boolean validateIsCompraOnline(String codPedido) {
		return validateIsCompraOnline(codPedido, false);
	}
	
	public boolean validateIsCompraOnline(String codPedido, boolean isChequeRegalo) {
		if (isChequeRegalo) {
			return validateIsCompraOnlineChequeRegalo(codPedido, 2);
		}
		return checkIsCompraOnlinePrendas(codPedido, 2);
	}

	//TODO cuando funcione Mis Compras volver a poner esta validación
//	@Validation (
//		description=
//			"Es visible la compra Online asociada al pedido <b>#{codPedido}</b> " + SECONDS_WAIT)
//	private boolean validateIsCompraOnlinePrendas(String codPedido, int seconds) {
//		return pageMisCompras.isTicketOnline(codPedido, seconds);
//	}	
	
	private boolean checkIsCompraOnlinePrendas(String codPedido, int seconds) {
		return 
			checkIsCompraOnlinePrendasCheck(codPedido, seconds)
			.areAllChecksOvercomed();
	}
	
	@Validation
	private ChecksTM checkIsCompraOnlinePrendasCheck(String codPedido, int seconds) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Es visible la compra Online asociada al pedido <b>" + codPedido + "</b> (la esperamos " + seconds + " segundos",
			pageMisCompras.isTicketOnline(codPedido, seconds), getLevel());
		
		return checks;		
	}	
	private State getLevel() {
		if (dataTest.isUserRegistered()) { 
			return (UtilsTest.todayBeforeDate("2023-10-01")) ? State.Warn : Defect;
		}
		return State.Defect;
	}
	
	
	@Validation (
		description=
			"Es visible la compra Online asociada al pedido <b>#{codPedido}</b> " + SECONDS_WAIT,
		level=Info,
		store=StoreType.None)
	private boolean validateIsCompraOnlineChequeRegalo(String codPedido, int seconds) {
		return pageMisCompras.isTicketOnline(codPedido, seconds);
	}	
	
	@Validation (
		description="Es visible una compra de tipo #{typeTicket} " + SECONDS_WAIT)
	public boolean validateIsCompraOfType(TypeTicket typeTicket, int seconds) {
		return pageMisCompras.isTicket(typeTicket, seconds);
	}
	
	@Step (
		description="Seleccionamos la #{posInLista}a compra (tipo Online) de la lista", 
		expected="Aparece la página con los detalles del pedido",
		saveHtmlPage=SaveWhen.IfProblem)
	public void selectCompraOnline(int posInLista, String codPais) {
		Ticket ticket = pageMisCompras.selectTicket(TypeTicket.Online, posInLista);	   
		new PageDetallePedidoSteps().validateIsPageOk(ticket, codPais);	   
	}

	@Step (
		description="Seleccionamos la #{posInLista}a compra (tipo Tienda) de la lista", 
		expected="Aparece una sección con los detalles de la Compra")
	public void selectCompraTienda(int posInLista) {
		Ticket ticket = pageMisCompras.selectTicket(TypeTicket.Tienda, posInLista);	 
		modalDetalleCompraSteps.validateIsOk(ticket);	  
	}
	
	@Step (
		description="Seleccionamos el ticket <b>#{idTicket}</b> de la lista", 
		expected="Aparece una sección con los detalles de la Compra")
	public void selectCompra(String idTicket) {
		pageMisCompras.selectTicket(idTicket);	  
		modalDetalleCompraSteps.checkIsDataVisible();
	}	
	
	public void clickDetalleArticulo(int posArticulo) {
		modalDetalleCompraSteps.selectArticulo(posArticulo);
	}
	
}
