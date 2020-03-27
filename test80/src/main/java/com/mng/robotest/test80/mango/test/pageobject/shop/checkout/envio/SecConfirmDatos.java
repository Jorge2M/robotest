package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecConfirmDatos {

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
    
    public static boolean isVisibleUntil(int maxSeconds, Channel channel, WebDriver driver) {
        String xpathDivGeneral = getXPathDivGeneral(channel);
        return (state(Visible, By.xpath(xpathDivGeneral), driver)
        		.wait(maxSeconds).check());
    }
    
    public static boolean isVisibleInputPostNumberIdDeutschland(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathInputPostNumberIdDeutschland), driver).check());
    }
    
    public static void sendDataInputPostNumberIdDeutschland(String data, WebDriver driver) throws Exception {
    	sendKeysWithRetry(2, data, By.xpath(XPathInputPostNumberIdDeutschland), driver);
    }
    
    public static void clickConfirmarDatosButtonAndWait(int maxSecondsToWait, WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathConfirmDatosButton), maxSecondsToWait);
    }
}
