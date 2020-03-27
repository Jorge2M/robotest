package com.mng.robotest.test80.mango.test.pageobject.shop.buscador;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecSearchMobilShop extends PageObjTM implements SecSearch {
	
	private final static String XPathInputBuscador = "//div[@class='search-component']//form[not(@class)]/input[@class='search-input']";
	private final static String XPathCancelarLink = "//div[@class[contains(.,'search-cancel')]]";
	
	private SecSearchMobilShop(WebDriver driver) {
		super(driver);
	}

	public static SecSearchMobilShop getNew(WebDriver driver) {
		return (new SecSearchMobilShop(driver));
	}
	
	@Override
	public void search(String text) throws Exception {
		state(Visible, By.xpath(XPathInputBuscador)).wait(2).check();
		WebElement input = driver.findElement(By.xpath(XPathInputBuscador));
		input.clear();
		input.sendKeys(text);
		input.sendKeys(Keys.RETURN);
	}
	
	@Override
	public void close() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathCancelarLink));
	}
}
