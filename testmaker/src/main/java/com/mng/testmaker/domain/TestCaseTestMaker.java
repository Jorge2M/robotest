package com.mng.testmaker.domain;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;

public class TestCaseTestMaker  {

	private final Method method;
	private WebDriver driver;

	public TestCaseTestMaker(Method method) {
		this.method = method;
	}
	
	public Method getMethod() {
		return this.method;
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
}
