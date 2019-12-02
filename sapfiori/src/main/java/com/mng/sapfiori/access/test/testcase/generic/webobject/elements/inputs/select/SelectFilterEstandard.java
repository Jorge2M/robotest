package com.mng.sapfiori.access.test.testcase.generic.webobject.elements.inputs.select;

import org.openqa.selenium.WebDriver;

public class SelectFilterEstandard extends SelectEstandard {

	private final String label;
	
	private SelectFilterEstandard(String label, WebDriver driver) {
		super(driver);
		this.label = label;
	}
	
	public static SelectFilterEstandard getNew(String label, WebDriver driver) {
		return new SelectFilterEstandard(label, driver);
	}
	
	@Override
	String getXPathDivSelector() {
		return "//label[@title='" + label + "']/../..";
	}
	
}
