package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.eps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


/**
 * Page1: la página inicial de iDEAL (la posterior a la selección del botón "Confirmar Pago")
 * Page2: la página de simulación
 * @author jorge.munoz
 *
 */
public class PageEpsSelBanco extends WebdrvWrapp {

    static String XPathIconoEps = "//div[@class='header-logo']";
    static String XPathIconoBanco = "//div[@class='loginlogo']";

    public static boolean isPresentIconoEps(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathIconoEps)));
    }
    
    public static boolean isVisibleIconoBanco(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathIconoBanco)));
    }
}
