package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageYandexPayingByCode {
    
    static String XPathDivPaymentCode = "//div[@class[contains(.,'payment-code-wrapper')]]";
    static String XPathPaymentCode = XPathDivPaymentCode + "//div[@class[contains(.,'payment-code-value')]]";
    static String XPathButtonBackToMangoDesktop = "//div[@class[contains(.,'backward-button')]]//a";
    static String XPathButtonBackToMangoMovil = "//div[@class[contains(.,'back-link')]]//a";
    
    static String getXPathBackToMangoLink(Channel channel) {
        switch (channel) {
        case desktop:
            return XPathButtonBackToMangoDesktop;
        default:
        case mobile:
            return XPathButtonBackToMangoMovil;
        }        
    }
    
    static String getXPathDataUnitThatContains(String value) {
        return ("//div[@class[contains(.,'data-unit__base')] and text()[contains(.,'" + value + "')]]");
    }
    
    public static boolean isPage(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathDivPaymentCode), driver).check());
    }
        
    public static boolean isVisibleEmail(String emailUsr, WebDriver driver) {
        String xpathEmail = getXPathDataUnitThatContains(emailUsr);
        return (state(Visible, By.xpath(xpathEmail), driver).check());
    }
    
    public static boolean isPresentEmail(String emailUsr, WebDriver driver) {
        String xpathEmail = getXPathDataUnitThatContains(emailUsr);
        return (state(Present, By.xpath(xpathEmail), driver).check());
    }    

	public static void clickBackToMango(Channel channel, WebDriver driver) {
		String xpathBackToMango = getXPathBackToMangoLink(channel);
		click(By.xpath(xpathBackToMango), driver).exec();
	}

    public static boolean isVisiblePaymentCode(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathPaymentCode), driver).check());
    }
    
    public static String getPaymentCode(WebDriver driver) {
        WebElement paymentCode = driver.findElement(By.xpath(XPathPaymentCode));
        if (paymentCode==null) {
            return "";
        }
        return (driver.findElement(By.xpath(XPathPaymentCode)).getText());
    }
}
