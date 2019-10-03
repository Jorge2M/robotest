package com.mng.robotest.test80.mango.test.pageobject.shop.buscador;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;

public class SecSearchMobilOutlet extends WebdrvWrapp implements SecSearch {
	
	private final WebDriver driver;
	
    private final static String XPathInputBuscador = "//form[not(@class)]/input[@class='search-input']";
    private final static String XPathCancelarLink = "//div[@class='search-cancel']";
    
    private SecSearchMobilOutlet(WebDriver driver) {
    	this.driver = driver;
    }
    
    public static SecSearchMobilOutlet getNew(WebDriver driver) {
    	return (new SecSearchMobilOutlet(driver));
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
