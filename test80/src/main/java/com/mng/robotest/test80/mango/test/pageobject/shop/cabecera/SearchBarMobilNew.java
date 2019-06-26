package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;

public class SearchBarMobilNew extends WebdrvWrapp implements SearchBarMobil {
	
	private final WebDriver driver;
	
	private final static String XPathCapa = "//div[@class='search-layer' or @class='search-box']";
	private final static String XPathInputBuscador = "//div[@class='search-component']//form[not(@class)]/input[@class='search-input']";
    private final static String XPathCancelarLink = "//div[@class[contains(.,'search-cancel')]]";
	
    private SearchBarMobilNew(WebDriver driver) {
    	this.driver = driver;
    }
    
    public static SearchBarMobilNew make(WebDriver driver) {
    	return (new SearchBarMobilNew(driver));
    }
	
	@Override
    public boolean isVisibleUntil(int maxSecondsWait) {
    	return (WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(XPathCapa), maxSecondsWait));
    }
	
    @Override
	public void search(String text) {
        WebElement input = driver.findElement(By.xpath(XPathInputBuscador));
        input.clear();
        sendKeysWithRetry(5, input, text);
        input.sendKeys(Keys.RETURN);
	}
	
    @Override
	public void close() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathCancelarLink));
	}
}
