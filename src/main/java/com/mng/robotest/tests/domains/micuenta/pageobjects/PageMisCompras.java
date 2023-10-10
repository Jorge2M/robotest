package com.mng.robotest.tests.domains.micuenta.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.micuenta.beans.Ticket;

public class PageMisCompras extends PageBase {

	public enum TypeTicket { TIENDA, ONLINE, UNDEFINED }
	private List<Ticket> listTickets = null;
	
	private static final String XPATH_CAPA_CONTENEDORA_DESKTOP = "//micro-frontend[@id='myPurchasesDesktop']";
	private static final String XPATH_CAPA_CONTENEDORA_MOBILE = "//micro-frontend[@id='myPurchasesMobile']";
	private static final String XPATH_ID_RELATIVE_TICKET = ".//*[@data-testid[contains(.,'purchaseNumber')]]";
	
	//TODO solicitado data-testid a Carla (11-02-2022)
	private static final String XPATH_ITEMS_RELATIVE_TICKET = ".//div[@class[contains(.,'layout-row')]]//div[@class[contains(.,'layout-placeholder')] and @class[contains(.,'md7')]]";	
	
	private static final String XPATH_LIST_TICKETS = 
		"//*[@data-testid[contains(.,'activePurchases')] or " +
			"@data-testid[contains(.,'inactivePurchases')]]";
	
	private static final String XPATH_TICKET = XPATH_LIST_TICKETS + "//div[@class[contains(.,'layout-content')]]";
	private static final String XPATH_PRICE_RELATIVE_TICKET = ".//*[@data-testid='myPurchases.price']";	
	private static final String XPATH_FECHA_RELATIVE_TICKET = ".//*[@data-testid='myPurchases.purchaseCard.date']";
	
	public List<Ticket> getTickets() {
		isVisibleTicket(5);
		waitLoadPage();
		waitMillis(1000);
		try {
			return getTicketsStaleUnsafe();
		}
		catch (StaleElementReferenceException | NoSuchElementException e) {
			waitMillis(1000);
			return getTicketsStaleUnsafe();
		}
	}
	
	private List<Ticket> getTicketsStaleUnsafe() {
		return getTicketsPage().stream()
				.map(this::getTicket)
				.toList();
	}
	
	
	public List<Ticket> getTickets(TypeTicket typeCompra) {
		if (listTickets==null || listTickets.isEmpty()) {
			listTickets = getTickets().stream()
				.filter(ticket -> ticket.getType()==typeCompra)
				.toList();
		}
		return listTickets;
	}
	
	public boolean isTicket(TypeTicket typeCompra, int seconds) {
		for (int i=0; i<seconds; i++) {
			List<Ticket> tickets = getTickets(typeCompra);
			if (!tickets.isEmpty()) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	
	private List<WebElement> getTicketsPage() {
		state(Visible, XPATH_TICKET).wait(2).check();
		return getElements(XPATH_TICKET);
	}
	
	private boolean isVisibleTicket(int seconds) {
		return state(Visible, XPATH_TICKET).wait(seconds).check();
	}

	public boolean areTickets() {
		return !getTickets().isEmpty();
	}
	
	public boolean isTicketOnline(String idPedido, int seconds) {
		for (int i=0; i<seconds; i++) {
			if (isTicketOnline(idPedido)) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	
	public boolean isTicketOnline(String idPedido) {
		return (getTickets().stream()
			.anyMatch(item -> item.getId().compareTo(idPedido)==0));
	}
	
	public void selectTicket(String idTicket) {
		click(getXPathTicketLink(idTicket)).exec();
	}
	
	private String getXPathCapaContenedora() {
		if (channel==Channel.mobile) {
			return XPATH_CAPA_CONTENEDORA_MOBILE;
		}
		return XPATH_CAPA_CONTENEDORA_DESKTOP;
	}

	private String getXPathTicketLink(String id) {
		return (XPATH_TICKET + "//img[@loading='lazy' and @alt[contains(.,'" + id + "')]]/..");
	}
	
	public boolean isPageUntil(int seconds) {
		return state(Visible, getXPathCapaContenedora()).wait(seconds).check();
	}
	
	private Ticket getTicket(WebElement ticketScreen) {
		var ticket = new Ticket();
		ticket.setType(getTypeTicketPage(ticketScreen));
		ticket.setId(getIdTicketPage(ticketScreen));
		ticket.setPrecio(getPrecioTicketPage(ticketScreen));
		ticket.setNumItems(getNumItemsTicketPage(ticketScreen));
		ticket.setFecha(getFechaTicketPage(ticketScreen));
		return ticket;
	}
	
	public Ticket selectTicket(TypeTicket type, int position) {
		Ticket ticket = getTickets(type).get(position-1);
		click(getXPathTicketLink(ticket.getId())).exec();
		return ticket;
	}
	
	private TypeTicket getTypeTicketPage(WebElement boxDataTicket) {
		String idMangoTicket = getIdTicketPage(boxDataTicket);
		if ("".compareTo(idMangoTicket)==0) {
			return TypeTicket.UNDEFINED;
		}
		if (StringUtils.isNumeric(idMangoTicket)) {
			return TypeTicket.TIENDA;
		}
		return TypeTicket.ONLINE;
	}
	
	private String getIdTicketPage(WebElement boxDataTicket) {
		By byIdTicket = By.xpath(XPATH_ID_RELATIVE_TICKET);
		if (!state(Visible, boxDataTicket).by(byIdTicket).check()) {
			return "";
		}
		String lineaId = getElement(boxDataTicket, byIdTicket).getText();
		Pattern pattern = Pattern.compile("_*: (.*)"); boxDataTicket.getAttribute("innerHTML");
		Matcher matcher = pattern.matcher(lineaId);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}
	
	private String getPrecioTicketPage(WebElement boxDataTicket) {
		return getElement(boxDataTicket, XPATH_PRICE_RELATIVE_TICKET).getText();
	}
	
	private int getNumItemsTicketPage(WebElement boxDataTicket) {
		if (!state(Visible, XPATH_ITEMS_RELATIVE_TICKET).check()) {
			return 0;
		}
		String textLinea = "0" + getElement(boxDataTicket, XPATH_ITEMS_RELATIVE_TICKET).getText();
		return Integer.valueOf(textLinea.replaceAll("[^0-9]", ""));
	}
	
	private String getFechaTicketPage(WebElement boxDataTicket) {
		return getElement(boxDataTicket, XPATH_FECHA_RELATIVE_TICKET).getText();
	} 
}