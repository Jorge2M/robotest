package com.mng.robotest.domains.transversal.acceptcookies.pageobjects;

import com.mng.robotest.domains.base.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalSetCookies extends PageBase {

	private static final String XPATH_MICROFRONTEND = "//micro-frontend[@id='cookies']";
	
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
			return XPATH_MICROFRONTEND + "//*[@role='listitem'][" + position + "]//button";
		}
		public String getXPathHeader() {
			return XPATH_MICROFRONTEND + "//h2";
		}
	}
	
	private static final String XPATH_SAVE_CONF_BUTTON = XPATH_MICROFRONTEND + "/div/div/div/button[1]";
	private static final String XPATH_CHECKBOX_INACTIVE = XPATH_MICROFRONTEND + "//input[@type='checkbox']";
			
	public boolean isVisible(int seconds) {
		return state(Visible, XPATH_SAVE_CONF_BUTTON).wait(seconds).check();
	}
	
	public boolean isInvisible(int seconds) {
		return state(Invisible, XPATH_SAVE_CONF_BUTTON).wait(seconds).check();
	}
	
	public void saveConfiguration() {
		click(XPATH_SAVE_CONF_BUTTON).exec();
	}
	
	public void clickSection(SectionConfCookies section) {
		state(Visible, section.getXPathOption()).wait(1).check();
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
		return getElementVisible(XPATH_CHECKBOX_INACTIVE).isEnabled();
	}
	
	public void clickSwitchCookies() {
		click(getElementVisible(XPATH_CHECKBOX_INACTIVE)).exec();
	}
}
