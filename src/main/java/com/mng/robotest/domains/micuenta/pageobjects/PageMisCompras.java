package com.mng.robotest.domains.micuenta.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.micuenta.beans.Ticket;
import com.mng.robotest.domains.transversal.PageBase;

public class PageMisCompras extends PageBase {

	public enum TypeTicket {Tienda, Online}
	private List<Ticket> listTickets = null;
	
	private static final String XPATH_CAPA_CONTENEDORA_DESKTOP = "//micro-frontend[@id='myPurchasesDesktop']";
	private static final String XPATH_CAPA_CONTENEDORA_MOBILE = "//micro-frontend[@id='myPurchasesMobile']";
	private static final String XPATH_ID_RELATIVE_TICKET = ".//*[@data-testid[contains(.,'purchaseNumber')]]";
	
	//TODO solicitado data-testid a Carla (11-02-2022)
	private static final String XPATH_ITEMS_RELATIVE_TICKET = ".//div[@class[contains(.,'layout-row')]]//div[@class[contains(.,'layout-placeholder')] and @class[contains(.,'sg-body-small')]]";	
	
	private static final String XPATH_LIST_TICKETS = 
		"//*[@data-testid[contains(.,'activePurchases')] or " +
			"@data-testid[contains(.,'inactivePurchases')]]";
	
	private static final String XPATH_TICKET = XPATH_LIST_TICKETS + "//div[@class[contains(.,'layout-content')]]";
	private static final String XPATH_PRICE_RELATIVE_TICKET = ".//*[@data-testid='price']";	
	private static final String XPATH_FECHA_RELATIVE_TICKET = ".//span[@class[contains(.,'sg-caption-light')]]";
	
	public List<Ticket> getTickets() {
		isVisibleTicket(5);
		waitLoadPage();
		waitMillis(1000);
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
	
	public boolean isTicket(TypeTicket typeCompra, int seconds) {
		for (int i=0; i<seconds; i++) {
			List<Ticket> listTickets = getTickets(typeCompra);
			if (listTickets.size()>0) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	
	private List<WebElement> getTicketsPage() {
		state(State.Visible, XPATH_TICKET).wait(2).check();
		return getElements(XPATH_TICKET);
	}
	
	private boolean isVisibleTicket(int seconds) {
		return state(Visible, XPATH_TICKET).wait(seconds).check();
	}

	public boolean areTickets() {
		return getTickets().size()>0;
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
			.filter(item -> item.getId().compareTo(idPedido)==0)
			.findAny().isPresent());
	}
	
	public void selectTicket(String idTicket) {
		click(getXPathTicketLink(idTicket)).exec();
	}
	
	private String getXPathCapaContenedora() {
		switch (channel) {
		case mobile:
			return XPATH_CAPA_CONTENEDORA_MOBILE;
		default:
			return XPATH_CAPA_CONTENEDORA_DESKTOP;
		}
	}

	private String getXPathTicketLink(String id) {
		return (XPATH_TICKET + "//img[@loading='lazy' and @alt[contains(.,'" + id + "')]]/..");
	}
	
	public boolean isPageUntil(int seconds) {
		return state(Visible, getXPathCapaContenedora()).wait(seconds).check();
	}
	
	private Ticket getTicket(WebElement ticketScreen) {
		Ticket ticket = new Ticket();
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
		if (StringUtils.isNumeric(idMangoTicket)) {
			return TypeTicket.Tienda;
		}
		return TypeTicket.Online;
	}
	
	private String getIdTicketPage(WebElement boxDataTicket) {
		String lineaId = boxDataTicket.findElement(By.xpath(XPATH_ID_RELATIVE_TICKET)).getText();
		Pattern pattern = Pattern.compile("_*: (.*)"); boxDataTicket.getAttribute("innerHTML");
		Matcher matcher = pattern.matcher(lineaId);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}
	
	private String getPrecioTicketPage(WebElement boxDataTicket) {
		return (boxDataTicket.findElement(By.xpath(XPATH_PRICE_RELATIVE_TICKET)).getText());
	}
	
	private int getNumItemsTicketPage(WebElement boxDataTicket) {
		String textLinea = "0" + boxDataTicket.findElement(By.xpath(XPATH_ITEMS_RELATIVE_TICKET)).getText();
		return (Integer.valueOf(textLinea.replaceAll("[^0-9]", "")));
	}
	
	private String getFechaTicketPage(WebElement boxDataTicket) {
		return (boxDataTicket.findElement(By.xpath(XPATH_FECHA_RELATIVE_TICKET)).getText());
	} 
}
