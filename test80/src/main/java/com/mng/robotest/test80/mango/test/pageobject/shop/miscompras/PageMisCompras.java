package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.Ticket;

public abstract class PageMisCompras extends PageObjTM {

	public enum TypeTicket {Tienda, Online}
	
	final Channel channel;
	
	private final SecDetalleCompraTiendaDesktop secDetalleCompraTienda;
	private final SecQuickViewArticuloDesktop secQuickViewArticulo;
	private final ModalDetalleMisComprasDesktop modalDetalleMisCompras;
	
	public abstract boolean isPageUntil(int maxSeconds);
	public abstract List<Ticket> getTickets();
	
	public static PageMisCompras make(Channel channel, WebDriver driver) {
		switch (channel) {
		case desktop:
			return new PageMisComprasMobil(driver);
		case mobile:
			return new PageMisComprasDesktop(driver);
		default:
			return null;
		}
	}
	
	protected PageMisCompras(Channel channel, WebDriver driver) {
		super(driver);
		this.channel = channel;
		this.secDetalleCompraTienda = SecDetalleCompraTiendaDesktop.getNew(driver);
		this.secQuickViewArticulo = SecQuickViewArticuloDesktop.getNew(driver);
		this.modalDetalleMisCompras = ModalDetalleMisComprasDesktop.getNew(channel, driver);
	}
	
	public SecDetalleCompraTiendaDesktop getSecDetalleCompraTienda() {
		return this.secDetalleCompraTienda;
	}
	public SecQuickViewArticuloDesktop getSecQuickViewArticulo() {
		return this.secQuickViewArticulo;
	}
	public ModalDetalleMisComprasDesktop getModalDetalleMisCompras() {
		return this.modalDetalleMisCompras;
	}
	
	public List<Ticket> getTickets(TypeTicket typeCompra) {
		return (
			getTickets().stream()
				.filter(ticket -> ticket.getType()==typeCompra)
				.collect(Collectors.toList()));
	}
	
}
