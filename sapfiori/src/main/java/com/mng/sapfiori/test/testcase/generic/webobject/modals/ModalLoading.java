package com.mng.sapfiori.test.testcase.generic.webobject.modals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.utils.SeleniumUtils;

public class ModalLoading extends SeleniumUtils {

	private final WebDriver driver;
	private final static String XPathDivLoading = "//div[@class[contains(.,'sapUiLocalBusy')]]";
	
	private ModalLoading(WebDriver driver) {
		this.driver = driver;
	}
	public static ModalLoading getNew(WebDriver driver) {
		return new ModalLoading(driver);
	}

	public boolean isVisibleUntil(int maxSeconds) {
		return isElementVisibleUntil(driver, By.xpath(XPathDivLoading), maxSeconds);
	}
	public boolean isInvisibleUntil(int maxSeconds) {
		return isElementInvisibleUntil(driver, By.xpath(XPathDivLoading), maxSeconds);
	}
}
