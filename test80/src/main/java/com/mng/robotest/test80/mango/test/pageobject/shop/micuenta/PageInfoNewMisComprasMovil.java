package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.TypeOfClick;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageInfoNewMisComprasMovil {

    static String XPathButtonToMisCompras = "//div[@class[contains(.,'button')] and @id='goToMyPurchases']";
    
    public static boolean isPage(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathButtonToMisCompras), driver).wait(2).check());
    }
    
    public static boolean isVisibleButtonToMisCompras(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathButtonToMisCompras), driver).check());
    }
    
    public static void clickButtonToMisCompras(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonToMisCompras));
        if (isVisibleButtonToMisCompras(driver)) {
        	clickAndWaitLoad(driver, By.xpath(XPathButtonToMisCompras), TypeOfClick.javascript);
        }
    }
}
