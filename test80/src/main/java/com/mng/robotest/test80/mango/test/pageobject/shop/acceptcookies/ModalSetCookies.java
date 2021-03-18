package com.mng.robotest.test80.mango.test.pageobject.shop.acceptcookies;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class ModalSetCookies extends PageObjTM {
	
	public enum SectionConfCookies {
		Tu_privacidad("ot-pvcy-txt"),
		Cookies_estrictamente_necesarias("ot-header-id-C0001"),
		Cookies_funcionales("ot-header-id-C0003"),
		Cookies_de_rendimiento("ot-header-id-C0002"),
		Cookies_dirigidas("ot-header-id-C0004"),
		Cookies_de_redes_sociales("ot-header-id-C0005");
		
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
	
	private final String XPathSaveConfButton = "//button[@class[contains(.,'save-preference')]]";
	private final String XPathWrapperSwitch = "//div[@class='ot-tgl']";
	private final String XPathInputSwitchHandler = ".//input[@class='category-switch-handler']";
	private final String XPathSwitchCookies = "//span[@class='ot-switch-nob']";
	
	private String getXPathMenuSection(SectionConfCookies section) {
		return ("//*[@id='" + section.getId() + "']/..");
	}
	
	public ModalSetCookies(WebDriver driver) {
		super(driver);
	}
	
	public boolean isVisible(int maxSeconds) {
		By bySave = By.xpath(XPathSaveConfButton);
		return state(State.Visible, bySave).wait(maxSeconds).check();
	}
	
	public boolean isInvisible(int maxSeconds) {
		By bySave = By.xpath(XPathSaveConfButton);
		return state(State.Invisible, bySave).wait(maxSeconds).check();
	}
	
	public void saveConfiguration() {
		click(By.xpath(XPathSaveConfButton)).exec();
	}
	
	public void clickSection(SectionConfCookies section) {
		click(By.id(section.getId())).exec();
	}
	
	public boolean isSectionUnfold(SectionConfCookies section) {
		String xpathMenu = getXPathMenuSection(section);
		WebElement menuSection = PageObjTM.getElementWeb(By.xpath(xpathMenu), driver);
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
		WebElement wrapperSwitch = PageObjTM.getElementVisible(driver, By.xpath(XPathWrapperSwitch));
		return state(State.Present, wrapperSwitch).by(By.xpath(XPathInputSwitchHandler + "//self::*[@checked]")).check();
	}
	
	public void clickSwitchCookies() {
		WebElement switchElem = PageObjTM.getElementVisible(driver, By.xpath(XPathSwitchCookies));
		click(switchElem).exec();
	}
}
