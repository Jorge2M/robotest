package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


public class SecCrossSelling extends WebdrvWrapp {
    static String XPathSection = "//section[@class='cross-selling']";
    
    public static String getXPath_link(int numLink) {
        return ("(" + XPathSection + "/a)[" + numLink + "]");
    }
    
    public static boolean isSectionVisible(WebDriver driver) {
    	return (isElementVisible(driver, By.xpath(XPathSection)));
    }
    
    /**
     * @return si un link del cross-selling está asociado a un determinado menú (validamos que coincida el texto y el href) 
     */
    public static boolean linkAssociatedToMenu(int numLink, String litMenu, String hrefMenu, WebDriver driver) {
        String xpathLink = getXPath_link(numLink);
        WebElement link = getElementVisible(driver, By.xpath(xpathLink));
        return (
        	link!=null &&
            link.getAttribute("innerHTML").compareTo(litMenu)==0 &&
            link.getAttribute("href").compareTo(hrefMenu)==0);
    }
}
