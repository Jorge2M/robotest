package com.mng.sapfiori.test.testcase.generic.webobject.utils;

import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.makers.StandarElementsMaker;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class SeleniumUtils extends WebdrvWrapp {
	
	
	public static void waitForPageFinished(WebDriver driver) throws Exception {
		waitForPageLoaded(driver);
		StandarElementsMaker standarMaker = StandarElementsMaker.getNew(driver);
		standarMaker.getModalLoading(driver).isInvisibleUntil(5);
	}
	
}
