package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paytrail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class PagePaytrailIdConfirm extends WebdrvWrapp {
    
    static String XPathInputId = "//input[@name[contains(.,'PMTCONNB')]]";
    static String XPathButtonConfirmar = "//input[@name[contains(.,'SAVEBTN')]]";
    
    public static boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathInputId)));
    }
    
    public static void inputIdConfirm(String idConfirm, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputId)).clear();
        driver.findElement(By.xpath(XPathInputId)).sendKeys(idConfirm);
    }
    
    public static void clickConfirmar(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonConfirmar));
    }
}
