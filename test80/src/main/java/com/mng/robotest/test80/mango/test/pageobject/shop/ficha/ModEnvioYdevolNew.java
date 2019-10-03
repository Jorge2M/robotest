package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;

/**
 * Modal que aparece al seleccionar el link "Envío y devoluciones" existente en la nueva Ficha
 * @author jorge.munoz
 *
 */

public class ModEnvioYdevolNew extends WebdrvWrapp {

    static String XPathWrapper = "//div[@class='handling-modal-wrapper']";
    static String XPathAspaForClose = XPathWrapper + "//span[@class[contains(.,'modal-close')]]";
    
    public static boolean isVisibleUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathWrapper), maxSecondsToWait));
    }
    
    public static void clickAspaForClose(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathAspaForClose));
    }
}
