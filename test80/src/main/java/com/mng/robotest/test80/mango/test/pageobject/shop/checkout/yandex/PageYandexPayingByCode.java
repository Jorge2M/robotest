package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageYandexPayingByCode extends WebdrvWrapp {
    
    static String XPathDivPaymentCode = "//div[@class[contains(.,'payment-code-wrapper')]]";
    static String XPathPaymentCode = XPathDivPaymentCode + "//div[@class[contains(.,'payment-code-value')]]";
    static String XPathButtonBackToMangoDesktop = "//div[@class[contains(.,'backward-button')]]//a";
    static String XPathButtonBackToMangoMovil = "//div[@class[contains(.,'back-link')]]//a";
    
    static String getXPathBackToMangoLink(Channel channel) {
        switch (channel) {
        case desktop:
            return XPathButtonBackToMangoDesktop;
        default:
        case movil_web:
            return XPathButtonBackToMangoMovil;
        }        
    }
    
    static String getXPathDataUnitThatContains(String value) {
        return ("//div[@class[contains(.,'data-unit__base')] and text()[contains(.,'" + value + "')]]");
    }
    
    public static boolean isPage(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathDivPaymentCode)));
    }
        
    public static boolean isVisibleEmail(String emailUsr, WebDriver driver) {
        String xpathEmail = getXPathDataUnitThatContains(emailUsr);
        return (isElementVisible(driver, By.xpath(xpathEmail)));
    }
    
    public static boolean isPresentEmail(String emailUsr, WebDriver driver) {
        String xpathEmail = getXPathDataUnitThatContains(emailUsr);
        return (isElementPresent(driver, By.xpath(xpathEmail)));
    }    
    
    public static void clickBackToMango(Channel channel, WebDriver driver) throws Exception {
        String xpathBackToMango = getXPathBackToMangoLink(channel);
        clickAndWaitLoad(driver, By.xpath(xpathBackToMango));
    }

    public static boolean isVisiblePaymentCode(WebDriver driver) {
        return isElementVisible(driver, By.xpath(XPathPaymentCode));
    }
    
    public static String getPaymentCode(WebDriver driver) {
        WebElement paymentCode = driver.findElement(By.xpath(XPathPaymentCode));
        if (paymentCode==null) {
            return "";
        }
        return (driver.findElement(By.xpath(XPathPaymentCode)).getText());
    }
}
