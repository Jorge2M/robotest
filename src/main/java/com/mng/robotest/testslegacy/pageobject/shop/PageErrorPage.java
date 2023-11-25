package com.mng.robotest.testslegacy.pageobject.shop;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageErrorPage extends PageBase {
	
	private static final String XP_IP_NODE = "//h2[text()[contains(.,'IP :')]]/following-sibling::*[1]";
	
	public String getIpNode() {
		return getElement(XP_IP_NODE).getText();
	}
	
}
