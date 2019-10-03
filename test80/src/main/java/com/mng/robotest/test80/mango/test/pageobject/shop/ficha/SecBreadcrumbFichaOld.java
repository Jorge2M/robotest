package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


public class SecBreadcrumbFichaOld extends WebdrvWrapp {
    public enum ItemBCrumb {Line, Group, Galery}
    
    static String XPathLinea = "//nav[@class='nav-product']";
    static String XPathBreadCrumb = "//ol[@typeof='BreadcrumbList']";
    static String XPathBreadCrumbItem = XPathBreadCrumb + "/li[@typeof='ListItem']";
    
    public static String getXPathBreadCrumbItem(ItemBCrumb itemBCrumb) {
        int position = 1;
        switch (itemBCrumb) {
        case Line: 
            position = 1;
            break;
        case Group:
            position = 2;
            break;
        case Galery:
        default:
            position = 3;
        }
        
        return "(" + XPathBreadCrumbItem + ")[" + position + "]";
    }
    
    public static boolean isVisibleBreadCrumb(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathBreadCrumb))); 
    }
    
    public static String getUrlItemBreadCrumb(ItemBCrumb itemBCrumb, WebDriver driver) {
        String xpathItem = getXPathBreadCrumbItem(itemBCrumb) + "//a";
        if (isElementVisible(driver, By.xpath(xpathItem))) {
            return driver.findElement(By.xpath(xpathItem)).getAttribute("href");
        }
        return "";
    }
    
    public static String getNameItemBreadCrumb(ItemBCrumb itemBCrumb, WebDriver driver) {
        String xpathItem = getXPathBreadCrumbItem(itemBCrumb) + "//span";
        if (isElementVisible(driver, By.xpath(xpathItem))) {
            return driver.findElement(By.xpath(xpathItem)).getAttribute("innerHTML");
        }
        return "";        
    }
}
