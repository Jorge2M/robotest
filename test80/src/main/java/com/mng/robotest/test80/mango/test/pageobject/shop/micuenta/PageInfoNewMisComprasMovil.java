package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class PageInfoNewMisComprasMovil extends WebdrvWrapp {

    static String XPathButtonToMisCompras = "//div[@class[contains(.,'button')] and @id='goToMyPurchases']";
    
    public static boolean isPage(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathButtonToMisCompras)));
    }
    
    public static void clickButtonToMisCompras(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonToMisCompras));
    }
}
