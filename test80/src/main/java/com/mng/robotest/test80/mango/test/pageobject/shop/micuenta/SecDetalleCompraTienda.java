package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class SecDetalleCompraTienda extends WebdrvWrapp {

    static String XPathDataTicket = "//div[@class[contains(.,'ticket-container')]]";
    static String XPathNumTicket = XPathDataTicket + "//div[@class='info']/p[1]";  
    static String XPathImporte = XPathDataTicket + "//div[@class='box-price' or @class='price']";
    static String XPathDireccion = XPathDataTicket + "//div[@class='info']/p[3]"; 
    static String XPathFechaDesktop = XPathDataTicket + "//div[@class='number']/span";
    static String XPathFechaMovil = XPathDataTicket + "//div[@class='number']/div[@class='date']";
    static String XPathCodigoBarrasImg = XPathDataTicket + "//div[@class='code']/img";
    static String XPathArticulo = "//div[@onclick[contains(.,'openProductDetails')]]";

    public static String getXPathArticulo(int posArticulo) {
        return ("(" + XPathArticulo + ")[" + posArticulo + "]");
    }
    
    public static String getXPathReferenciaArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo);
        return (xpathArticulo + "//div[@class='reference']");
    }
    
    public static String getXPathNombreArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo);
        return (xpathArticulo + "//div[@class='date']/span");
    }    
    
    public static String getXPathPrecioArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo);        
        return (xpathArticulo + "//div[@class='price' or @class[contains(.,'box-price')]]");
    }
    
    
    public static String getXPathFecha (Channel channel) {
        if (channel==Channel.movil_web) {
            return XPathFechaMovil;
        }
        return XPathFechaDesktop;
    }
    
    public static boolean isVisibleSectionUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathDataTicket), maxSecondsToWait));
    }
    
    public static String getNumTicket(WebDriver driver) {
        String dataNumTicket = driver.findElement(By.xpath(XPathNumTicket)).getText();
        return (getDataRightFrom(": ", dataNumTicket));
    }
    
    public static String getImporte(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathImporte)).getText());
    }
    
    public static String getDireccion(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathDireccion)).getText());
    }
    
    public static String getFecha(Channel channel, WebDriver driver) {
        String xpathFecha = getXPathFecha (channel);
        return (driver.findElement(By.xpath(xpathFecha)).getText());
    }
    
    public static int getNumPrendas(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathArticulo)).size());
    }
    
    private static String getDataRightFrom(String stringFrom, String data) {
        int beginIndex = data.indexOf(stringFrom) + stringFrom.length();
        return (data.substring(beginIndex));
    }
    
    public static boolean isVisibleCodigoBarrasImg(WebDriver driver) {
        if (isElementVisible(driver, By.xpath(XPathCodigoBarrasImg))) {
            //Consideramos que la imagen ha de tener unas dimensiones mínimas de 30x50 para ser visible
            int width=driver.findElement(By.xpath(XPathCodigoBarrasImg)).getSize().getWidth();
            int height=driver.findElement(By.xpath(XPathCodigoBarrasImg)).getSize().getHeight();
            return (width>=30 && height>=50);
        }
        
        return false;
    }
    
    public static String getReferenciaArticulo(int posArticulo, WebDriver driver) {
        String xpathReferencia = getXPathReferenciaArticulo(posArticulo);
        return (driver.findElement(By.xpath(xpathReferencia)).getText().replaceAll("\\D+",""));
    }
    
    public static String getNombreArticulo(int posArticulo, WebDriver driver) {
        String xpathNombre = getXPathNombreArticulo(posArticulo);
        return (driver.findElement(By.xpath(xpathNombre)).getText());
    }
    
    public static String getPrecioArticulo(int posArticulo, WebDriver driver) {
        String xpathPrecio = getXPathPrecioArticulo(posArticulo);
        return (driver.findElement(By.xpath(xpathPrecio)).getText());
    }
    
    public static ArticuloScreen getDataArticulo(int posArticulo, WebDriver driver) {
        //Sólo informamos algunos datos relevantes del artículo
        ArticuloScreen articulo = new ArticuloScreen();
        articulo.setReferencia(getReferenciaArticulo(posArticulo, driver));
        articulo.setNombre(getNombreArticulo(posArticulo, driver));
        articulo.setPrecio(getPrecioArticulo(posArticulo, driver));
        
        return articulo;
    }
    
    public static void selectArticulo(int posArticulo, WebDriver driver) {
        String xpathArticulo = getXPathArticulo(posArticulo);
        driver.findElement(By.xpath(xpathArticulo)).click();
    }
}
