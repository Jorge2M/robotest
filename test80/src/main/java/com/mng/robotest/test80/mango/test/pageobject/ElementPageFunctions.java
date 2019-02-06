package com.mng.robotest.test80.mango.test.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp.OptionSelect;

public class ElementPageFunctions {

	public enum StateElem {Present, Visible, Clickable}
	
    public static boolean isElementInState(ElementPage element, StateElem state, WebDriver driver) {
    	int maxSecondsWait = 0;
    	return (isElementInStateUntil(element, state, maxSecondsWait, driver));
    }
	
    public static boolean isElementInStateUntil(ElementPage element, StateElem state, int maxSecondsWait, WebDriver driver) {
    	By elemBy = By.xpath(element.getXPath());
    	return (isElementInStateUntil(elemBy, state, maxSecondsWait, driver));
    }
    
    public static boolean isElementInState(ElementPage element, StateElem state, Channel channel, WebDriver driver) {
       	int maxSecondsWait = 0;
    	return (isElementInStateUntil(element, state, maxSecondsWait, channel, driver));
    }

    public static boolean isElementInStateUntil(ElementPage element, StateElem state, int maxSecondsWait, Channel channel, WebDriver driver) {
    	By elemBy = By.xpath(element.getXPath(channel));
    	return (isElementInStateUntil(elemBy, state, maxSecondsWait, driver));
    }
    
    private static boolean isElementInStateUntil(By elemBy, StateElem state, int maxSecondsWait, WebDriver driver) {
    	switch (state) {
    	case Visible:
    		return WebdrvWrapp.isElementVisibleUntil(driver, elemBy, maxSecondsWait);
    	case Clickable:
    		return WebdrvWrapp.isElementClickableUntil(driver, elemBy, maxSecondsWait);
    	case Present:
    	default:
        	return WebdrvWrapp.isElementPresentUntil(driver, elemBy, maxSecondsWait);
    	}
    }

    public static void selectInDropDown(ElementPage dropDown, String value, WebDriver driver) {
        new Select(driver.findElement(By.xpath(dropDown.getXPath()))).selectByValue(value);
    }

    public static void selectElementWaitingForAvailability(ElementPage element, int secondsWaitForElement, WebDriver driver)
            throws Exception {
        Thread.sleep(100);
        WebdrvWrapp.waitClickAndWaitLoad(driver, secondsWaitForElement, By.xpath(element.getXPath()));
    }

    public static void clickAndWait(ElementPage element, WebDriver driver) throws Exception {
    	WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(element.getXPath()));
    }

    public static void clickAndWait(ElementPage element, int maxSecondsToWait, WebDriver driver) throws Exception {
        WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(element.getXPath()), maxSecondsToWait);
    }
    
    public static void clickElementVisibleAndWaitLoad(ElementPage element, int maxSecondsToWait, WebDriver driver) throws Exception {
        WebdrvWrapp.clickElementVisibleAndWaitLoad(driver, By.xpath(element.getXPath()), maxSecondsToWait);
    }

    public static void clickAndWait(Channel channel, ElementPage element, WebDriver driver) throws Exception {
        WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(element.getXPath(channel)));
    }

    public static void clickIfPossibleAndWait(ElementPage element, WebDriver driver) throws Exception {
    	if (isElementInState(element, StateElem.Clickable, driver))
    		clickAndWait(element, driver);
    }

    public static void clickAndRetry(ElementPage element, WebDriver driver) throws Exception {
        clickAndWait(element, driver);

        int maxSecondsToWait = 2;
        if (isElementInStateUntil(element, StateElem.Present, maxSecondsToWait, driver))
            clickAndWait(element, driver);
    }

    public static void clickAndRetry(Channel channel, ElementPage element, WebDriver driver) throws Exception {
        clickAndWait(channel, element, driver);

        int maxSecondsToWait = 2;
        if (isElementInStateUntil(element, StateElem.Present, maxSecondsToWait, channel, driver))
            clickAndWait(channel, element, driver);
    }

    public static void selectElement(ElementPage element, WebDriver driver) throws Exception {
    	WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(element.getXPath()), 3);
    }

    public static void selectElement(ElementPage element, Channel channel, WebDriver driver, WebdrvWrapp.TypeOfClick typeOfClick) throws Exception {
        WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(element.getXPath(channel)), 3);
	}

    public static void selectElement(ElementPage element, Channel channel, WebDriver driver) throws Exception {
    	WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(element.getXPath(channel)), 3);
    }

    public static void moveToAndSelectElement(ElementPage element, WebDriver driver) throws Exception {
    	WebdrvWrapp.moveToElement(By.xpath(element.getXPath()), driver);
        selectElement(element, driver);
    }

    public static void inputDataInElement(ElementPage element, String dataToInput, WebDriver driver) throws Exception {
    	WebdrvWrapp.sendKeysWithRetry(2, dataToInput, By.xpath(element.getXPath()), driver);
    }
    
    public static WebElement getElementWeb(ElementPage element, WebDriver driver) {
    	try {
    		return (driver.findElement(By.xpath(element.getXPath())));
    	}
    	catch (NoSuchElementException e) {
    		return null;
    	}
    }
    
    public static void selectByValue(ElementPage select, String value, OptionSelect typeSelect, WebDriver driver) {
    	By selectBy = By.xpath(select.getXPath());
    	WebdrvWrapp.selectOption(selectBy, value, typeSelect, driver);
    }
}
