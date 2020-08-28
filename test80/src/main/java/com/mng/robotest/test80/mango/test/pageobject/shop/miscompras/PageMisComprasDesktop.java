package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.Ticket;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMisComprasDesktop extends PageMisCompras {

	private final static String XPathListTickets = "//div[@id='listData']";
	private final static String prefixIdCompra = "box_";
	private final static String XPathTicket = XPathListTickets + "//div[@id[contains(.,'" + prefixIdCompra + "')] and @class[contains(.,'fills')]]";
	private final static String XPathDataBoxTicket = ".//div[@class='box-info']";
	private final static String XPathBlockTienda = "//div[@id='menuTabsShop']";
	private final static String XPathBlockOnline = "//div[@id='menuTabsOnline']";
	
	static String XPathCapaContenedora = "//div[@id='myPurchasesPage']";
	static String XPathEmptyListImageTienda = XPathListTickets + "//img[@src[contains(.,'empty_offline')]]"; 
	static String XPathEmptyListImageOnline = XPathListTickets + "//img[@src[contains(.,'empty_online')]]";
	static String XPathArticuloMasInfo = "//div[@class='small-box-container']";
	static String XPathReferenciaArticulo = XPathArticuloMasInfo + "//div[@class='reference']";

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
		return getTicketsPage().stream()
			.map(item -> getTicket(item))
			.filter(item -> item.getType()==typeTicket)
			.collect(Collectors.toList());
	}
	
    public void clickBlockIfExists(TypeTicket typeCompra) {
        String xpathBlock = getXPathBlock(typeCompra);
        if (state(State.Visible, By.xpath(xpathBlock)).wait(2).check()) {
        	driver.findElement(By.xpath(xpathBlock)).click();
        }
    }
	
	private Ticket getTicket(WebElement ticketScreen) {
		WebElement boxDataTicket = ticketScreen.findElement(By.xpath(XPathDataBoxTicket));
		Ticket ticket = new Ticket();
		ticket.setType(getTypeTicketPage(boxDataTicket));
		ticket.setId(getIdTicketPage(boxDataTicket));
		ticket.setPrecio(getPrecioTicketPage(boxDataTicket));
		ticket.setNumItems(getNumItemsTicketPage(boxDataTicket));
		ticket.setFecha(getFechaTicketPage(boxDataTicket));
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
		return (
			Integer.valueOf(
				boxDataTicket.findElement(By.xpath("//div[@class='shop-look']")).getText())
			);
	}
	private String getFechaTicketPage(WebElement boxDataTicket) {
		return (boxDataTicket.findElement(By.xpath("//div[@class='date']")).getText());
	}
	
	
	
	
	
	
    
	
	

    
