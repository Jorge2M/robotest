package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class PageMercpagoConf extends WebdrvWrapp {
    
    static String XPathSectionReviewDesktop = "//section[@class='review-step']";
    static String XPathButtonPagar = "//button[@class[contains(.,'ch-btn')] and @type='submit']";
    
    public static String getXPathSectionReview(Channel channel) {
        switch (channel) {
        case movil_web:
            return XPathButtonPagar;
        default:
        case desktop:
            return XPathSectionReviewDesktop;
        }
    }
    
    public static boolean isPageUntil(Channel channel, int maxSecondsToWait, WebDriver driver) {
        String xpathSection = getXPathSectionReview(channel);
        return (isElementPresentUntil(driver, By.xpath(xpathSection), maxSecondsToWait));
    }
    
    public static void clickPagar(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonPagar));
    }
}
