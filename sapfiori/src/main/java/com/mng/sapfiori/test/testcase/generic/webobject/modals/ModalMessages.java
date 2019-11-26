package com.mng.sapfiori.test.testcase.generic.webobject.modals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.utils.PageObject;

public class ModalMessages extends PageObject {

	private final static String XPathModal = "//div[@id[contains(.,'-messageDialog')]]";
	
	private ModalMessages(WebDriver driver) {
		super(driver);
	}
	
	public static ModalMessages getNew(WebDriver driver) {
		return new ModalMessages(driver);
	}
	
	public boolean isVisible() {
		return isVisibleUntil(0);
	}
	
	public boolean isVisibleUntil(int maxSeconds) {
		return isElementVisibleUntil(driver, By.xpath(XPathModal), maxSeconds);
	}
}
