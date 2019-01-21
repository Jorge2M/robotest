package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class SecCrossSelling extends WebdrvWrapp {
    static String XPathSection = "//section[@class='cross-selling']";
    
    public static String getXPath_link(int numLink) {
        return ("(" + XPathSection + "/a)[" + numLink + "]");
    }
    
    public static boolean isSection(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathSection)));
    }
    
    /**
     * @return si un link del cross-selling está asociado a un determinado menú (validamos que coincida el texto y el href) 
     */
    public static boolean linkAssociatedToMenu(int numLink, WebElement menu1erNivel, WebDriver driver) {
        String xpathLink = getXPath_link(numLink);
        WebElement link = getElementVisible(driver, By.xpath(xpathLink));
        if (link!=null &&
            link.getAttribute("innerHTML").compareTo(menu1erNivel.getAttribute("innerHTML"))==0 &&
            link.getAttribute("href").compareTo(menu1erNivel.getAttribute("href"))==0)
            return true;
         
        return false;
    }
}
