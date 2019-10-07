package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paytrail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class PagePaytrailEpayment extends WebdrvWrapp {
    
    static String XPathFormCodeCard = "//form[@action[contains(.,'AUTH=OLD')]]";
    static String XPathButtonOkFromCodeCard = XPathFormCodeCard + "//input[@class='button' and @name='Ok']";
    
    public static void clickOkFromCodeCard(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonOkFromCodeCard));
    }
}
