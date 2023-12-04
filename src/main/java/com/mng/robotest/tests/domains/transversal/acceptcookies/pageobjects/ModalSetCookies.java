package com.mng.robotest.tests.domains.transversal.acceptcookies.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class ModalSetCookies extends PageBase {

	private static final String XP_MICROFRONTEND = "//micro-frontend[@id='cookies']";
	
	public enum SectionConfCookies {
		TU_PRIVACIDAD(1),
		COOKIES_ESTRICTAMENTE_NECESARIAS(2),
		COOKIES_FUNCIONOALES(3),
		COOKIES_DE_RENDIMIENTO(4),
		COOKIES_DIRIGIDAS(5),
		COOKIES_DE_REDES_SOCIALES(6);
		
		private int position;
		private SectionConfCookies(int position) {
			this.position = position;
		}
		public String getXPathOption() {
			return XP_MICROFRONTEND + "//*[@role='listitem'][" + position + "]//button";
		}
		public String getXPathHeader() {
			return XP_MICROFRONTEND + "//h2";
		}
	}
	
	private static final String XP_SAVE_CONF_BUTTON = XP_MICROFRONTEND + "/div/div/div/button[1]";
	private static final String XP_CHECKBOX_INACTIVE = XP_MICROFRONTEND + "//input[@type='checkbox']";
			
	public boolean isVisible(int seconds) {
		return state(VISIBLE, XP_SAVE_CONF_BUTTON).wait(seconds).check();
	}
	
	public boolean isInvisible(int seconds) {
		return state(INVISIBLE, XP_SAVE_CONF_BUTTON).wait(seconds).check();
	}
	
	public void saveConfiguration() {
		click(XP_SAVE_CONF_BUTTON).exec();
	}
	
	public void clickSection(SectionConfCookies section) {
		state(VISIBLE, section.getXPathOption()).wait(1).check();
		click(section.getXPathOption()).exec();
	}
	
	public boolean isSectionUnfold(SectionConfCookies section) {
		var optionSection = getElementWeb(section.getXPathOption());
		if (optionSection==null) {
			return false;
		}
		var headerSection = getElementWeb(section.getXPathHeader());
		if (headerSection==null) {
			return false;
		}
		return optionSection.getText().compareTo(headerSection.getText())==0;
	}
	
	public void enableSwitchCookies() {
		if (!isSwitchEnabled()) {
			clickSwitchCookies();
		}
	}
	
	public void disableSwitchCookies() {
		if (isSwitchEnabled()) {
			clickSwitchCookies();
		}
	}
	
	public boolean isSwitchEnabled() {
		return getElementVisible(XP_CHECKBOX_INACTIVE).isEnabled();
	}
	
	public void clickSwitchCookies() {
		click(getElementVisible(XP_CHECKBOX_INACTIVE)).exec();
	}
}
