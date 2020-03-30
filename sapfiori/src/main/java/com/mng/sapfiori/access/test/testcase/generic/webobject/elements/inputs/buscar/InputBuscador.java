package com.mng.sapfiori.access.test.testcase.generic.webobject.elements.inputs.buscar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal.InputBase;

public class InputBuscador extends InputBase {

	private final static String XPathForm = "//form[@id[contains(.,'btnBasicSearch-F')]]";
	private final static String XPathInputBuscador = XPathForm + "//input[@type='search']";
	private final static String XPathLupa = XPathForm + "//div[@id[contains(.,'btnBasicSearch-search')]]";
	
	private InputBuscador(WebDriver driver) {
		super(XPathInputBuscador, driver);
	}
	
	public static InputBuscador getNew(WebDriver driver) {
		return new InputBuscador(driver);
	}
	
	public void clickLupaForSearch() {
		clickAndWaitLoad(By.xpath(XPathLupa));
	}
}
