package com.mng.testmaker.service.webdriver.pageobject;

import org.openqa.selenium.By;

import com.mng.testmaker.conf.Channel;

public interface ElementPage {

	By getBy();
	
	default By getBy(Channel channel) {
		return getBy();
	}
	default By getBy(Enum<?> app) {
		return getBy();
	}
	default By getBy(Channel channel, Enum<?> app) {
		return getBy();
	}
}
