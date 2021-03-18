package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMercpagoConf {
    
    static String XPathSectionReviewDesktop = "//section[@class='review-step']";
    static String XPathButtonPagar = "//button[@class[contains(.,'ch-btn')] and @type='submit']";
    
    public static String getXPathSectionReview(Channel channel) {
        switch (channel) {
        case mobile:
            return XPathButtonPagar;
        default:
        case desktop:
            return XPathSectionReviewDesktop;
        }
    }
    
    public static boolean isPageUntil(Channel channel, int maxSeconds, WebDriver driver) {
        String xpathSection = getXPathSectionReview(channel);
        return (state(Present, By.xpath(xpathSection), driver).wait(maxSeconds).check());
    }

	public static void clickPagar(WebDriver driver) {
		click(By.xpath(XPathButtonPagar), driver).exec();
	}
}
