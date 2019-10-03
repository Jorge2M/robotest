package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paytrail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


public class PagePaytrailResultadoOk extends WebdrvWrapp {
    
    static String aceptadoEnFinlandes = "hyväksytty";
    static String volverAlServicioDelVendedorEnFinlandes = "Palaa myyjän palveluun";
    
    public static String getXPathVolverAMangoButton() {
        return "//input[@class='button' and @value[contains(.,'" + volverAlServicioDelVendedorEnFinlandes + "')]]";
    }
    
    public static boolean isPage(WebDriver driver) {
        return (driver.getTitle().toLowerCase().contains(aceptadoEnFinlandes));
    }
    
    public static void clickVolverAMangoButton(WebDriver driver) throws Exception {
        String xpathButton = getXPathVolverAMangoButton();
        clickAndWaitLoad(driver, By.xpath(xpathButton));
    }
}
