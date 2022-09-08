package com.mng.robotest.test.pageobject.shop.acceptcookies;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;

public class SectionCookies extends PageBase {

	private static final String XPATH_ACCEPT_BUTTON = "//button[@id[contains(.,'accept-btn')]]";
	private static final String XPATH_SET_COOKIES_BUTTON = "//button[@id[contains(.,'pc-btn')]]";
	
	public boolean isVisible(int maxSeconds) {
		return state(State.Visible, XPATH_ACCEPT_BUTTON).wait(maxSeconds).check();
	}
	
	public boolean isInvisible(int maxSeconds) {
		return state(State.Invisible, XPATH_ACCEPT_BUTTON).wait(maxSeconds).check();
	}
	
	public void accept() {
		click(XPATH_ACCEPT_BUTTON).exec();
		if (!isInvisible(2)) {
			click(XPATH_ACCEPT_BUTTON).exec();
		}
	}
	
	public void setCookies() {
		click(XPATH_SET_COOKIES_BUTTON).exec();
	}
}
