package com.mng.robotest.test.pageobject.shop.buscador;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecSearchDeviceShop extends PageObjTM implements SecSearch {
	
	private static final String XPATH_INPUT_BUSCADOR = "//div[@class='search-component']//form[not(@class)]/input[@class[contains(.,'search-input')]]";
	private static final String XPATH_CANCELAR_LINK = "//div[@class[contains(.,'search-cancel')]]";
	
	private SecSearchDeviceShop(WebDriver driver) {
		super(driver);
	}

	public static SecSearchDeviceShop getNew(WebDriver driver) {
		return (new SecSearchDeviceShop(driver));
	}
	
	@Override
	public void search(String text) {
		state(Visible, By.xpath(XPATH_INPUT_BUSCADOR)).wait(2).check();
		WebElement input = driver.findElement(By.xpath(XPATH_INPUT_BUSCADOR));
		input.clear();
		input.sendKeys(text);
		input.sendKeys(Keys.RETURN);
	}
	
	@Override
	public void close() {
		click(By.xpath(XPATH_CANCELAR_LINK)).exec();
	}
}
