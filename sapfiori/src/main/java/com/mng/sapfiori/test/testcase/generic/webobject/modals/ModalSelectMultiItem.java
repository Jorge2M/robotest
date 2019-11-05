package com.mng.sapfiori.test.testcase.generic.webobject.modals;

import java.util.List;
import org.openqa.selenium.WebDriver;


public class ModalSelectMultiItem extends ModalSelectFromListBase {

	ModalSelectMultiItem(String label, WebDriver driver) {
		super(label, driver);
	}
	
	public static ModalSelectMultiItem getNew(String label, WebDriver driver) {
		return new ModalSelectMultiItem(label, driver);
	}
	
	public void selectElementsByPosition(List<Integer> listPosElementsToSelect) throws Exception {
		for (int posElement : listPosElementsToSelect) {
			selectElementByPosition(posElement);
		}
	}
	
	public void selectElementsByValue(List<String> listValueElementsToSelect) throws Exception {
		for (String valueElement : listValueElementsToSelect) {
			selectElementByValue(valueElement);
		}
	}
}
