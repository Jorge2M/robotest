package com.mng.sapfiori.test.testcase.generic.webobject.makers;

import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.select.SelectEstandardWithoutLabel;

public class StandarElementsMaker {

	private final WebDriver driver;
	
	private StandarElementsMaker(WebDriver driver) {
		this.driver = driver;
	}
	
	public static StandarElementsMaker getNew(WebDriver driver) {
		return new StandarElementsMaker(driver);
	}
	
	public SelectEstandardWithoutLabel getSelectEstandardWithoutLabel(int id) {
		return SelectEstandardWithoutLabel.getNew(id, driver);
	}
}
