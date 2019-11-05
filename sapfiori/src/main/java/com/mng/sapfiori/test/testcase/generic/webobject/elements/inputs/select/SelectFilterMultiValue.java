package com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.select;

import org.openqa.selenium.WebDriver;

public class SelectFilterMultiValue extends SelectMultiValue {

	private final String label;
	
	private SelectFilterMultiValue(String label, WebDriver driver) {
		super(driver);
		this.label = label;
	}
	
	public static SelectFilterMultiValue getNew(String label, WebDriver driver) {
		return new SelectFilterMultiValue(label, driver);
	}
	
	@Override
	String getXPathDivSelector() {
		return "//label[@title='" + label + "']/../..";
	}
}
