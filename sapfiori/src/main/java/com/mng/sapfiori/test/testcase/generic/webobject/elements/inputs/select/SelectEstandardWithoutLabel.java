package com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.select;

import org.openqa.selenium.WebDriver;

public class SelectEstandardWithoutLabel extends SelectEstandard {

	private final int id;
	
	private SelectEstandardWithoutLabel(int id, WebDriver driver) {
		super(driver);
		this.id = id;
	}
	
	public static SelectEstandardWithoutLabel getNew(int id, WebDriver driver) {
		return new SelectEstandardWithoutLabel(id, driver);
	}
	
	@Override
	String getXPathDivSelector() {
		return "//div[@aria-labelledby[contains(.,'select" + id + "-label')]]";
	}
	
}
