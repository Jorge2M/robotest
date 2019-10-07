package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalDetalleMisCompras;


public class PageMisCompras extends WebdrvWrapp {
    
    private final SecDetalleCompraTienda secDetalleCompraTienda;
    private final SecQuickViewArticulo secQuickViewArticulo;
    private final ModalDetalleMisCompras modalDetalleMisCompras;
    
    private final WebDriver driver;
    private final Channel channel;
    
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
    
    private PageMisCompras(Channel channel, WebDriver driver) {
    	this.driver = driver;
    	this.channel = channel;
        this.secDetalleCompraTienda = SecDetalleCompraTienda.getNew(driver);
        this.secQuickViewArticulo = SecQuickViewArticulo.getNew(driver);
        this.modalDetalleMisCompras = ModalDetalleMisCompras.getNew(channel, driver);
    }
    public static PageMisCompras getNew(Channel channel, WebDriver driver) {
    	return new PageMisCompras(channel, driver);
    }
    
    public SecDetalleCompraTienda getSecDetalleCompraTienda() {
    	return this.secDetalleCompraTienda;
    }
    public SecQuickViewArticulo getSecQuickViewArticulo() {
    	return this.secQuickViewArticulo;
    }
    public ModalDetalleMisCompras getModalDetalleMisCompras() {
    	return this.modalDetalleMisCompras;
    }
    
    public boolean isPageUntil(int maxSecondsToWait) {
        return (isElementVisibleUntil(driver, By.xpath(XPathCapaContenedora), maxSecondsToWait));
    }
    
