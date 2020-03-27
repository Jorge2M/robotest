package com.mng.testmaker.service.webdriver.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

//import com.mng.testmaker.domain.suitetree.TestCaseTM;
import com.mng.testmaker.service.webdriver.pageobject.StateElement.Builder;
import com.mng.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PageObjTM extends WebdrvWrapp {

	public final WebDriver driver;
	
	public PageObjTM(WebDriver driver) {
		this.driver = driver;
	}
//	public PageObjTM() {
//		this.driver = TestCaseTM.getTestCaseInExecution().getDriver();
//	}

	public Builder state(State state, By by) {
		return new Builder(state, by, driver);
	}
	public Builder state(State state, WebElement webelement) {
		return new Builder(state, webelement, driver);
	}
	
	public static Builder state(State state, By by, WebDriver driver) {
		return new Builder(state, by, driver);
	}
	public static Builder state(State state, WebElement webelement, WebDriver driver) {
		return new Builder(state, webelement, driver);
	}
	
}
