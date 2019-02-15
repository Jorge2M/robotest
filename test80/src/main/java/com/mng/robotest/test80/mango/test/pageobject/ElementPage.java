package com.mng.robotest.test80.mango.test.pageobject;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;

public interface ElementPage {

	String getXPath();
	
	@SuppressWarnings("unused")
	default String getXPath(Channel channel) {
		return getXPath();
	}
}
