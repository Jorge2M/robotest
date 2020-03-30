package com.mng.testmaker.service.webdriver.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.service.webdriver.pageobject.ClickElement.BuilderClick;
//import com.mng.testmaker.domain.suitetree.TestCaseTM;
import com.mng.testmaker.service.webdriver.pageobject.StateElement.BuilderState;
import com.mng.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageObjTM extends WebdrvWrapp {

	public final WebDriver driver;
	
	public PageObjTM(WebDriver driver) {
		this.driver = driver;
	}

	public BuilderClick click(By by) {
		return new BuilderClick(by, driver);
	}
	public BuilderClick click(WebElement webelement) {
		return new BuilderClick(webelement, driver);
	}
	public static BuilderClick click(By by, WebDriver driver) {
		return new BuilderClick(by, driver);
	}
	public static BuilderClick click(WebElement webelement, WebDriver driver) {
		return new BuilderClick(webelement, driver);
	}
	
	public BuilderState state(State state, By by) {
		return new BuilderState(state, by, driver);
	}
	public BuilderState state(State state, WebElement webelement) {
		return new BuilderState(state, webelement, driver);
	}
	public static BuilderState state(State state, By by, WebDriver driver) {
		return new BuilderState(state, by, driver);
	}
	public static BuilderState state(State state, WebElement webelement, WebDriver driver) {
		return new BuilderState(state, webelement, driver);
	}
	
}
