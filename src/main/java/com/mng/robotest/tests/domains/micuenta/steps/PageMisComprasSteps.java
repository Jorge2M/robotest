package com.mng.robotest.tests.domains.micuenta.steps;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.micuenta.beans.Ticket;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageMisCompras;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageMisCompras.TypeTicket;
import com.mng.robotest.testslegacy.utils.UtilsTest;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.conf.StoreType.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class PageMisComprasSteps extends StepBase {
	
	private final PageMisCompras pgMisCompras = new PageMisCompras();
	private final ModalDetalleCompraSteps mdDetalleCompraSteps = new ModalDetalleCompraSteps();

	@Validation
	public ChecksTM validateIsPage() {
		var checks = ChecksTM.getNew();
		int seconds = 5;
		checks.add(
			"Aparece la página de \"Mis Compras\" " + getLitSecondsWait(seconds),
			pgMisCompras.isPage(seconds), WARN);
		
		return checks;
	}
		
	@Validation
	private ChecksTM checkListArticlesVoid(TypeTicket typeCompra) {
		var checks = ChecksTM.getNew();
	  	checks.add(
			"No aparece ningún tícket",
			!pgMisCompras.areTickets(), WARN);
	  	return checks;
	}
	
	public boolean checkIsCompraOnline(String codPedido) {
		return checkIsCompraOnline(codPedido, false);
	}
	
	public boolean checkIsCompraOnline(String codPedido, boolean isChequeRegalo) {
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
			"Es visible la compra Online asociada al pedido <b>" + codPedido + "</b> " + getLitSecondsWait(seconds),
			pgMisCompras.isTicketOnline(codPedido, seconds), getLevel());
		
		return checks;		
	}	
	private State getLevel() {
		if (dataTest.isUserRegistered()) { 
			return (UtilsTest.todayBeforeDate("2024-01-31")) ? WARN : DEFECT;
		}
		return DEFECT;
	}
	
	
	@Validation (
		description=
			"Es visible la compra Online asociada al pedido <b>#{codPedido}</b> " + SECONDS_WAIT,
		level=INFO,	store=NONE)
	private boolean validateIsCompraOnlineChequeRegalo(String codPedido, int seconds) {
		return pgMisCompras.isTicketOnline(codPedido, seconds);
	}	
	
	@Validation (description="Es visible una compra de tipo #{typeTicket} " + SECONDS_WAIT)
	public boolean validateIsCompraOfType(TypeTicket typeTicket, int seconds) {
		return pgMisCompras.isTicket(typeTicket, seconds);
	}
	
	@Step (
		description="Seleccionamos la #{posInLista}a compra (tipo Online) de la lista", 
		expected="Aparece la página con los detalles del pedido",
		saveHtmlPage=IF_PROBLEM)
	public void selectCompraOnline(int posInLista) {
		Ticket ticket = pgMisCompras.selectTicket(TypeTicket.ONLINE, posInLista);	   
		new PageDetallePedidoSteps().validateIsPageOk(ticket);	   
	}

	@Step (
		description="Seleccionamos la #{posInLista}a compra (tipo Tienda) de la lista", 
		expected="Aparece una sección con los detalles de la Compra")
	public void selectCompraTienda(int posInLista) {
		Ticket ticket = pgMisCompras.selectTicket(TypeTicket.TIENDA, posInLista);	 
		mdDetalleCompraSteps.validateIsOk(ticket);	  
	}
	
	@Step (
		description="Seleccionamos el ticket <b>#{idTicket}</b> de la lista", 
		expected="Aparece una sección con los detalles de la Compra")
	public void selectCompra(String idTicket) {
		pgMisCompras.selectTicket(idTicket);	  
		mdDetalleCompraSteps.checkIsDataVisible();
	}	
	
	public void clickDetalleArticulo(int posArticulo) {
		mdDetalleCompraSteps.selectArticulo(posArticulo);
	}
	
}
