package com.mng.sapfiori.access.test.testcase.generic.webobject.modals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.access.test.testcase.generic.webobject.utils.PageObject;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

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
		return (state(Visible, By.xpath(XPathModal)).wait(maxSeconds).check());
	}
}