    private String getXPathBlock(TypeCompra typeCompra) {
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
    
    private String getXPathEmptyListImage(TypeCompra typeCompra) {
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
    
    private String getXPathBlock(TypeCompra typeCompra, boolean active) {
        String xpathBlock = getXPathBlock(typeCompra);
        String attributeActive = "@class[contains(.,'active')]";
        if (active) {
            return xpathBlock.replace("']", "' and " + attributeActive + "]");
        }
        return xpathBlock.replace("']", "' and not(" + attributeActive + "])");
    }
    
    private String getXPathCompra(int posInLista) {
        return "(" + XPathCompra + ")[" + posInLista + "]";  
    }
    
    private String getXPathFechaCompra(int posInLista) {
        return (getXPathCompra(posInLista) + "//div[@class='box-info']/div[@class='date']");
    }
    
    private String getXPathImporteCompra(int posInLista) {
        return (getXPathCompra(posInLista) + "//div[@class='box-price']");
    }    

    private String getXPathNumPrendasCompra(int posInLista) {
        if (channel==Channel.movil_web) {
            return (getXPathCompra(posInLista) + "//span[@class='prendas']");
        }
        return (getXPathCompra(posInLista) + "//div[@class='box-info']//div[@class='shop-look']");
    }
    
    private String getXPathEstadoCompraOnline(int posInLista) {
        String lastElem = "p[3]";
        if (channel==Channel.movil_web) {
            lastElem = "/li[@class='regular'][2]";
        }
        return (getXPathCompra(posInLista) + "//div[@class='box-info']/div[@class='shop']/" + lastElem);
    }
    
    private String getXPathDireccionCompraTienda(int posInLista) {
        return (getXPathCompra(posInLista) + "//div[@class='shop']/p[2]");
    }
    
    public boolean isPresentBlockUntil(int maxSecondsToWait, TypeCompra typeCompra) {
        String xpathBlock = getXPathBlock(typeCompra);
        return (isElementPresentUntil(driver, By.xpath(xpathBlock), maxSecondsToWait));
    }    
    
    public boolean isSelectedBlockUntil(int maxSecondsToWait, TypeCompra typeCompra) {
        String xpathBlock = getXPathBlock(typeCompra, true);
        return (isElementVisibleUntil(driver, By.xpath(xpathBlock), maxSecondsToWait));
    }
    
    public boolean isVisibleEmptyListImage(TypeCompra typeCompra) {
        String xpathImage = getXPathEmptyListImage(typeCompra);
        return isElementVisible(driver, By.xpath(xpathImage));
    }
    
    public void clickBlock(TypeCompra typeCompra) {
        String xpathBlock = getXPathBlock(typeCompra);
        driver.findElement(By.xpath(xpathBlock)).click();
    }
    
    public void clickBlockTienda() {
        driver.findElement(By.xpath(XPathBlockTienda)).click();
    }    
    
    public boolean isVisibleAnyCompraUntil(int maxSecondsToWait) {
        return (isElementVisibleUntil(driver, By.xpath(XPathCompra), maxSecondsToWait));
    }
    
    public TypeCompra getTypeCompra(int posInLista) {
        String idCompra = getIdCompra(posInLista);
        if (StringUtils.isNumeric(idCompra)) {
            return TypeCompra.Tienda;
        }
        return TypeCompra.Online;
    }
    
    public void clickCompra(int posInLista) throws Exception {
        String xpathCompra = getXPathCompra(posInLista);
        clickAndWaitLoad(driver, By.xpath(xpathCompra));
    }
    
    public CompraOnline getDataCompraOnline(int posInLista) {
        CompraOnline compraOnline = new CompraOnline();
        compraOnline.fecha = getFechaCompra(posInLista);
        compraOnline.numPedido = getIdCompra(posInLista);
        compraOnline.importe = getImporteCompra(posInLista);
        compraOnline.estado = getEstadoCompraOnline(posInLista);
        compraOnline.numPrendas = getNumPrendasCompra(posInLista);
        
        return compraOnline;
    }
    
    public CompraTienda getDataCompraTienda(int posInLista) {
        CompraTienda compraTienda = new CompraTienda();
        compraTienda.fecha = getFechaCompra(posInLista);
        compraTienda.idCompra = getIdCompra(posInLista);
        compraTienda.importe = getImporteCompra(posInLista);
        compraTienda.direccion = getDireccionCompraTienda(posInLista);
        compraTienda.numPrendas = getNumPrendasCompra(posInLista);
        
        return compraTienda;
    }    
    
    public String getFechaCompra(int posInLista) {
        String xpathFecha = getXPathFechaCompra(posInLista);
        return (driver.findElement(By.xpath(xpathFecha)).getText());
    }
    
    public String getIdCompra(int posInLista) {
        String XPathCompraNum = getXPathCompra(posInLista);
        String idDomElement = driver.findElement(By.xpath(XPathCompraNum)).getAttribute("id");
        return (idDomElement.replace(prefixIdCompra, ""));
    }
    
    public List<WebElement> getListaCompras() {
        return (driver.findElements(By.xpath(XPathCompra)));
    }
    
    public boolean isVisibleCompraOnline(String codPedido) {
        List<WebElement> listaCompras = getListaCompras();
        for (int posInLista=1; posInLista<=listaCompras.size(); posInLista++) {
            if (getIdCompra(posInLista).compareTo(codPedido)==0) {
                return true;
            }
        }
        
        return false;
    }
    
    public String getImporteCompra(int posInLista) {
        String xpathImporte = getXPathImporteCompra(posInLista);
        return (driver.findElement(By.xpath(xpathImporte)).getText());
    }
    
    public String getDireccionCompraTienda(int posInLista) {
        String xpathDireccion = getXPathDireccionCompraTienda(posInLista);
        return (driver.findElement(By.xpath(xpathDireccion)).getText());
    }
    
    public String getEstadoCompraOnline(int posInLista) {
        String xpathEstado = getXPathEstadoCompraOnline(posInLista);
        return (driver.findElement(By.xpath(xpathEstado)).getText());
    }
    
    public int getNumPrendasCompra(int posInLista) {
        String xpathNumPrendas = getXPathNumPrendasCompra(posInLista);
        int valueReturn = Integer.valueOf(driver.findElement(By.xpath(xpathNumPrendas)).getText().replaceAll("\\D+","")).intValue();
        return (valueReturn);
    }

	public void clickMasInfoArticulo() {
		driver.findElement(By.xpath(XPathArticuloMasInfo)).click();
	}

	public String getReferenciaPrimerArticulo() {
		return driver.findElement(By.xpath(XPathReferenciaArticulo)).getText();
	}
}
