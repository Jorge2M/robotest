package com.mng.sapfiori.access.test.testcase.generic.webobject.modals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.access.test.testcase.generic.webobject.utils.PageObject;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalLoading extends PageObject {

	private final static String XPathDivLoading = "//div[@class[contains(.,'sapUiLocalBusy')]]";
	
	private ModalLoading(WebDriver driver) {
		super(driver);
	}
	public static ModalLoading getNew(WebDriver driver) {
		return new ModalLoading(driver);
	}

	public boolean isVisibleUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathDivLoading)).wait(maxSeconds).check());
	}
	public boolean isInvisibleUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPathDivLoading)).wait(maxSeconds).check());
	}
}
