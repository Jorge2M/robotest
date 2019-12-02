package com.mng.sapfiori.access.test.testcase.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PageInitial extends WebdrvWrapp {

	private final WebDriver driver;
	
	private final static String XPathLabelInitialPageSpanish = "//h1[text()='Página inicial']";
	
	private PageInitial(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageInitial getNew(WebDriver driver) {
		return new PageInitial(driver);
	}
	
	public boolean checkIsInitialPageSpanish() {
		return isElementVisible(driver, By.xpath(XPathLabelInitialPageSpanish));
	}
	
}
