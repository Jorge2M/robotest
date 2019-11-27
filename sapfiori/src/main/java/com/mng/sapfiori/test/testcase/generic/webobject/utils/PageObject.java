package com.mng.sapfiori.test.testcase.generic.webobject.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.makers.StandarElementsMaker;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PageObject extends WebdrvWrapp {
	
	protected final WebDriver driver;
	protected final StandarElementsMaker elementsMaker;
	
	public PageObject(WebDriver driver) {
		this.driver = driver;
		this.elementsMaker = StandarElementsMaker.getNew(driver);
	}
	
	public WebDriver getWebDriver() {
		return this.driver;
	}
	
	public StandarElementsMaker getElementsMaker() {
		return this.elementsMaker;
	}
	
	public void waitForPageFinished() throws Exception {
		waitForPageLoaded(driver);
		StandarElementsMaker standarMaker = StandarElementsMaker.getNew(driver);
		standarMaker.getModalLoading().isInvisibleUntil(5);
	}
	
	public boolean clickAndWaitLoad(By byElem) throws Exception {
		boolean ok = clickAndWaitLoad(driver, byElem);
		waitForPageFinished();
		return ok;
	}
}
