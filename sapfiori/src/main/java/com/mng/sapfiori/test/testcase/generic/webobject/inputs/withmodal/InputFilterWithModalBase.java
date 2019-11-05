package com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class InputFilterWithModalBase extends WebdrvWrapp {

	final String label;
	final WebDriver driver;
	
	private final static String TagLabel = "@TagLabel"; 
	private final static String XPathLabelWithTag = "//label[@title='" + TagLabel + "']";
	private final static String XPathInputRelativeLabel = "/following::input";
	private final static String XPathIconSetFilterRelativeLabel = "/following::div[@class='sapMInputBaseIconContainer']";
	
	public InputFilterWithModalBase(String label, WebDriver driver) {
		this.label = label;
		this.driver = driver;
	}
	
	private String getXPathLabel() {
		return XPathLabelWithTag.replace(TagLabel, label);
	}
	
	public void sendKeys(String message) {
		String xpathInput = getXPathLabel() + XPathInputRelativeLabel;
		driver.findElement(By.xpath(xpathInput)).sendKeys(message);
	}
	
	void clickIconBase() throws Exception {
		waitForPageLoaded(driver);
		String xpathIcon = getXPathLabel() + XPathIconSetFilterRelativeLabel;
		driver.findElement(By.xpath(xpathIcon)).click();
	}
	
}
