package com.mng.testmaker.service.webdriver.wrapper;

import com.mng.testmaker.conf.Channel;

public interface ElementPage {

	String getXPath();
	
	default String getXPath(Channel channel) {
		return getXPath();
	}
}
