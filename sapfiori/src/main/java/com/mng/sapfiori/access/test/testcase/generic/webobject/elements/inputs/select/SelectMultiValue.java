package com.mng.sapfiori.access.test.testcase.generic.webobject.elements.inputs.select;


import java.util.List;

import org.openqa.selenium.WebDriver;

public abstract class SelectMultiValue extends SelectBase {

	static final String XPathOption = "//li[@class[contains(.,'sapMMultiComboBoxItem')]]";
	
	SelectMultiValue(/*String label,*/ WebDriver driver) {
		super(/*label,*/driver);
	}
	
	@Override
	String getXPathOption() {
		return XPathOption;
	}
	
	public void selectByValue(List<String> valuesToSelect) {
		for (String valueToSelect : valuesToSelect) {
			selectByValue(valueToSelect);
		}
	}

	public void selectByPosition(List<Integer> positionsToSelect) {
		for (Integer position : positionsToSelect) {
			selectByPosition(position);
		}
	}
}
