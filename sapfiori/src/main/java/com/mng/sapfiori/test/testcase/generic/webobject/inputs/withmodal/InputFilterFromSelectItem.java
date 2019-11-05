package com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal;

import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.modals.ModalSelectMultiItem;

public class InputFilterFromSelectItem extends InputFilterWithModalBase {

	private InputFilterFromSelectItem(String label, WebDriver driver) {
		super(label, driver);
	}
	
	public static InputFilterFromSelectItem getNew(String label, WebDriver driver) {
		return new InputFilterFromSelectItem(label, driver);
	}
	
	public ModalSelectMultiItem clickIconSetFilter() throws Exception {
		super.clickIconBase();
		return ModalSelectMultiItem.getNew(label, driver);
	}
}
