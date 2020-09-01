package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageMisCompras.TypeTicket;
import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalDetalleCompraDesktop extends ModalDetalleCompra implements PageDetallePedido {
	
    private static String XPathDataTicket = "//div[@class[contains(.,'ticket-container')]]";
    private static String XPathIdTicketTienda = XPathDataTicket + "//div[@class='info']/p[1]";
    private static String XPathIdTicketOnline = XPathDataTicket + "//div[@class='info']/p[2]";  
    private static String XPathImporte = XPathDataTicket + "//div[@class='box-price' or @class='price' or @class[contains(.,'price-shop')]]";
    private static String XPathDireccion = XPathDataTicket + "//div[@class='info']/p[3]"; 
    private static String XPathCodigoBarrasImg = XPathDataTicket + "//div[@class='code']/img";
    private static String XPathArticulo = "//div[@onclick[contains(.,'openProductDetails')]]";
    private static String XPathLinkToMisCompras = "//div[@class[contains(.,'shopping-breadcrumbs')]]";
//    private static String XPathLinkToMisComprasMobil = "//div[@class[contains(.,'iconBack')]]";
//    
//	private String getXPathLinkToMisCompras() {
//		switch (channel) {
//		case desktop:
//			return XPathLinkToMisComprasDesktop;
//		case mobile:
//		default:
//			return XPathLinkToMisComprasMobil;
//		}
//	}
	
	private String getXPathIdTicket(TypeTicket type) {
		switch (type) {
		case Tienda:
			return XPathIdTicketTienda;
		case Online:
		default:
			return XPathIdTicketOnline;
		}
	}
    
    public ModalDetalleCompraDesktop(WebDriver driver) {
    	super(driver);
    }

	private String getXPathArticulo(int position) {
		return "//div[@id='box_" + position + "' and @class[contains(.,'fills')]]";
	}
    
    private String getXPathReferenciaArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo);
        return (xpathArticulo + "//div[@class='reference']");
    }
    
    private String getXPathNombreArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo);
        return (xpathArticulo + "//div[@class='date']/span");
    }    
    
    private String getXPathPrecioArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo);        
        return (xpathArticulo + "//div[@class='price' or @class[contains(.,'box-price')]]");
    }
    
    @Override
    public boolean isPage() {
    	return isVisibleSectionUntil(2);
    }
    @Override
    public DetallePedido getTypeDetalle() {
    	return DetallePedido.New;
    }
    @Override
    public boolean isPresentImporteTotal(String importeTotal, String codPais) {
    	String importe = getImporte();
    	return (importe.compareTo(importeTotal)==0);
    }
    @Override
    public boolean isVisiblePrendaUntil(int maxSeconds) {
    	return getNumPrendas()>0;
    }
    @Override
    public void clickBackButton(Channel channel) {
    	gotoListaMisCompras();
    }
    
    public boolean isVisibleSectionUntil(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathDataTicket)).wait(maxSeconds).check());
    }
    
    public String getIdTicket(TypeTicket typeTicket) {
    	String xpathIdTicket = getXPathIdTicket(typeTicket);
        String dataNumTicket = driver.findElement(By.xpath(xpathIdTicket)).getText();
        return (getDataRightFrom(": ", dataNumTicket));
    }
    
    public String getImporte() {
        return (driver.findElement(By.xpath(XPathImporte)).getText());
    }
    
    public String getDireccion() {
        return (driver.findElement(By.xpath(XPathDireccion)).getText());
    }
    
    @Override
    public int getNumPrendas() {
        return (driver.findElements(By.xpath(XPathArticulo)).size());
    }
    
    private String getDataRightFrom(String stringFrom, String data) {
        int beginIndex = data.indexOf(stringFrom) + stringFrom.length();
        return (data.substring(beginIndex));
    }
    
    public boolean isVisibleCodigoBarrasImg() {
    	if (state(Visible, By.xpath(XPathCodigoBarrasImg)).check()) {
            //Consideramos que la imagen ha de tener unas dimensiones mínimas de 30x50 para ser visible
            int width=driver.findElement(By.xpath(XPathCodigoBarrasImg)).getSize().getWidth();
            int height=driver.findElement(By.xpath(XPathCodigoBarrasImg)).getSize().getHeight();
            return (width>=30 && height>=50);
        }
        
        return false;
    }
    
    public String getReferenciaArticulo(int posArticulo) {
        String xpathReferencia = getXPathReferenciaArticulo(posArticulo);
        return (driver.findElement(By.xpath(xpathReferencia)).getText().replaceAll("\\D+",""));
    }
    
    public String getNombreArticulo(int posArticulo) {
        String xpathNombre = getXPathNombreArticulo(posArticulo);
        return (driver.findElement(By.xpath(xpathNombre)).getText());
    }
    
    public String getPrecioArticulo(int posArticulo) {
        String xpathPrecio = getXPathPrecioArticulo(posArticulo);
        return (driver.findElement(By.xpath(xpathPrecio)).getText());
    }
    
    public ArticuloScreen getDataArticulo(int posArticulo, AppEcom app) {
        //Sólo informamos algunos datos relevantes del artículo
        ArticuloScreen articulo = new ArticuloScreen(app);
        articulo.setReferencia(getReferenciaArticulo(posArticulo));
        articulo.setNombre(getNombreArticulo(posArticulo));
        articulo.setPrecio(getPrecioArticulo(posArticulo));
        
        return articulo;
    }
    
    public void selectArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo);
        driver.findElement(By.xpath(xpathArticulo)).click();
    }
    
	public void gotoListaMisCompras() {
		click(By.xpath(XPathLinkToMisCompras)).exec();
	}
}
