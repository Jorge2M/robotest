package com.mng.robotest.test80.mango.test.pageobject.shop.pedidos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class PageListPedidos extends WebdrvWrapp {

    private static final String XPathListaPedidos = "//span[@id[contains(.,'listaPedidos')]]";
    
    public static String getXPath_LinkPedido(String codPedido) {
        return ("//span[text()='" + codPedido + "']");
    }
    
    public static boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathListaPedidos)));
    }
    
    public static boolean isVisibleCodPedido(String codPedido, WebDriver driver) {
        String xpathPedido = getXPath_LinkPedido(codPedido);
        return (isElementVisible(driver, By.xpath(xpathPedido)));
    }
    
    public static void selectLineaPedido(String codPedido, WebDriver driver) throws Exception {
        String xpathPedido = getXPath_LinkPedido(codPedido);
        clickAndWaitLoad(driver, By.xpath(xpathPedido));
    }
}
