package com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.buscar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class InputBuscador extends WebdrvWrapp {

	private final WebDriver driver;
	
	private final static String XPathForm = "//form[@id[contains(.,'btnBasicSearch-F')]]";
	private final static String XPathInputBuscador = XPathForm + "//input[@type='search']";
	private final static String XPathLupa = XPathForm + "//div[@id[contains(.,'btnBasicSearch-search')]]";
	
	private InputBuscador(WebDriver driver) {
		this.driver = driver;
	}
	
	public static InputBuscador getNew(WebDriver driver) {
		return new InputBuscador(driver);
	}
	
	public void sendText(String text) {
		driver.findElement(By.xpath(XPathInputBuscador)).sendKeys(text);
	}
	
	public void clickLupaForSearch() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathLupa));
	}
}
