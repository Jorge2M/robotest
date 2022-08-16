package com.mng.robotest.domains.ficha.pageobjects;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

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
	
	private static final String XPATH_WRAPPER = "//div[@id='productSocial']";
	private static final String XPATH_ICON = "//a[@class='icon']";
	
	private String getXPathIcon(IconSocial icon) {
		return (XPATH_ICON + "//self::*[@data-ga='" + icon + "']");
	}
	
	public boolean isVisibleUntil(int maxSeconds) {
		return state(Visible, XPATH_WRAPPER).wait(maxSeconds).check();
	}
	
	public boolean isVisibleIcon(IconSocial icon) {
		String xpathIcon = getXPathIcon(icon);
		return state(Visible, xpathIcon).check();
	}
}
