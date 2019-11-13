package com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal;

import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.modals.ModalSelectMultiItem;

public class InputWithIconForSelectItem extends InputWithIconBase {

	private InputWithIconForSelectItem(String label, WebDriver driver) {
		super(label, driver);
	}
	
	public static InputWithIconForSelectItem getNew(String label, WebDriver driver) {
		return new InputWithIconForSelectItem(label, driver);
	}
	
	public ModalSelectMultiItem clickIconSetFilter() throws Exception {
		super.clickIconBase();
		return standarElements.getModalSelectMultiItem(label, driver);
	}
}
