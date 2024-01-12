package com.mng.robotest.tests.domains.transversal.acceptcookies.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class SectionCookies extends PageBase {

	private static final String XP_WRAPPER = "//micro-frontend[@id='cookies']";
	private static final String XP_SET_COOKIES_BUTTON = XP_WRAPPER + "//button/span/../../button[1]";
	private static final String XP_ACCEPT_BUTTON = XP_WRAPPER + "//button/span/../../button[2]";
	
	//Currently (14-11-23) only associated with the new menus in Genesis (https://www.pre.mangooutlet.com/es/es)
	//TODO si con Genesis podemos aprovechar los class habrá que cambiar esto
	private static final String XP_WRAPPER_NEW = "//*[@data-testid='cookies.layout']";
	private static final String XP_SET_COOKIES_BUTTON_NEW = XP_WRAPPER_NEW + "//button[@class[contains(.,'Button_secondary')]]";
	private static final String XP_ACCEPT_BUTTON_NEW = XP_WRAPPER_NEW + "//button[@class[contains(.,'CookiesContent_button')]][2]";
	
	private String getXPathSetCookiesButton() {
		return "(" + XP_SET_COOKIES_BUTTON + " | " + XP_SET_COOKIES_BUTTON_NEW + ")";
	}
	private String getXPathAcceptButton() {
		return "(" + XP_ACCEPT_BUTTON + " | " + XP_ACCEPT_BUTTON_NEW + ")";
	}
	
	public boolean isVisible(int seconds) {
		return state(VISIBLE, getXPathAcceptButton()).wait(seconds).check();
	}
	
	public boolean isInvisible(int seconds) {
		return state(INVISIBLE, getXPathAcceptButton()).wait(seconds).check();
	}
	
	public void accept() {
		click(getXPathAcceptButton()).exec();
		if (!isInvisible(2)) {
			click(getXPathAcceptButton()).exec();
		}
	}
	
	public void setCookies() {
		state(PRESENT, getXPathSetCookiesButton()).wait(1).check();
		click(getXPathSetCookiesButton()).exec();
	}
	
}
