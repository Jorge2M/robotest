package com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal;

import org.openqa.selenium.WebDriver;

public class InputFilterWithConditions extends InputFilterWithModalBase {

	private InputFilterWithConditions(String label, WebDriver driver) {
		super(label, driver);
	}
	
	public static InputFilterWithConditions getNew(String label, WebDriver driver) {
		return new InputFilterWithConditions(label, driver);
	}
	
	public ModalSelectConditions clickIconSetFilter() throws Exception {
		super.clickIconBase();
		return ModalSelectConditions.getNew(driver);
	}
}
