package com.mng.robotest.domains.galeria.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.utils.UtilsTest;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;


public class SecBannerHeadGallery extends PageBase {
	
	public enum TypeLinkInfo { MORE, LESS }
	
	private static final String XPATH_BANNER = "//div[@class='bannerHead' or @class='firstBanner' or @class='innerBanner' or @class='_2mOAS']"; //React
	private static final String XPATH_BANNER_WITH_VIDEO = XPATH_BANNER + "//div[@data-video]";
	private static final String XPATH_BANNER_WITH_BACKGROUND_IMAGE = XPATH_BANNER + "//div[@style[contains(.,'background-image')]]";
	
	private static final String XPATH_TEXT = XPATH_BANNER +	"//*[" + 
			"@class[contains(.,'textinfo')] or " + 
			"@class[contains(.,'vsv-text')] or " + 
			"@class='vsv-content-text' or " +
			"@class[contains(.,'vsv-display')] or " +
			"@class[contains(.,'text-subtitle')]]";
	
	private static final String XPATH_TEXT_LINK_INFO_REBAJAS = XPATH_BANNER + "//div[@class[contains(.,'infotext')]]";
	private static final String XPATH_TEXT_LINK_MORE_INFO_REBAJAS = XPATH_TEXT_LINK_INFO_REBAJAS + "//self::*[@class[contains(.,'max')]]";
	private static final String XPATH_TEXT_LINK_LESS_INFO_REBAJAS = XPATH_TEXT_LINK_INFO_REBAJAS + "//self::*[@class[contains(.,'min')]]";
	private static final String XPATH_TEXT_INFO_REBAJAS = XPATH_BANNER + "//div[@class[contains(.,'text3')]]";
	
	private static String getXPathTextInfoRebajas(TypeLinkInfo typeLink) {
		switch (typeLink) {
		case MORE:
			return XPATH_TEXT_LINK_MORE_INFO_REBAJAS;
		case LESS:
		default:
			return XPATH_TEXT_LINK_LESS_INFO_REBAJAS;
		}
	}
	
	public boolean isVisible() {
		if (state(Visible, XPATH_BANNER).check()) {
			Dimension bannerSize = getElement(XPATH_BANNER).getSize(); 
			if (bannerSize.height>0 && bannerSize.width>0) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isSalesBanner(IdiomaPais idioma) {
		boolean isVisibleBanner = isVisible();
		if (isVisibleBanner) {
			String textBanner = getText();
			String saleTraduction = UtilsTest.getSaleTraduction(idioma);
			if (UtilsTest.textContainsPercentage(textBanner, idioma) || textBanner.contains(saleTraduction)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isBannerWithoutTextAccesible() {
		String xpath = XPATH_BANNER_WITH_VIDEO + " | " + XPATH_BANNER_WITH_BACKGROUND_IMAGE;
		return state(Visible, xpath).check();
	}
	
	public boolean isLinkable() {
		if (state(Present, XPATH_BANNER).check()) {
			WebElement banner = getElement(XPATH_BANNER);
			return state(Clickable, banner).by(By.xpath(".//a[@href]")).check();
		}
		return false;
	}
	
	public void clickBannerIfClickable() {
		if (isLinkable()) {
			click(XPATH_BANNER).exec();
		}
	}
	
	public String getText() {
		if (state(Present, XPATH_TEXT).check()) {
			return getElement(XPATH_BANNER).getText();
		}
		return "";
	}
	
	public boolean isVisibleLinkInfoRebajas() {
		return state(Visible, XPATH_TEXT_LINK_INFO_REBAJAS).check();
	}

	public void clickLinkInfoRebajas() {
		click(XPATH_TEXT_LINK_INFO_REBAJAS).type(javascript).exec();
	}

	public boolean isVisibleLinkTextInfoRebajas(TypeLinkInfo typeLink) {
		String xpathText = getXPathTextInfoRebajas(typeLink);
		return state(Visible, xpathText).check();
	}
	
	public boolean isVisibleInfoRebajasUntil(int seconds) {
		return state(Visible, XPATH_TEXT_INFO_REBAJAS).wait(seconds).check();
	}
}
