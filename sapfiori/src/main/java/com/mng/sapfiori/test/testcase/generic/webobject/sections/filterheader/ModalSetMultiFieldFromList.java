package com.mng.sapfiori.test.testcase.generic.webobject.sections.filterheader;

import java.util.List;
import org.openqa.selenium.WebDriver;


public class ModalSetMultiFieldFromList extends ModalSetFieldFromListAbstr {

	ModalSetMultiFieldFromList(String label, WebDriver driver) {
		super(label, driver);
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
