package com.mng.robotest.domains.ficha.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

/**
 * Modal para compartir el enlace que aparece al seleccionar el link "Compartir" de la nueva Ficha
 * @author jorge.munoz
 *
 */

public class ModCompartirNew {

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
	
	public static String getXPathIcon(IconSocial icon) {
		return (XPATH_ICON + "//self::*[@data-ga='" + icon + "']");
	}
	
	public static boolean isVisibleUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_WRAPPER), driver)
				.wait(maxSeconds).check());
	}
	
	public static boolean isVisibleIcon(IconSocial icon, WebDriver driver) {
		String xpathIcon = getXPathIcon(icon);
		return (state(Visible, By.xpath(xpathIcon), driver).check());
	}
}
