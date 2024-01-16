package com.mng.robotest.tests.domains.transversal.acceptcookies.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class SectionCookies extends PageBase {

	private static final String XP_WRAPPER_NEW = "//*[@id='cookies']";
	private static final String XP_SET_COOKIES_BUTTON = XP_WRAPPER_NEW + "//*[@data-testid='cookies.button.settings']";
	private static final String XP_ACCEPT_BUTTON = XP_WRAPPER_NEW + "//*[@data-testid='cookies.button.acceptAll']";
	
	public boolean isVisible(int seconds) {
		return state(VISIBLE, XP_ACCEPT_BUTTON).wait(seconds).check();
	}
	
	public boolean isInvisible(int seconds) {
		return state(INVISIBLE, XP_ACCEPT_BUTTON).wait(seconds).check();
	}
	
	public void accept() {
		click(XP_ACCEPT_BUTTON).exec();
		if (!isInvisible(2)) {
			click(XP_ACCEPT_BUTTON).exec();
		}
	}
	
	public void setCookies() {
		state(PRESENT, XP_SET_COOKIES_BUTTON).wait(1).check();
		click(XP_SET_COOKIES_BUTTON).exec();
	}
	
}
