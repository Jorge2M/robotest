package com.mng.robotest.test80.mango.test.pageobject;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;

public interface ElementPage {

	String getXPath();
	default String getXPath(Channel channel) {
		return getXPath();
	}
}
