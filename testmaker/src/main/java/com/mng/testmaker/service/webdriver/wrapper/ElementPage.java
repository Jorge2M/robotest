package com.mng.testmaker.service.webdriver.wrapper;

import com.mng.testmaker.utils.otras.Channel;

public interface ElementPage {

	String getXPath();
	
	default String getXPath(Channel channel) {
		return getXPath();
	}
}
