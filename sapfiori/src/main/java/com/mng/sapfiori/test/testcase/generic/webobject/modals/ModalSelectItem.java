package com.mng.sapfiori.test.testcase.generic.webobject.modals;

import org.openqa.selenium.WebDriver;


public class ModalSelectItem extends ModalSelectFromListBase {

	ModalSelectItem(String label, WebDriver driver) {
		super(label, driver);
	}
	
	static ModalSelectItem getNew(String label, WebDriver driver) {
		return new ModalSelectItem(label, driver);
	}
}
