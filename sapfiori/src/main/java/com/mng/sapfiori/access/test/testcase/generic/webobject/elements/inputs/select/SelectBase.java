package com.mng.sapfiori.access.test.testcase.generic.webobject.elements.inputs.select;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public abstract class SelectBase extends WebdrvWrapp {

	final WebDriver driver;
	
	private static final String XPathArrowRelative = "//span[@id[contains(.,'-arrow')]]";

	SelectBase(WebDriver driver) {
		this.driver = driver;
	}
	
	abstract String getXPathOption();
	abstract String getXPathDivSelector();
	
	private String getXPathArrowForUnfold() {
		String xpathSelector = getXPathDivSelector();
		return xpathSelector + XPathArrowRelative;
	}
	
	void clickArrowToUnfold() {
		By byArrow = By.xpath(getXPathArrowForUnfold());
		driver.findElement(byArrow).click();
	}
	
	public void selectByValue(String valueToSelect) {
		WebElement optionElem = unfoldSelectAndGetOption(valueToSelect);
		if (optionElem==null) {
			waitMillis(300);
			optionElem = unfoldSelectAndGetOption(valueToSelect);
		}
		optionElem.click();
	}
	
	private WebElement unfoldSelectAndGetOption(String valueToSelect) {
		clickArrowToUnfold();
		return (getOption(valueToSelect));
	}
	
	private WebElement getOption(String valueToSelect) {
		WebElement option = getOptionV1(valueToSelect);
		if (option!=null) {
			return option;
		} else {
			return (getOptionV2(valueToSelect));
		}
	}
	
	private WebElement getOptionV1(String valueToSelect) {
		By byOption = By.xpath(getXPathOption() + "//self::*[text()='" + valueToSelect + "']");
		return (getElementVisible(driver, byOption));
	}
	
	private WebElement getOptionV2(String valueToSelect) {
		By byOption = By.xpath(getXPathOption());
		for (WebElement option : driver.findElements(byOption)) {
			if (valueToSelect.compareTo(option.getText())==0 &&
				option.isDisplayed()) {
				return option;
			}
		}
		return null;
	}
	
	public void selectByPosition(int positionToSelect) {
		clickArrowToUnfold();
		By byOption = By.xpath("(" + getXPathOption() + ")[" + positionToSelect + "]");
		WebdrvWrapp.isElementVisibleUntil(driver, byOption, 1);
		WebElement optionElem = getElementVisible(driver, byOption);
		optionElem.click();
	}
}
