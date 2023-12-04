package com.mng.robotest.tests.domains.ficha.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class ModCompartirNew extends PageBase {

	//El valor es el que figura en el data-ga del ancor
	public enum IconSocial {
		WHATSAPP(false), 
		PINTEREST(false), 
		FACEBOOK(false), 
		TWITTER(false), 
		TELEGRAM(false), 
		MAIL(false), 
		WEIBO(true), 
		WECHAT(true);
		
		private final boolean isSpecificChina;
	 
		IconSocial(boolean isSpecificChina) {
			this.isSpecificChina = isSpecificChina;
		}
		
		public boolean isSpecificChina() {
			return this.isSpecificChina;
		}		
	}
	
	private static final String XP_WRAPPER = "//div[@id='productSocial']";
	private static final String XP_ICON = "//a[@class='icon']";
	
	private String getXPathIcon(IconSocial icon) {
		return (XP_ICON + "//self::*[@data-ga='" + icon.name().toLowerCase() + "']");
	}
	
	public boolean isVisibleUntil(int seconds) {
		moveToElement(XP_WRAPPER);
		return state(VISIBLE, XP_WRAPPER).wait(seconds).check();
	}
	
	public boolean isVisibleIcon(IconSocial icon) {
		String xpathIcon = getXPathIcon(icon);
		return state(VISIBLE, xpathIcon).check();
	}
}
