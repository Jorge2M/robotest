package com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.sapfiori.access.test.testcase.generic.webobject.utils.PageObject;

public class InputBase extends PageObject {
	
	private final String xpathInput;
	
	public InputBase(String xpathInput, WebDriver driver) {
		super(driver);
		this.xpathInput = xpathInput;
	}

	public void clearAndSendText(CharSequence... message) throws Exception {
		waitForPageFinished();
		WebElement inputElem = getInputElement();
		inputElem.clear();
		waitMillis(200);
		waitForPageFinished();
		inputElem = getInputElement(); //For avoid possible StaleElementException
		inputElem.sendKeys(message);
		waitMillis(200);
		waitForPageFinished();
	}
	
	public void sendText(CharSequence... message) throws Exception {
		waitForPageFinished();
		getInputElement().sendKeys(message);
	}
	
	public WebElement getInputElement() {
		return driver.findElement(By.xpath(xpathInput));
	}
	
	public boolean isVisible() {
		return isElementVisible(driver, By.xpath(xpathInput));
	}
}
