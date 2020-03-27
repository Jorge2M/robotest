package com.mng.robotest.test80.mango.test.pageobject.shop.pedidos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageListPedidos {

    private static final String XPathListaPedidos = "//span[@id[contains(.,'listaPedidos')]]";
    
    public static String getXPath_LinkPedido(String codPedido) {
        return ("//span[text()='" + codPedido + "']");
    }
    
    public static boolean isPage(WebDriver driver) {
    	return (state(Present, By.xpath(XPathListaPedidos), driver).check());
    }
    
    public static boolean isVisibleCodPedido(String codPedido, WebDriver driver) {
        String xpathPedido = getXPath_LinkPedido(codPedido);
        return (state(Visible, By.xpath(xpathPedido), driver).check());
    }
    
    public static void selectLineaPedido(String codPedido, WebDriver driver) throws Exception {
        String xpathPedido = getXPath_LinkPedido(codPedido);
        clickAndWaitLoad(driver, By.xpath(xpathPedido));
    }
}
