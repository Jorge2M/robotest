package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class SecQuickViewArticulo extends WebdrvWrapp {
    
	private final WebDriver driver;
	
    private static String XPathContainerItem = "//div[@class='container-item']";
    private static String XPathContainerDescription = XPathContainerItem + "//div[@class[contains(.,'container-description-title')]]";
    private static String XPathReferencia = XPathContainerDescription + "//li[@class='reference']";
    private static String XPathNombre = XPathContainerDescription + "//li[1]";
    private static String XPathPrecio = XPathContainerDescription + "//ul/li[2]";
    
	private SecQuickViewArticulo(WebDriver driver) {
		this.driver = driver;
	}
	public static SecQuickViewArticulo getNew(WebDriver driver) {
		return new SecQuickViewArticulo(driver);
	}
    
    public boolean isVisibleUntil(int maxSecondsToWait) {
        return (isElementVisibleUntil(driver, By.xpath(XPathContainerItem), maxSecondsToWait));
    }
    
    public String getReferencia() {
        return (driver.findElement(By.xpath(XPathReferencia)).getText().replaceAll("\\D+",""));
    }
    
    public String getNombre() {
        return (driver.findElement(By.xpath(XPathNombre)).getText());
    }
    
    public String getPrecio() {
        return (driver.findElement(By.xpath(XPathPrecio)).getText());
    }
}
