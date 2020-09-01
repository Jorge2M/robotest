package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.Ticket;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMisComprasDesktop extends PageMisCompras {

	private final static String XPathCapaContenedora = "//div[@id='myPurchasesPage']";
	private final static String XPathListTickets = "//div[@id='listData']";
	private final static String prefixIdCompra = "box_";
	private final static String XPathTicket = XPathListTickets + "//div[@id[contains(.,'" + prefixIdCompra + "')] and @class[contains(.,'fills')]]";
	private final static String XPathBlockTienda = "//div[@id='menuTabsShop']";
	private final static String XPathBlockOnline = "//div[@id='menuTabsOnline']";

	public PageMisComprasDesktop(WebDriver driver) {
		super(Channel.desktop, driver);
	}

    private String getXPathBlock(TypeTicket typeCompra) {
        String xpathBlock = "";
        switch (typeCompra) {
        case Online: 
            xpathBlock = XPathBlockOnline;
            break;
        case Tienda:
            xpathBlock = XPathBlockTienda;
            break;
        default:
            break;
        }
        return xpathBlock;
    }

    private String getXPathTicket(String id) {
    	return (XPathTicket + "//self::*[@id[contains(.,'"+ id + "')]]");
    }
    
	@Override
	public boolean isPageUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathCapaContenedora)).wait(maxSeconds).check());
	}
	
	@Override
	public List<Ticket> getTickets() {
		List<Ticket> listTickets = getListTickets(TypeTicket.Online);
		listTickets.addAll(getListTickets(TypeTicket.Tienda));
		return (listTickets);
	}
	
	private List<Ticket> getListTickets(TypeTicket typeTicket) {
		clickBlockIfExists(typeTicket);
		isVisibleTicket(5);
		return getTicketsPage().stream()
			.map(item -> getTicket(item))
			.filter(item -> item.getType()==typeTicket)
			.collect(Collectors.toList());
	}
	
    public void clickBlockIfExists(TypeTicket typeCompra) {
        String xpathBlock = getXPathBlock(typeCompra);
        if (state(State.Visible, By.xpath(xpathBlock)).wait(2).check()) {
        	driver.findElement(By.xpath(xpathBlock)).click();
    		PageObjTM.waitForPageLoaded(driver);
        }
    }
	private boolean isVisibleTicket(int maxSeconds) {
		return (state(Visible, By.xpath(XPathTicket)).wait(maxSeconds).check());
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
	
    private List<WebElement> getTicketsPage() {
        return (driver.findElements(By.xpath(XPathTicket)));
    }

	private TypeTicket getTypeTicketPage(WebElement boxDataTicket) {
		String idTicket = getIdTicketPage(boxDataTicket);
        if (StringUtils.isNumeric(idTicket)) {
            return TypeTicket.Tienda;
        }
        return TypeTicket.Online;
	}
	private String getIdTicketPage(WebElement boxDataTicket) {
	    return (boxDataTicket.getAttribute("id").replace(prefixIdCompra, ""));
	}
	private String getPrecioTicketPage(WebElement boxDataTicket) {
		return (boxDataTicket.findElement(By.xpath("//div[@class='box-price']")).getText());
	}
	private int getNumItemsTicketPage(WebElement boxDataTicket) {
		String textLinea = boxDataTicket.findElement(By.xpath("//div[@class='shop-look']")).getText();
		return (Integer.valueOf(textLinea.replaceAll("[^0-9]", "")));
	}
	
	private String getFechaTicketPage(WebElement boxDataTicket) {
		return (boxDataTicket.findElement(By.xpath("//div[@class='date']")).getText());
	}
	
	public boolean isPresentBlock(TypeTicket typeCompra, int maxSeconds) {
		String xpathBlock = getXPathBlock(typeCompra);
		return (state(Present, By.xpath(xpathBlock)).wait(maxSeconds).check());
	}   
	
	@Override
	public Ticket selectTicket(TypeTicket type, int position) {
		Ticket ticket = getTickets(type).get(position-1);
		if (channel==Channel.desktop) {
			clickBlockIfExists(type);
		}
		By byTicket = By.xpath(getXPathTicket(ticket.getId()));
		driver.findElement(byTicket).click();
		return ticket;
	}
}
