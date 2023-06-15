package com.mng.robotest.domains.transversal.acceptcookies.pageobjects;

import com.mng.robotest.domains.base.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SectionCookies extends PageBase {

	private static final String XPATH_MICROFRONTEND = "//micro-frontend[@id='cookies']";
	private static final String XPATH_SET_COOKIES_BUTTON = XPATH_MICROFRONTEND + "//button[1]";
	private static final String XPATH_ACCEPT_BUTTON = XPATH_MICROFRONTEND + "//button[2]";
	
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
