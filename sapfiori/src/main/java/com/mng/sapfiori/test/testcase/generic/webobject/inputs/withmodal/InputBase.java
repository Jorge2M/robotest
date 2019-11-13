package com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.makers.StandarElementsMaker;
import com.mng.sapfiori.test.testcase.generic.webobject.utils.SeleniumUtils;

public class InputBase extends SeleniumUtils {

	final String label;
	final WebDriver driver;
	final StandarElementsMaker standarElements;
	
	private final static String TagLabel = "@TagLabel";
	private final static String XPathLabelWithTag = "//bdi[text()='" + TagLabel + "']/..";
	private final static String XPathInputRelativeLabel = "/following::input";

	
	public InputBase(String label, WebDriver driver) {
		this.label = label;
		this.driver = driver;
		this.standarElements = StandarElementsMaker.getNew(driver);
	}
	
	String getXPathLabel() {
		return XPathLabelWithTag.replace(TagLabel, label);
	}
	
	public void sendKeys(String message) {
		String xpathInput = getXPathLabel() + XPathInputRelativeLabel;
		driver.findElement(By.xpath(xpathInput)).sendKeys(message);
	}
	
}
