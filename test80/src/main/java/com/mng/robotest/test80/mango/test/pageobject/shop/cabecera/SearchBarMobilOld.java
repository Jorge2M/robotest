package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;

public class SearchBarMobilOld extends WebdrvWrapp implements SearchBarMobil {
	
	private final WebDriver driver;
	
	private final static String XPathCapa = "//div[@class='menu-search-layer']";
    private final static String XPathInputBuscador = "//form[not(@class)]/input[@class='search-input']";
    private final static String XPathCancelarLink = "//div[@class='search-cancel']";
    
    private SearchBarMobilOld(WebDriver driver) {
    	this.driver = driver;
    }
    
    public static SearchBarMobilOld make(WebDriver driver) {
    	return (new SearchBarMobilOld(driver));
    }
    
    @Override
    public boolean isVisibleUntil(int maxSecondsWait) {
    	return (WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(XPathCapa), maxSecondsWait));
    }
    
    @Override
	public void search(String text) {
        WebElement input = getElementVisible(driver, By.xpath(XPathInputBuscador));
        input.clear();
        sendKeysWithRetry(5, input, text);
        input.sendKeys(Keys.RETURN);
	}
	
    @Override
	public void close() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathCancelarLink));
	}
    
    public boolean isBuscadorVisibleUntil(int maxSeconds) {
    	return (isElementVisibleUntil(driver, By.xpath(XPathInputBuscador), maxSeconds));
    }
}
