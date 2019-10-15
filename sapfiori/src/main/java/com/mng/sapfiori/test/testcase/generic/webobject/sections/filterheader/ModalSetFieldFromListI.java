package com.mng.sapfiori.test.testcase.generic.webobject.sections.filterheader;

import org.openqa.selenium.WebDriver;

public interface ModalSetFieldFromListI {

	boolean isModalVisible();
	void clickEnterToShowInitialElements() throws Exception;
	void clickOkButton() throws Exception;
	boolean isElementListPresent(int maxSeconds);
	void selectElementByPosition(int posElement) throws Exception;
	void selectElementByValue(String valueElement) throws Exception;
	
	public static ModalSetFieldFromListI make(FieldFilterFromListI fieldFilter, WebDriver driver) {
		if (fieldFilter.isMultiSelect()) {
			return (new ModalSetMultiFieldFromList(fieldFilter.getLabel(), driver));
		}
		return (new ModalSetOneFieldFromList(fieldFilter.getLabel(), driver));
	}
}
