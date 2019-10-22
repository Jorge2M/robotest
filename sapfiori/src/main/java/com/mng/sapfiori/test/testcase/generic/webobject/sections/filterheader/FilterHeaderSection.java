package com.mng.sapfiori.test.testcase.generic.webobject.sections.filterheader;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class FilterHeaderSection extends WebdrvWrapp {

	private final WebDriver driver;
	
	public FilterHeaderSection(WebDriver driver) {
		this.driver = driver;
	}
	
	public InputSetFieldFromList getInputSetFieldFromList(FieldFilterFromListI fieldFilter, WebDriver driver) {
		return InputSetFieldFromList.getNew(fieldFilter, driver);
	}
	
//	public InputFechaModule getInputFecha(String label) {
//		return InputFechaModule.getNew(label, driver);
//	}
}
