package com.mng.sapfiori.test.testcase.generic.webobject.sections.filterheader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InputSetFieldFromList {

	private final FieldFilterFromListI fieldFilter;
	private final WebDriver driver;
	
	private final static String TagLabel = "@TagLabel"; 
	private final static String XPathLabelWithTag = "//label[@title='" + TagLabel + "']";
	private final static String XPathInputRelativeLabel = "/following::input";
	private final static String XPathIconSetFilterRelativeLabel = "/following::div[@class='sapMInputBaseIconContainer']";
	
	private InputSetFieldFromList(FieldFilterFromListI fieldFilter, WebDriver driver) {
		this.fieldFilter = fieldFilter;
		this.driver = driver;
	}
	
	public static InputSetFieldFromList getNew(FieldFilterFromListI fieldFilter, WebDriver driver) {
		return new InputSetFieldFromList(fieldFilter, driver);
	}
	
	private String getXPathLabel() {
		return XPathLabelWithTag.replace(TagLabel, fieldFilter.getLabel());
	}
	
	public void sendKeys(String message) {
		String xpathInput = getXPathLabel() + XPathInputRelativeLabel;
		driver.findElement(By.xpath(xpathInput)).sendKeys(message);
	}
	
	public ModalSetFieldFromListI clickIconSetFilter() {
		String xpathIcon = getXPathLabel() + XPathIconSetFilterRelativeLabel;
		driver.findElement(By.xpath(xpathIcon)).click();
		return ModalSetFieldFromListI.make(fieldFilter, driver);
	}
}
