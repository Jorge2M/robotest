package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.Ticket;

public abstract class PageMisCompras extends PageObjTM {

	private final PageDetalleCompra modalDetalleCompra;
	
	public enum TypeTicket {Tienda, Online}
	final Channel channel;
	private List<Ticket> listTickets = null;
	
	public abstract boolean isPageUntil(int maxSeconds);
	public abstract String getXPathTicket();
	public abstract Ticket getTicket(WebElement ticketScreen);
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
		this.modalDetalleCompra = PageDetalleCompra.make(channel, driver);
	}
	
	public PageDetalleCompra getModalDetalleCompra() {
		return this.modalDetalleCompra;
	}
	
	public List<Ticket> getTickets() {
		isVisibleTicket(5);
		return getTicketsPage().stream()
				.map(item -> getTicket(item))
				.collect(Collectors.toList());
	}
	
	public List<Ticket> getTickets(TypeTicket typeCompra) {
		if (listTickets==null) {
			listTickets = getTickets().stream()
				.filter(ticket -> ticket.getType()==typeCompra)
				.collect(Collectors.toList());
		}
		return listTickets;
	}
	
	public boolean isTicket(TypeTicket typeCompra, int maxSeconds) {
		for (int i=0; i<maxSeconds; i++) {
			List<Ticket> listTickets = getTickets(typeCompra);
			if (listTickets.size()>0) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	
    private List<WebElement> getTicketsPage() {
    	String xpathTicket = getXPathTicket();
    	state(State.Visible, By.xpath(xpathTicket)).wait(2).check();
        return (driver.findElements(By.xpath(xpathTicket)));
    }
	
	private boolean isVisibleTicket(int maxSeconds) {
		String xpathTicket = getXPathTicket();
		return (state(Visible, By.xpath(xpathTicket)).wait(maxSeconds).check());
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
