package com.mng.testmaker.service.webdriver.pageobject;

import com.mng.testmaker.conf.Channel;

public interface ElementPage {

	String getXPath();
	
	default String getXPath(Channel channel) {
		return getXPath();
	}
}
