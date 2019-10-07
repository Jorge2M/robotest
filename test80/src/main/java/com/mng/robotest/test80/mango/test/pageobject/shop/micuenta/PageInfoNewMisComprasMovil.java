package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.TypeOfClick;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class PageInfoNewMisComprasMovil extends WebdrvWrapp {

    static String XPathButtonToMisCompras = "//div[@class[contains(.,'button')] and @id='goToMyPurchases']";
    
    public static boolean isPage(WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathButtonToMisCompras), 2));
    }
    
    public static boolean isVisibleButtonToMisCompras(WebDriver driver) {
    	return (WebdrvWrapp.isElementVisible(driver, By.xpath(XPathButtonToMisCompras)));
    }
    
    public static void clickButtonToMisCompras(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonToMisCompras));
        if (isVisibleButtonToMisCompras(driver)) {
        	clickAndWaitLoad(driver, By.xpath(XPathButtonToMisCompras), TypeOfClick.javascript);
        }
    }
}
