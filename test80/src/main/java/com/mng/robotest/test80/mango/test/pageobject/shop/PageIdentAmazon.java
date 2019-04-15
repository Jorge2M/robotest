package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageIdentAmazon extends WebdrvWrapp {

    static String XPathImgLogoAmazon = "//div/img[@src[contains(.,'logo_payments')]]";
    static String XPathInputEmail = "//input[@id='ap_email']";
    static String XPathInputPassword = "//input[@id='ap_password']";
    
    /**
     * @return indicador de si realmente estamos en la página de identificación de Amazon (comprobamos si aparece el logo de Amazon)
     */
    public static boolean isLogoAmazon(WebDriver driver) { 
        return (isElementVisible(driver, By.xpath(XPathImgLogoAmazon)));
    }
    
    /**
     * @return indicador de si la página contiene los campos de usuario/password para la identificación
     */
    public static boolean isPageIdent(WebDriver driver) {
        return (
        	isElementVisible(driver, By.xpath(XPathInputEmail)) &&
            isElementVisible(driver, By.xpath(XPathInputPassword)));
    }
}