//    private String getXPathEmptyListImage(TypeTicket typeCompra) {
//        String xpathBlock = "";
//        switch (typeCompra) {
//        case Online: 
//            xpathBlock = XPathEmptyListImageOnline;
//            break;
//        case Tienda:
//            xpathBlock = XPathEmptyListImageTienda;
//            break;
//        default:
//            break;
//        }
//        
//        return xpathBlock;        
//    }
//    
//    private String getXPathBlock(TypeTicket typeCompra, boolean active) {
//        String xpathBlock = getXPathBlock(typeCompra);
//        String attributeActive = "@class[contains(.,'active')]";
//        if (active) {
//            return xpathBlock.replace("']", "' and " + attributeActive + "]");
//        }
//        return xpathBlock.replace("']", "' and not(" + attributeActive + "])");
//    }
//    
//    private String getXPathCompra(int posInLista) {
//        return "(" + XPathCompra + ")[" + posInLista + "]";  
//    }
//    
//    private String getXPathFechaCompra(int posInLista) {
//        return (getXPathCompra(posInLista) + "//div[@class='box-info']/div[@class='date']");
//    }
//    
//    private String getXPathImporteCompra(int posInLista) {
//        return (getXPathCompra(posInLista) + "//div[@class='box-price']");
//    }    
//
//    private String getXPathNumPrendasCompra(int posInLista) {
//        if (channel==Channel.mobile) {
//            return (getXPathCompra(posInLista) + "//span[@class='prendas']");
//        }
//        return (getXPathCompra(posInLista) + "//div[@class='box-info']//div[@class='shop-look']");
//    }
//    
//    private String getXPathEstadoCompraOnline(int posInLista) {
//        String lastElem = "p[3]";
//        if (channel==Channel.mobile) {
//            lastElem = "/li[@class='regular'][2]";
//        }
//        return (getXPathCompra(posInLista) + "//div[@class='box-info']/div[@class='shop']/" + lastElem);
//    }
//    
//    private String getXPathDireccionCompraTienda(int posInLista) {
//        return (getXPathCompra(posInLista) + "//div[@class='shop']/p[2]");
//    }
//    
//    public boolean isPresentBlockUntil(int maxSeconds, TypeTicket typeCompra) {
//        String xpathBlock = getXPathBlock(typeCompra);
//        return (state(Present, By.xpath(xpathBlock)).wait(maxSeconds).check());
//    }    
//    
//    public boolean isSelectedBlockUntil(int maxSeconds, TypeTicket typeCompra) {
//        String xpathBlock = getXPathBlock(typeCompra, true);
//        return (state(Visible, By.xpath(xpathBlock)).wait(maxSeconds).check());
//    }
//    
//    public boolean isVisibleEmptyListImage(TypeTicket typeCompra) {
//        String xpathImage = getXPathEmptyListImage(typeCompra);
//        return (state(Visible, By.xpath(xpathImage)).check());
//    }
//    
//
//    
//    public void clickBlockTienda() {
//        driver.findElement(By.xpath(XPathBlockTienda)).click();
//    }    
//    
//    public boolean isVisibleAnyCompraUntil(int maxSeconds) {
//    	return (state(Visible, By.xpath(XPathCompra)).wait(maxSeconds).check());
//    }
//    
//
//    
//    public void clickCompra(int posInLista) {
//        String xpathCompra = getXPathCompra(posInLista);
//        click(By.xpath(xpathCompra)).exec();
//    }
//    
//    public String getFechaCompra(int posInLista) {
//        String xpathFecha = getXPathFechaCompra(posInLista);
//        return (driver.findElement(By.xpath(xpathFecha)).getText());
//    }
//    
//    public String getIdCompra(int posInLista) {
//        String XPathCompraNum = getXPathCompra(posInLista);
//        String idDomElement = driver.findElement(By.xpath(XPathCompraNum)).getAttribute("id");
//        return (idDomElement.replace(prefixIdCompra, ""));
//    }
//    
//
//    
//    public boolean isVisibleCompraOnline(String codPedido) {
//        List<WebElement> listaCompras = getListaCompras();
//        for (int posInLista=1; posInLista<=listaCompras.size(); posInLista++) {
//            if (getIdCompra(posInLista).compareTo(codPedido)==0) {
//                return true;
//            }
//        }
//        
//        return false;
//    }
//    
//    public String getImporteCompra(int posInLista) {
//        String xpathImporte = getXPathImporteCompra(posInLista);
//        return (driver.findElement(By.xpath(xpathImporte)).getText());
//    }
//    
//    public String getDireccionCompraTienda(int posInLista) {
//        String xpathDireccion = getXPathDireccionCompraTienda(posInLista);
//        return (driver.findElement(By.xpath(xpathDireccion)).getText());
//    }
//    
//    public String getEstadoCompraOnline(int posInLista) {
//        String xpathEstado = getXPathEstadoCompraOnline(posInLista);
//        return (driver.findElement(By.xpath(xpathEstado)).getText());
//    }
//    
//    public int getNumPrendasCompra(int posInLista) {
//        String xpathNumPrendas = getXPathNumPrendasCompra(posInLista);
//        int valueReturn = Integer.valueOf(driver.findElement(By.xpath(xpathNumPrendas)).getText().replaceAll("\\D+","")).intValue();
//        return (valueReturn);
//    }
//
//	public void clickMasInfoArticulo() {
//		driver.findElement(By.xpath(XPathArticuloMasInfo)).click();
//	}
//
//	public String getReferenciaPrimerArticulo() {
//		return driver.findElement(By.xpath(XPathReferenciaArticulo)).getText();
//	}
}
