package com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.sapfiori.test.testcase.generic.webobject.utils.PageObject;

public class InputBase extends PageObject {

	final String label;
	
	private final static String TagLabel = "@TagLabel";
	private final static String XPathLabelWithTag = "//bdi[text()='" + TagLabel + "']/..";
	private final static String XPathInputRelativeLabel = "/following::input";

	
	public InputBase(String label, WebDriver driver) {
		super(driver);
		this.label = label;
	}
	
	String getXPathLabel() {
		return XPathLabelWithTag.replace(TagLabel, label);
	}
	
	public void clearAndSendText(String message) throws Exception {
		WebElement inputElem = driver.findElement(By.xpath(getXPathLabel() + XPathInputRelativeLabel));
		PageObject.waitMillis(500);
		inputElem.clear();
		PageObject.sendKeysWithRetry(2, inputElem, message);
	}
	
	public void sendText(String message) {
		String xpathInput = getXPathLabel() + XPathInputRelativeLabel;
		driver.findElement(By.xpath(xpathInput)).sendKeys(message);
	}
}
