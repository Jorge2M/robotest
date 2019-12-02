package com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal;

import org.openqa.selenium.WebDriver;

public class InputLabel extends InputBase {
	
	public final String label;
	
	private final static String TagLabel = "@TagLabel";
	private final static String XPathLabelWithTag = "//bdi[text()='" + TagLabel + "']/..";
	private final static String XPathInputRelativeLabel = "/following::input";

	public InputLabel(String label, WebDriver driver) {
		super(getXPathLabel(label) + XPathInputRelativeLabel, driver);
		this.label = label;
	}
	
	String getXPathLabel() {
		return getXPathLabel(label);
	}
	
	private static String getXPathLabel(String label) {
		return (XPathLabelWithTag.replace(TagLabel, label));
	}
}
