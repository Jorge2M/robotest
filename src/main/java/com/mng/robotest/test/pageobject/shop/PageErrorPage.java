package com.mng.robotest.test.pageobject.shop;

import com.mng.robotest.domains.base.PageBase;

public class PageErrorPage extends PageBase {
	
	private static final String XPATH_IP_NODE = "//h2[text()[contains(.,'IP :')]]/following-sibling::*[1]";
	
	public String getIpNode() {
		return getElement(XPATH_IP_NODE).getText();
	}
	
}
