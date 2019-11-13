package com.mng.sapfiori.test.testcase.generic.webobject.inputs.withmodal;

import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.modals.ModalSelectMultiItem;

public class InputWithIconForSelectMultiItem extends InputWithIconBase {

	private InputWithIconForSelectMultiItem(String label, WebDriver driver) {
		super(label, driver);
	}
	
	public static InputWithIconForSelectMultiItem getNew(String label, WebDriver driver) {
		return new InputWithIconForSelectMultiItem(label, driver);
	}
	
	public ModalSelectMultiItem clickIconSetFilter() throws Exception {
		super.clickIconBase();
		return standarElements.getModalSelectMultiItem(label, driver);
	}
}
