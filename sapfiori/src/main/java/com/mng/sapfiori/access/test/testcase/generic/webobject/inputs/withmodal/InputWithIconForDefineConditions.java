package com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal;

import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.access.test.testcase.generic.webobject.modals.ModalSelectConditions;

public class InputWithIconForDefineConditions extends InputWithIconBase {

	private InputWithIconForDefineConditions(String label, WebDriver driver) {
		super(label, driver);
	}
	
	public static InputWithIconForDefineConditions getNew(String label, WebDriver driver) {
		return new InputWithIconForDefineConditions(label, driver);
	}
	
	public ModalSelectConditions clickIconSetFilter() throws Exception {
		super.clickIconBase();
		return elementsMaker.getModalSelectConditions();
	}
}
