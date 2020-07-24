package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.CompraOnline;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.CompraTienda;


public class PageMisComprasShop extends PageMisCompras {
    
    private final SecDetalleCompraTiendaShop secDetalleCompraTienda;
    private final SecQuickViewArticuloShop secQuickViewArticulo;
    private final ModalDetalleMisComprasShop modalDetalleMisCompras;
    
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
    
    private PageMisComprasShop(Channel channel, WebDriver driver) {
    	super(driver);
    	this.channel = channel;
        this.secDetalleCompraTienda = SecDetalleCompraTiendaShop.getNew(driver);
        this.secQuickViewArticulo = SecQuickViewArticuloShop.getNew(driver);
        this.modalDetalleMisCompras = ModalDetalleMisComprasShop.getNew(channel, driver);
    }
    public static PageMisComprasShop getNew(Channel channel, WebDriver driver) {
    	return new PageMisComprasShop(channel, driver);
    }
    
    public SecDetalleCompraTiendaShop getSecDetalleCompraTienda() {
    	return this.secDetalleCompraTienda;
    }
    public SecQuickViewArticuloShop getSecQuickViewArticulo() {
    	return this.secQuickViewArticulo;
    }
    public ModalDetalleMisComprasShop getModalDetalleMisCompras() {
    	return this.modalDetalleMisCompras;
    }
    
    public boolean isPageUntil(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathCapaContenedora)).wait(maxSeconds).check());
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
        if (channel==Channel.mobile) {
            return (getXPathCompra(posInLista) + "//span[@class='prendas']");
        }
        return (getXPathCompra(posInLista) + "//div[@class='box-info']//div[@class='shop-look']");
    }
    
    private String getXPathEstadoCompraOnline(int posInLista) {
        String lastElem = "p[3]";
        if (channel==Channel.mobile) {
            lastElem = "/li[@class='regular'][2]";
        }
        return (getXPathCompra(posInLista) + "//div[@class='box-info']/div[@class='shop']/" + lastElem);
    }
    
    private String getXPathDireccionCompraTienda(int posInLista) {
        return (getXPathCompra(posInLista) + "//div[@class='shop']/p[2]");
    }
    
    public boolean isPresentBlockUntil(int maxSeconds, TypeCompra typeCompra) {
        String xpathBlock = getXPathBlock(typeCompra);
        return (state(Present, By.xpath(xpathBlock)).wait(maxSeconds).check());
    }    
    
    public boolean isSelectedBlockUntil(int maxSeconds, TypeCompra typeCompra) {
        String xpathBlock = getXPathBlock(typeCompra, true);
        return (state(Visible, By.xpath(xpathBlock)).wait(maxSeconds).check());
    }
    
    public boolean isVisibleEmptyListImage(TypeCompra typeCompra) {
        String xpathImage = getXPathEmptyListImage(typeCompra);
        return (state(Visible, By.xpath(xpathImage)).check());
    }
    
    public void clickBlock(TypeCompra typeCompra) {
        String xpathBlock = getXPathBlock(typeCompra);
        driver.findElement(By.xpath(xpathBlock)).click();
    }
    
    public void clickBlockTienda() {
        driver.findElement(By.xpath(XPathBlockTienda)).click();
    }    
    
    public boolean isVisibleAnyCompraUntil(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathCompra)).wait(maxSeconds).check());
    }
    
    public TypeCompra getTypeCompra(int posInLista) {
        String idCompra = getIdCompra(posInLista);
        if (StringUtils.isNumeric(idCompra)) {
            return TypeCompra.Tienda;
        }
        return TypeCompra.Online;
    }
    
    public void clickCompra(int posInLista) {
        String xpathCompra = getXPathCompra(posInLista);
        click(By.xpath(xpathCompra)).exec();
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
