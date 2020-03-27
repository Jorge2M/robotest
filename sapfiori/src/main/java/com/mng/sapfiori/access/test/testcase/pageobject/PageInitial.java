package com.mng.sapfiori.access.test.testcase.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.access.test.testcase.generic.webobject.utils.PageObject;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageInitial extends PageObject {

	private final static String XPathLabelInitialPageSpanish = "//h1[text()='Página inicial']";
	
	private PageInitial(WebDriver driver) {
		super(driver);
	}
	
	public static PageInitial getNew(WebDriver driver) {
		return new PageInitial(driver);
	}
	
	public boolean checkIsInitialPageSpanish() {
		return (state(Visible, By.xpath(XPathLabelInitialPageSpanish)).check());
	}
	
}
