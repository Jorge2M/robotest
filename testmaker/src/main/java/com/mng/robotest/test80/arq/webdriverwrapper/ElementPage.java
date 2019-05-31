package com.mng.robotest.test80.arq.webdriverwrapper;

import com.mng.robotest.test80.arq.utils.otras.Channel;

public interface ElementPage {

	String getXPath();
	
	@SuppressWarnings("unused")
	default String getXPath(Channel channel) {
		return getXPath();
	}
}