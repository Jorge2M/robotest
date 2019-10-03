package com.mng.sapfiori.test.testcase.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;

public class PageInitial {

	private final WebDriver driver;
	
	private final static String XPathLabelInitialPageSpanish = "//h1[text()='Página inicial']";
	
	private PageInitial(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageInitial getNew(WebDriver driver) {
		return new PageInitial(driver);
	}
	
	public boolean checkIsInitialPageSpanish() {
		return WebdrvWrapp.isElementVisible(driver, By.xpath(XPathLabelInitialPageSpanish));
	}
	
}
