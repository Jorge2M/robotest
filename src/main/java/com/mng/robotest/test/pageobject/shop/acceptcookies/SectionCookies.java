package com.mng.robotest.test.pageobject.shop.acceptcookies;

import com.mng.robotest.domains.base.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SectionCookies extends PageBase {

	private static final String XPATH_ACCEPT_BUTTON = "//button[@id[contains(.,'accept-btn')]]";
	private static final String XPATH_SET_COOKIES_BUTTON = "//button[@id[contains(.,'pc-btn')]]";
	
	public boolean isVisible(int seconds) {
		return state(Visible, XPATH_ACCEPT_BUTTON).wait(seconds).check();
	}
	
	public boolean isInvisible(int seconds) {
		return state(Invisible, XPATH_ACCEPT_BUTTON).wait(seconds).check();
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
