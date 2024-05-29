package com.mng.robotest.tests.domains.setcookies.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;

public abstract class ModalSetCookies extends PageBase {

	public static ModalSetCookies make(Channel channel, AppEcom app) {
		//TODO eliminar el código antiguo en un futuro
//		if (app==AppEcom.shop) {
//			return new ModalSetCookiesOld();
//		}
		return new ModalSetCookiesGenesis();
	}
	
	public abstract void enable(CookiesType section);
	public abstract void disable(CookiesType section);
	public abstract boolean isEnabled(CookiesType section);
	
	private static final String XP_SAVE_CONF_BUTTON = "//*[@data-testid='cookies.settings.button.save']";	
	
	public enum CookiesType {
		TU_PRIVACIDAD("Tu privacidad"),
		COOKIES_ESTRICTAMENTE_NECESARIAS("Cookies estrictamente necesarias"),
		COOKIES_DE_PREFERENCIA_O_PERSONALIZACION("Cookies de preferencia o de personalización"),
		COOKIES_DE_ANALISIS("Cookies de análisis"),
		COOKIES_PUBLICITARIAS_O_COMPORTAMENTALES("Cookies publicitarias comportamentales"),
		COOKIES_DE_REDES_SOCIALES("Cookies de redes sociales");
		
		private String literal;
		private CookiesType(String literal) {
			this.literal = literal;
		}
		
		public String getLiteral() {
			return literal;
		}
	}

	String getXPathSection(CookiesType section) {
		return "//*[text()='" + section.getLiteral() + "']/ancestor::button[1]";
	}
	
	public boolean isVisible(int seconds) {
		return state(VISIBLE, XP_SAVE_CONF_BUTTON).wait(seconds).check();
	}
	
	public boolean isInvisible(int seconds) {
		return state(INVISIBLE, XP_SAVE_CONF_BUTTON).wait(seconds).check();
	}
	
	void clickSection(CookiesType section) {
		String xpathSection = getXPathSection(section);
		state(VISIBLE, xpathSection).wait(1).check();
		click(xpathSection).exec();
	}
	
	public void saveConfiguration() {
		click(XP_SAVE_CONF_BUTTON).exec();
	}
	
}
