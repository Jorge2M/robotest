package com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.sapfiori.access.test.testcase.generic.webobject.utils.PageObject;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class InputBase extends PageObject {
	
	private final String xpathInput;
	private final String xpathItemInInputCrossIcon;
	
	public InputBase(String xpathInput, WebDriver driver) {
		super(driver);
		this.xpathInput = xpathInput;
		this.xpathItemInInputCrossIcon = 
				xpathInput + "/..//div[@class='sapMTokenizer']//span[@id[contains(.,'-icon')]]"; 
	}

	public void clearAndSendText(CharSequence... message) {
		waitForPageFinished();
		clear();
		waitMillis(200);
		waitForPageFinished();
		WebElement inputElem = getInputElement();
		inputElem = getInputElement(); //For avoid possible StaleElementException
		inputElem.sendKeys(message);
		waitMillis(200);
		waitForPageFinished();
	}
	
	public void clear() {
		By byItemCrossIcon = By.xpath(xpathItemInInputCrossIcon);
		if (state(Clickable, byItemCrossIcon).check()) {
			driver.findElement(byItemCrossIcon).click();
		}
		WebElement inputElem = getInputElement();
		inputElem.clear();
	}
	
	public void sendText(CharSequence... message) {
		waitForPageFinished();
		getInputElement().sendKeys(message);
	}
	
	public WebElement getInputElement() {
		return driver.findElement(By.xpath(xpathInput));
	}
	
	public boolean isVisible() {
		return (state(Visible, By.xpath(xpathInput), driver).check());
	}
}
