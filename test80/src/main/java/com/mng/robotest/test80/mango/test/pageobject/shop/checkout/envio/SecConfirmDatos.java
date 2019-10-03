package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


public class SecConfirmDatos extends WebdrvWrapp {

    static String XPathDivGeneralDesktop = "//div[@class[contains(.,'fixedConfirm')]]";
    static String XPathDivGeneralMovil = "//div[@class[contains(.,'dp-confirm-page')]]";
    static String XPathConfirmDatosButton = "//span[@id[contains(.,'confirmButton')]]";
    static String XPathInputPostNumberIdDeutschland = "//input[@placeholder[contains(.,'Post Number ID')]]";
    
    public static String getXPathDivGeneral(Channel channel) {
        switch (channel) {
        case desktop:
            return XPathDivGeneralDesktop;
        case movil_web:
        default:            
            return XPathDivGeneralMovil;
        }
    }
    
    public static boolean isVisibleUntil(int maxSecondsToWait, Channel channel, WebDriver driver) {
        String xpathDivGeneral = getXPathDivGeneral(channel);
        return (isElementVisibleUntil(driver, By.xpath(xpathDivGeneral), maxSecondsToWait));
    }
    
    public static boolean isVisibleInputPostNumberIdDeutschland(WebDriver driver) {
    	return (WebdrvWrapp.isElementVisible(driver, By.xpath(XPathInputPostNumberIdDeutschland)));
    }
    
    public static void sendDataInputPostNumberIdDeutschland(String data, WebDriver driver) throws Exception {
    	WebdrvWrapp.sendKeysWithRetry(2, data, By.xpath(XPathInputPostNumberIdDeutschland), driver);
    }
    
    public static void clickConfirmarDatosButtonAndWait(int maxSecondsToWait, WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathConfirmDatosButton), maxSecondsToWait);
    }
}
