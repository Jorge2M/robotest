package com.mng.robotest.test80.mango.test.pageobject.shop.buscador;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class SecSearchMobilShop extends WebdrvWrapp implements SecSearch {
	
	private final WebDriver driver;
	
	private final static String XPathInputBuscador = "//div[@class='search-component']//form[not(@class)]/input[@class='search-input']";
    private final static String XPathCancelarLink = "//div[@class[contains(.,'search-cancel')]]";
	
    private SecSearchMobilShop(WebDriver driver) {
    	this.driver = driver;
    }
    
    public static SecSearchMobilShop getNew(WebDriver driver) {
    	return (new SecSearchMobilShop(driver));
    }
	
    @Override
	public void search(String text) throws Exception {
    	isElementVisibleUntil(driver, By.xpath(XPathInputBuscador), 2);
        WebElement input = driver.findElement(By.xpath(XPathInputBuscador));
        input.clear();
        input.sendKeys(text);
        //sendKeysWithRetry(5, input, text);
        input.sendKeys(Keys.RETURN);
	}
	
    @Override
	public void close() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathCancelarLink));
	}
}
