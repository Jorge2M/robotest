package com.mng.robotest.test.pageobject.shop.miscompras;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test.pageobject.shop.micuenta.Ticket;

public class PageMisCompras extends PageObjTM {

	private final PageDetalleCompra modalDetalleCompra;
	
	public enum TypeTicket {Tienda, Online}
	final Channel channel;
	private List<Ticket> listTickets = null;
	
	private final static String XPathCapaContenedoraDesktop = "//micro-frontend[@id='myPurchasesDesktop']";
	private final static String XPathCapaContenedoraMobile = "//micro-frontend[@id='myPurchasesMobile']";
	
	private final static String XPathListTickets = 
		"//*[@data-testid[contains(.,'activePurchases')] or " +
			"@data-testid[contains(.,'inactivePurchases')]]";
	
	//private final static String XPathTicket = XPathListTickets + "//div[@class[contains(.,'purchase-card__border')]]";
	private final static String XPathTicket = XPathListTickets + "/div[@class[contains(.,'layout-content')]]";

	
	public PageMisCompras(Channel channel, WebDriver driver) {
		super(driver);
		this.channel = channel;
		this.modalDetalleCompra = PageDetalleCompra.make(channel, driver);
	}
	
	public PageDetalleCompra getModalDetalleCompra() {
		return this.modalDetalleCompra;
	}
	
	public List<Ticket> getTickets() {
		isVisibleTicket(5);
		waitForPageLoaded(driver);
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
		state(State.Visible, By.xpath(XPathTicket)).wait(2).check();
		return (driver.findElements(By.xpath(XPathTicket)));
	}
	
	private boolean isVisibleTicket(int maxSeconds) {
		return (state(Visible, By.xpath(XPathTicket)).wait(maxSeconds).check());
	}

	
	public boolean areTickets() {
		return getTickets().size()>0;
	}
	
	public boolean isTicketOnline(String idPedido) {
		return (getTickets().stream()
			.filter(item -> item.getId().compareTo(idPedido)==0)
			.findAny().isPresent());
	}
	
	private String getXPathCapaContenedora() {
		switch (channel) {
		case mobile:
			return XPathCapaContenedoraMobile;
		default:
			return XPathCapaContenedoraDesktop;
		}
	}

	private String getXPathTicketLink(String id) {
		return (XPathTicket + "//img[@loading='lazy' and @alt[contains(.,'" + id + "')]]/..");
	}
	
	public boolean isPageUntil(int maxSeconds) {
		By byCapa = By.xpath(getXPathCapaContenedora());
		return (state(Visible, byCapa).wait(maxSeconds).check());
	}
	
	public Ticket getTicket(WebElement ticketScreen) {
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
		By byTicket = By.xpath(getXPathTicketLink(ticket.getId()));
		click(byTicket).exec();
		return ticket;
	}
	
	private TypeTicket getTypeTicketPage(WebElement boxDataTicket) {
		String idMangoTicket = getIdTicketPage(boxDataTicket);
		if (StringUtils.isNumeric(idMangoTicket)) {
			return TypeTicket.Tienda;
		}
		return TypeTicket.Online;
	}
	
	//private final static String XPathIdRelativeTicket = ".//div[@class[contains(.,'card__info')]]/div/div";
	//TODO solicitado data-testid a Carla (11-02-2022)
	private final static String XPathIdRelativeTicket = ".//div[@class[contains(.,'layout-row')]]//div[@class[contains(.,'layout-placeholder')] and @class[contains(.,'sg-body-small')]]";
	private String getIdTicketPage(WebElement boxDataTicket) {
		String lineaId = boxDataTicket.findElement(By.xpath(XPathIdRelativeTicket)).getText();
		Pattern pattern = Pattern.compile("_*: (.*)"); boxDataTicket.getAttribute("innerHTML");
		Matcher matcher = pattern.matcher(lineaId);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}
	
	private final static String XPathPriceRelativeTicket = ".//div[@class[contains(.,'sg-headline')]]";
	private String getPrecioTicketPage(WebElement boxDataTicket) {
		return (boxDataTicket.findElement(By.xpath(XPathPriceRelativeTicket)).getText());
	}
	
	//private final static String XPathItemsRelativeTicket = ".//div[@class[contains(.,'card__info')]]/div/div[2]";
	//TODO solicitado data-testid a Carla (11-02-2022)
	private final static String XPathItemsRelativeTicket = ".//div[@class[contains(.,'layout-row')]]//div[@class[contains(.,'layout-placeholder')] and @class[contains(.,'sg-body-small')]][2]";
	private int getNumItemsTicketPage(WebElement boxDataTicket) {
		String textLinea = "0" + boxDataTicket.findElement(By.xpath(XPathItemsRelativeTicket)).getText();
		return (Integer.valueOf(textLinea.replaceAll("[^0-9]", "")));
	}
	
	private final static String XPathFechaRelativeTicket = ".//span[@class[contains(.,'sg-caption-light')]]";
	private String getFechaTicketPage(WebElement boxDataTicket) {
		return (boxDataTicket.findElement(By.xpath(XPathFechaRelativeTicket)).getText());
	} 
}
