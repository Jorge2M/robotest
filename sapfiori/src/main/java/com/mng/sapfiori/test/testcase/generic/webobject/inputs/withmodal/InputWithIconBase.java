package com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InputWithIconBase extends InputBase {

	private final static String XPathIconSetFilterRelativeLabel = "/following::div[@class='sapMInputBaseIconContainer']";

	public InputWithIconBase(String label, WebDriver driver) {
		super(label, driver);
	}
	
	void clickIconBase() throws Exception {
		waitForPageFinished(driver);
		String xpathIcon = getXPathLabel() + XPathIconSetFilterRelativeLabel;
		driver.findElement(By.xpath(xpathIcon)).click();
	}
	
}
