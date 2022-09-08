package com.mng.robotest.test.pageobject.shop.acceptcookies;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;

public class ModalSetCookies extends PageBase {
	
	public enum SectionConfCookies {
		TU_PRIVACIDAD("ot-pvcy-txt"),
		COOKIES_ESTRICTAMENTE_NECESARIAS("ot-header-id-C0001"),
		COOKIES_FUNCIONOALES("ot-header-id-C0003"),
		COOKIES_DE_RENDIMIENTO("ot-header-id-C0002"),
		COOKIES_DIRIGIDAS("ot-header-id-C0004"),
		COOKIES_DE_REDES_SOCIALES("ot-header-id-C0005");
		
		private String id;
		private SectionConfCookies(String id) {
			this.id = id;
		}
		public String getId() {
			return id;
		}
		public String getNombre() {
			return this.name().replace("_", " ");
		}
	}
	
	private static final String XPATH_SAVE_CONF_BUTTON = "//button[@class[contains(.,'save-preference')]]";
	private static final String XPATH_WRAPPER_SWITCH = "//div[@class='ot-tgl']";
	private static final String XPATH_INPUT_SWITCH_HANDLER = ".//input[@class='category-switch-handler']";
	private static final String XPATH_SWITCH_COOKIES = "//span[@class='ot-switch-nob']";
	
	private String getXPathMenuSection(SectionConfCookies section) {
		return "//*[@id='" + section.getId() + "']/..";
	}
	
	public boolean isVisible(int maxSeconds) {
		return state(State.Visible, XPATH_SAVE_CONF_BUTTON).wait(maxSeconds).check();
	}
	
	public boolean isInvisible(int maxSeconds) {
		return state(State.Invisible, XPATH_SAVE_CONF_BUTTON).wait(maxSeconds).check();
	}
	
	public void saveConfiguration() {
		click(XPATH_SAVE_CONF_BUTTON).exec();
	}
	
	public void clickSection(SectionConfCookies section) {
		click(By.id(section.getId())).exec();
	}
	
	public boolean isSectionUnfold(SectionConfCookies section) {
		String xpathMenu = getXPathMenuSection(section);
		WebElement menuSection = getElementWeb(xpathMenu);
		return (
			menuSection!=null && 
			menuSection.getAttribute("aria-selected").compareTo("true")==0);
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
		WebElement wrapperSwitch = getElementVisible(XPATH_WRAPPER_SWITCH);
		return state(State.Present, wrapperSwitch).by(By.xpath(XPATH_INPUT_SWITCH_HANDLER + "//self::*[@checked]")).check();
	}
	
	public void clickSwitchCookies() {
		WebElement switchElem = getElementVisible(XPATH_SWITCH_COOKIES);
		click(switchElem).exec();
	}
}
