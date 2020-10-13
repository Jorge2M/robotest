package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.Ticket;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMisComprasDesktop extends PageMisCompras {

	private final static String XPathCapaContenedora = "//div[@id='myPurchasesDesktop']";
	private final static String XPathListTickets = "//div[@class[contains(.,'_3UUJa')]]"; //React
	private final static String prefixIdCompra = "purchase-";
	private final static String XPathTicket = XPathListTickets + "//div[@id[contains(.,'" + prefixIdCompra + "')]]";

	public PageMisComprasDesktop(WebDriver driver) {
		super(Channel.desktop, driver);
	}

    private String getXPathTicket(String id) {
    	return (getXPathTicket() + "//a[@href[contains(.,'"+ id + "')]]");
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
	public Ticket getTicket(WebElement ticketScreen) {
		Ticket ticket = new Ticket();
		ticket.setType(getTypeTicketPage(ticketScreen));
		ticket.setId(getIdTicketPage(ticketScreen));
		ticket.setIdMango(getIdMangoTicketPage(ticketScreen));
		ticket.setPrecio(getPrecioTicketPage(ticketScreen));
		ticket.setNumItems(getNumItemsTicketPage(ticketScreen));
		ticket.setFecha(getFechaTicketPage(ticketScreen));
		return ticket;
	}
	
	@Override
	public Ticket selectTicket(TypeTicket type, int position) {
		Ticket ticket = getTickets(type).get(position-1);
		By byTicket = By.xpath(getXPathTicket(ticket.getIdMango()));
		driver.findElement(byTicket).click();
		return ticket;
	}
	
	private TypeTicket getTypeTicketPage(WebElement boxDataTicket) {
		String idMangoTicket = getIdMangoTicketPage(boxDataTicket);
        if (StringUtils.isNumeric(idMangoTicket)) {
            return TypeTicket.Tienda;
        }
        return TypeTicket.Online;
	}
	private String getIdTicketPage(WebElement boxDataTicket) {
	    return (boxDataTicket.findElement(By.xpath("//p[@class[contains(.,'_3G4Rb')]]/span[2]")).getText()); //React
	}
	private String getIdMangoTicketPage(WebElement boxDataTicket) {
		return (boxDataTicket.getAttribute("id").replace(prefixIdCompra, ""));
	}
	private String getPrecioTicketPage(WebElement boxDataTicket) {
		return (boxDataTicket.findElement(By.xpath("//h1[@class[contains(.,'_2EhpI')]]")).getText()); //React
	}
	private int getNumItemsTicketPage(WebElement boxDataTicket) {
		String textLinea = boxDataTicket.findElement(By.xpath("//p[@class='sg-body-small']")).getText();
		return (Integer.valueOf(textLinea.replaceAll("[^0-9]", "")));
	}
	
	private String getFechaTicketPage(WebElement boxDataTicket) {
		return (boxDataTicket.findElement(By.xpath("//div[@class[contains(.,'sg-caption-light')]]")).getText());
	} 
}
