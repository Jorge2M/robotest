package com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.select;

import org.openqa.selenium.WebDriver;

public abstract class SelectEstandard extends SelectBase {

	static final String XPathOption = "//li[@class[contains(.,'sapMSelectListItemBaseHoverable')]]";
	
	SelectEstandard(/*String label,*/ WebDriver driver) {
		super(/*label,*/driver);
	}
	
	@Override
	String getXPathOption() {
		return XPathOption;
	}
}
