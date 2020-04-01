package com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal;

import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.access.test.testcase.generic.webobject.modals.ModalSelectMultiItem;

public class InputWithIconForSelectItem extends InputWithIconBase {

	private InputWithIconForSelectItem(String label, WebDriver driver) {
		super(label, driver);
	}
	
	public static InputWithIconForSelectItem getNew(String label, WebDriver driver) {
		return new InputWithIconForSelectItem(label, driver);
	}
	
	public ModalSelectMultiItem clickIconSetFilter() {
		super.clickIconBase();
		return elementsMaker.getModalSelectMultiItem(label);
	}
}
