package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.Ticket;

public abstract class PageMisCompras extends PageObjTM {

	private final ModalDetalleCompraDesktop modalDetalleCompra;
	
	public enum TypeTicket {Tienda, Online}
	final Channel channel;
	private List<Ticket> listTickets = null;
	
	public abstract boolean isPageUntil(int maxSeconds);
	public abstract List<Ticket> getTickets();
	public abstract Ticket selectTicket(TypeTicket type, int position);
	
	public static PageMisCompras make(Channel channel, WebDriver driver) {
		switch (channel) {
		case desktop:
			return new PageMisComprasDesktop(driver);
		case mobile:
			return new PageMisComprasMobil(driver);
		default:
			return null;
		}
	}
	
	protected PageMisCompras(Channel channel, WebDriver driver) {
		super(driver);
		this.channel = channel;
		this.modalDetalleCompra = new ModalDetalleCompraDesktop(channel, driver);
	}
	
	public ModalDetalleCompraDesktop getModalDetalleCompra() {
		return this.modalDetalleCompra;
	}
	
	public List<Ticket> getTickets(TypeTicket typeCompra) {
		if (listTickets==null) {
			listTickets = getTickets().stream()
				.filter(ticket -> ticket.getType()==typeCompra)
				.collect(Collectors.toList());
		}
		return listTickets;
	}
	
	public boolean areTickets() {
		return getTickets().size()>0;
	}
	
	public boolean isTicketOnline(String idPedido) {
		return (getTickets().stream()
			.filter(item -> item.getId().compareTo(idPedido)==0)
			.findAny().isPresent());
	}
}
