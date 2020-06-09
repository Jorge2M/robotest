package com.mng.sapfiori.access.test.testcase.generic.webobject.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.access.test.testcase.generic.webobject.makers.StandarElementsMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

public class PageObject extends PageObjTM {
	
	protected final StandarElementsMaker elementsMaker;
	
	public PageObject(WebDriver driver) {
		super(driver);
		this.elementsMaker = StandarElementsMaker.getNew(driver);
	}
	
	public WebDriver getWebDriver() {
		return this.driver;
	}
	
	public StandarElementsMaker getElementsMaker() {
		return this.elementsMaker;
	}
	
	public void waitForPageFinished() {
		waitForPageLoaded(driver);
		StandarElementsMaker standarMaker = StandarElementsMaker.getNew(driver);
		standarMaker.getModalLoading().isInvisibleUntil(20);
	}
	
	public void clickAndWaitLoad(By byElem) {
		click(byElem).exec();
		waitForPageFinished();
	}
}
