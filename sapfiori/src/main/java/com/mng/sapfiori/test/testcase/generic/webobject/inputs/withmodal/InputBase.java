package com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.sapfiori.test.testcase.generic.webobject.utils.PageObject;

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
		//PageObject.waitMillis(200);
		inputElem.sendKeys(message);
		//PageObject.waitMillis(200);
	}
	
	public void sendText(CharSequence... message) throws Exception {
		waitForPageFinished();
		getInputElement().sendKeys(message);
	}
	
	public WebElement getInputElement() {
		return driver.findElement(By.xpath(xpathInput));
	}
	
	public boolean isVisible() {
		return PageObject.isElementVisible(driver, By.xpath(xpathInput));
	}
}
