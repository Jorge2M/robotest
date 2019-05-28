package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;


public class PageResultPagoTpv extends WebdrvWrapp {
    static String XPathCabeceraConfCompra = "//div[@class[contains(.,'details')]]/h2";
    static String XPathCodPedido = "//div[@id[contains(.,'num-pedido')]]/div[@class='valor']";
    static String XPathGastosTransporte = "//div[@id='transporte']/div[@class='valor']";
    
    public static boolean isPresentCabeceraConfCompra(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathCabeceraConfCompra)));
    }

    public static boolean isVisibleCodPedido(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathCodPedido)));
    }
    
    public static String getCodigoPedido(WebDriver driver) {
        if (isVisibleCodPedido(driver)) {
            return (driver.findElement(By.xpath(XPathCodPedido)).getText());
        }
        return "";
    }
    
    public static boolean isGastoTransporteAcero(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathGastosTransporte)).getText().compareTo("0 €")==0);
    }
}
