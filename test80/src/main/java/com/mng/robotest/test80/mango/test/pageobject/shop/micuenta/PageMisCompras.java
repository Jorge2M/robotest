package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;


public class PageMisCompras extends WebdrvWrapp {
    
    public static SecDetalleCompraTienda SecDetalleCompraTienda;
    public static SecQuickViewArticulo SecQuickViewArticulo;
    
    public enum TypeCompra {Tienda, Online}
    static String XPathCapaContenedora = "//div[@id='myPurchasesPage']";
    static String XPathBlockTienda = "//div[@id='menuTabsShop']";
    static String XPathBlockOnline = "//div[@id='menuTabsOnline']";
    static String XPathListCompras = "//div[@id='listData']";
    static String XPathEmptyListImageTienda = XPathListCompras + "//img[@src[contains(.,'empty_offline')]]"; 
    static String XPathEmptyListImageOnline = XPathListCompras + "//img[@src[contains(.,'empty_online')]]";
    static String prefixIdCompra = "box_";
    static String XPathCompra = XPathListCompras + "//div[@id[contains(.,'" + prefixIdCompra + "')] and @class[contains(.,'fills')]]";
    static String XPathArticuloMasInfo = "//div[@class='small-box-container']";
    static String XPathReferenciaArticulo = XPathArticuloMasInfo + "//div[@class='reference']";
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathCapaContenedora), maxSecondsToWait));
    }
    
    public static String getXPathBlock(TypeCompra typeCompra) {
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
    
    public static String getXPathEmptyListImage(TypeCompra typeCompra) {
        String xpathBlock = "";
        switch (typeCompra) {
        case Online: 
            xpathBlock = XPathEmptyListImageOnline;
            break;
        case Tienda:
            xpathBlock = XPathEmptyListImageTienda;
            break;
        default:
            break;
        }
        
        return xpathBlock;        
    }
    
    public static String getXPathBlock(TypeCompra typeCompra, boolean active) {
        String xpathBlock = getXPathBlock(typeCompra);
        String attributeActive = "@class[contains(.,'active')]";
        if (active) {
            return xpathBlock.replace("']", "' and " + attributeActive + "]");
        }
        return xpathBlock.replace("']", "' and not(" + attributeActive + "])");
    }
    
    public static String getXPathCompra(int posInLista) {
        return "(" + XPathCompra + ")[" + posInLista + "]";  
    }
    
    public static String getXPathFechaCompra(int posInLista) {
        return (getXPathCompra(posInLista) + "//div[@class='box-info']/div[@class='date']");
    }
    
    public static String getXPathImporteCompra(int posInLista) {
        return (getXPathCompra(posInLista) + "//div[@class='box-price']");
    }    

    public static String getXPathNumPrendasCompra(int posInLista, Channel channel) {
        if (channel==Channel.movil_web) {
            return (getXPathCompra(posInLista) + "//span[@class='prendas']");
        }
        return (getXPathCompra(posInLista) + "//div[@class='box-info']//div[@class='shop-look']");
    }
    
    public static String getXPathEstadoCompraOnline(int posInLista, Channel channel) {
        String lastElem = "p[3]";
        if (channel==Channel.movil_web) {
            lastElem = "/li[@class='regular'][2]";
        }
        return (getXPathCompra(posInLista) + "//div[@class='box-info']/div[@class='shop']/" + lastElem);
    }
    
    public static String getXPathDireccionCompraTienda(int posInLista) {
        return (getXPathCompra(posInLista) + "//div[@class='shop']/p[2]");
    }
    
    public static boolean isPresentBlockUntil(int maxSecondsToWait, TypeCompra typeCompra, WebDriver driver) {
        String xpathBlock = getXPathBlock(typeCompra);
        return (isElementPresentUntil(driver, By.xpath(xpathBlock), maxSecondsToWait));
    }    
    
    public static boolean isSelectedBlockUntil(int maxSecondsToWait, TypeCompra typeCompra, WebDriver driver) {
        String xpathBlock = getXPathBlock(typeCompra, true/*active*/);
        return (isElementVisibleUntil(driver, By.xpath(xpathBlock), maxSecondsToWait));
    }
    
    public static boolean isVisibleEmptyListImage(TypeCompra typeCompra, WebDriver driver) {
        String xpathImage = getXPathEmptyListImage(typeCompra);
        return isElementVisible(driver, By.xpath(xpathImage));
    }
    
    public static void clickBlock(TypeCompra typeCompra, WebDriver driver) {
        String xpathBlock = getXPathBlock(typeCompra);
        driver.findElement(By.xpath(xpathBlock)).click();
    }
    
    public static void clickBlockTienda(WebDriver driver) {
        driver.findElement(By.xpath(XPathBlockTienda)).click();
    }    
    
    public static boolean isVisibleAnyCompraUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathCompra), maxSecondsToWait));
    }
    
    public static TypeCompra getTypeCompra(int posInLista, WebDriver driver) {
        String idCompra = getIdCompra(posInLista, driver);
        if (StringUtils.isNumeric(idCompra)) {
            return TypeCompra.Tienda;
        }
        return TypeCompra.Online;
    }
    
    public static void clickCompra(int posInLista, WebDriver driver) throws Exception {
        String xpathCompra = getXPathCompra(posInLista);
        clickAndWaitLoad(driver, By.xpath(xpathCompra));
    }
    
    public static CompraOnline getDataCompraOnline(int posInLista, Channel channel, WebDriver driver) {
        CompraOnline compraOnline = new CompraOnline();
        compraOnline.fecha = getFechaCompra(posInLista, driver);
        compraOnline.numPedido = getIdCompra(posInLista, driver);
        compraOnline.importe = getImporteCompra(posInLista, driver);
        compraOnline.estado = getEstadoCompraOnline(posInLista, channel, driver);
        compraOnline.numPrendas = getNumPrendasCompra(posInLista, channel, driver);
        
        return compraOnline;
    }
    
    public static CompraTienda getDataCompraTienda(int posInLista, Channel channel, WebDriver driver) {
        CompraTienda compraTienda = new CompraTienda();
        compraTienda.fecha = getFechaCompra(posInLista, driver);
        compraTienda.idCompra = getIdCompra(posInLista, driver);
        compraTienda.importe = getImporteCompra(posInLista, driver);
        compraTienda.direccion = getDireccionCompraTienda(posInLista, driver);
        compraTienda.numPrendas = getNumPrendasCompra(posInLista, channel, driver);
        
        return compraTienda;
    }    
    
    public static String getFechaCompra(int posInLista, WebDriver driver) {
        String xpathFecha = getXPathFechaCompra(posInLista);
        return (driver.findElement(By.xpath(xpathFecha)).getText());
    }
    
    public static String getIdCompra(int posInLista, WebDriver driver) {
        String XPathCompraNum = getXPathCompra(posInLista);
        String idDomElement = driver.findElement(By.xpath(XPathCompraNum)).getAttribute("id");
        return (idDomElement.replace(prefixIdCompra, ""));
    }
    
    public static List<WebElement> getListaCompras(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathCompra)));
    }
    
    public static boolean isVisibleCompraOnline(String codPedido, WebDriver driver) {
        List<WebElement> listaCompras = getListaCompras(driver);
        for (int posInLista=1; posInLista<=listaCompras.size(); posInLista++) {
            if (getIdCompra(posInLista, driver).compareTo(codPedido)==0) {
                return true;
            }
        }
        
        return false;
    }
    
    public static String getImporteCompra(int posInLista, WebDriver driver) {
        String xpathImporte = getXPathImporteCompra(posInLista);
        return (driver.findElement(By.xpath(xpathImporte)).getText());
    }
    
    public static String getDireccionCompraTienda(int posInLista, WebDriver driver) {
        String xpathDireccion = getXPathDireccionCompraTienda(posInLista);
        return (driver.findElement(By.xpath(xpathDireccion)).getText());
    }
    
    public static String getEstadoCompraOnline(int posInLista, Channel channel, WebDriver driver) {
        String xpathEstado = getXPathEstadoCompraOnline(posInLista, channel);
        return (driver.findElement(By.xpath(xpathEstado)).getText());
    }
    
    public static int getNumPrendasCompra(int posInLista, Channel channel, WebDriver driver) {
        String xpathNumPrendas = getXPathNumPrendasCompra(posInLista, channel);
        int valueReturn = Integer.valueOf(driver.findElement(By.xpath(xpathNumPrendas)).getText().replaceAll("\\D+","")).intValue();
        return (valueReturn);
    }

	public static void clickMasInfoArticulo(WebDriver driver) {
		driver.findElement(By.xpath(XPathArticuloMasInfo)).click();
	}

	public static String getReferenciaPrimerArticulo(WebDriver driver) {
		return driver.findElement(By.xpath(XPathReferenciaArticulo)).getText();
	}
}
