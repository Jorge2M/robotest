package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

@SuppressWarnings("javadoc")
public class SecQuickViewArticulo extends WebdrvWrapp {
    
    static String XPathContainerItem = "//div[@class='container-item']";
    static String XPathContainerDescription = XPathContainerItem + "//div[@class[contains(.,'container-description-title')]]";
    static String XPathReferencia = XPathContainerDescription + "//li[@class='reference']";
    static String XPathNombre = XPathContainerDescription + "//li[1]";
    static String XPathPrecio = XPathContainerDescription + "//ul/li[2]";
    
    public static boolean isVisibleUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathContainerItem), maxSecondsToWait));
    }
    
    public static String getReferencia(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathReferencia)).getText().replaceAll("\\D+",""));
    }
    
    public static String getNombre(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathNombre)).getText());
    }
    
    public static String getPrecio(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathPrecio)).getText());
    }
}
