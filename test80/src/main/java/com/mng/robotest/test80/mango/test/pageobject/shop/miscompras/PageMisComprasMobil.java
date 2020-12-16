package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.Ticket;

public class PageMisComprasMobil extends PageMisCompras {

	private static String XPathCapaContenedora = "//div[@id[contains(.,'myPurchases')]]";
	private static String XPathTicket = XPathCapaContenedora + "//*[@class[contains(.,'_31R3B')] or @class[contains(.,'B1UJq')]]"; //React
	private static String XPathInfoTicket = "//span[@class[contains(.,'sg-subtitle-small')]]";
	private static String XPathVerDetalleTicket = "//a[@class='sg-button-primary']";
	private static String XPathNumItemsTicket = ".//div[@class='_2Hl12']"; //React
	
    private String getXPathTicket(String id) {
    	return (getXPathTicket() + XPathInfoTicket + "[1]" + "//self::*[text()='" + id + "']");
    }
    private String getXPathVerDetalleTicket(String id) {
    	return (XPathVerDetalleTicket + "//self::*[@href[contains(.,'" + id + "')]]");
    }
	
	public PageMisComprasMobil(WebDriver driver) {
		super(Channel.mobile, driver);
	}
	
	@Override
	public String getXPathTicket() {
		return XPathTicket;
	}
	
	@Override
	public boolean isPageUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathCapaContenedora)).wait(maxSeconds).check());
	}

	@Override
	public Ticket selectTicket(TypeTicket type, int position) {
		Ticket ticket = getTickets(type).get(position-1);
		By byVerDetalle = By.xpath(getXPathVerDetalleTicket(ticket.getId()));
		if (state(State.Visible, byVerDetalle).check()) {
			driver.findElement(byVerDetalle).click();
		} else {
			By byTicket = By.xpath(getXPathTicket(ticket.getId()));
			driver.findElement(byTicket).click();
		}
		return ticket;
	}
	
    @Override
	public Ticket getTicket(WebElement ticketScreen) {
		Ticket ticket = new Ticket();
		ticket.setType(getTypeTicketPage(ticketScreen));
		ticket.setId(getIdTicketPage(ticketScreen));
		ticket.setPrecio(getPrecioTicketPage(ticketScreen));
		ticket.setNumItems(getNumItemsTicketPage(ticketScreen));
		ticket.setFecha(getFechaTicketPage(ticketScreen));
		return ticket;
	}

	private TypeTicket getTypeTicketPage(WebElement boxDataTicket) {
		String idTicket = getIdTicketPage(boxDataTicket);
        if (StringUtils.isNumeric(idTicket)) {
            return TypeTicket.Tienda;
        }
        return TypeTicket.Online;
	}
	private String getIdTicketPage(WebElement boxDataTicket) {
	    return (boxDataTicket.findElements(By.xpath("." + XPathInfoTicket)).get(0).getText());
	}
	private String getPrecioTicketPage(WebElement boxDataTicket) {
	    return (boxDataTicket.findElements(By.xpath("." + XPathInfoTicket)).get(1).getText());
	}
	private int getNumItemsTicketPage(WebElement boxDataTicket) {
		By byNumItems = By.xpath(XPathNumItemsTicket);
		if (state(State.Visible, boxDataTicket).by(byNumItems).check()) {
			String textLinea = boxDataTicket.findElement(byNumItems).getText();
			return (Integer.valueOf(textLinea.replaceAll("[^0-9]", "")));
		}
		return 1;
	}
	private String getFechaTicketPage(WebElement boxDataTicket) {
	    return (boxDataTicket.findElements(By.xpath("." + XPathInfoTicket)).get(3).getText());
	}
}
