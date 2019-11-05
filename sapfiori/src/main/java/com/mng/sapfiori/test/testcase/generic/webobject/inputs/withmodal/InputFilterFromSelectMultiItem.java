package com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal;

import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.modals.ModalSelectMultiItem;

public class InputFilterFromSelectMultiItem extends InputFilterWithModalBase {

	private InputFilterFromSelectMultiItem(String label, WebDriver driver) {
		super(label, driver);
	}
	
	public static InputFilterFromSelectMultiItem getNew(String label, WebDriver driver) {
		return new InputFilterFromSelectMultiItem(label, driver);
	}
	
	public ModalSelectMultiItem clickIconSetFilter() throws Exception {
		super.clickIconBase();
		return ModalSelectMultiItem.getNew(label, driver);
	}
}
