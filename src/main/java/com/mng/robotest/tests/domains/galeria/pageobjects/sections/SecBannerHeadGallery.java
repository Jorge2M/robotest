package com.mng.robotest.tests.domains.galeria.pageobjects.sections;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.utils.UtilsTest;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;


public class SecBannerHeadGallery extends PageBase {
	
	public enum TypeLinkInfo { MORE, LESS }
	
	private static final String XP_BANNER = "//div[@class='bannerHead' or @class='firstBanner' or @class='innerBanner' or @class='_2mOAS']"; //React
	private static final String XP_BANNER_WITH_VIDEO = XP_BANNER + "//div[@data-video]";
	private static final String XP_BANNER_WITH_BACKGROUND_IMAGE = XP_BANNER + "//div[@style[contains(.,'background-image')]]";
	
	private static final String XP_TEXT = XP_BANNER +	"//*[" + 
			"@class[contains(.,'textinfo')] or " + 
			"@class[contains(.,'vsv-text')] or " + 
			"@class='vsv-content-text' or " +
			"@class[contains(.,'vsv-display')] or " +
			"@class[contains(.,'text-subtitle')]]";
	
	private static final String XP_TEXT_LINK_INFO_REBAJAS = XP_BANNER + "//div[@class[contains(.,'infotext')]]";
	private static final String XP_TEXT_LINK_MORE_INFO_REBAJAS = XP_TEXT_LINK_INFO_REBAJAS + "//self::*[@class[contains(.,'max')]]";
	private static final String XP_TEXT_LINK_LESS_INFO_REBAJAS = XP_TEXT_LINK_INFO_REBAJAS + "//self::*[@class[contains(.,'min')]]";
	private static final String XP_TEXT_INFO_REBAJAS = XP_BANNER + "//div[@class[contains(.,'text3')]]";
	
	private static String getXPathTextInfoRebajas(TypeLinkInfo typeLink) {
		switch (typeLink) {
		case MORE:
			return XP_TEXT_LINK_MORE_INFO_REBAJAS;
		case LESS:
		default:
			return XP_TEXT_LINK_LESS_INFO_REBAJAS;
		}
	}
	
	public boolean isVisible() {
		if (state(Visible, XP_BANNER).check()) {
			Dimension bannerSize = getElement(XP_BANNER).getSize(); 
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
		String xpath = XP_BANNER_WITH_VIDEO + " | " + XP_BANNER_WITH_BACKGROUND_IMAGE;
		return state(Visible, xpath).check();
	}
	
	public boolean isLinkable() {
		if (state(Present, XP_BANNER).check()) {
			WebElement banner = getElement(XP_BANNER);
			return state(Clickable, banner).by(By.xpath(".//a[@href]")).check();
		}
		return false;
	}
	
	public void clickBannerIfClickable() {
		if (isLinkable()) {
			click(XP_BANNER).exec();
		}
	}
	
	public String getText() {
		if (state(Present, XP_TEXT).check()) {
			return getElement(XP_BANNER).getText();
		}
		return "";
	}
	
	public boolean isVisibleLinkInfoRebajas() {
		return state(Visible, XP_TEXT_LINK_INFO_REBAJAS).check();
	}

	public void clickLinkInfoRebajas() {
		click(XP_TEXT_LINK_INFO_REBAJAS).type(javascript).exec();
	}

	public boolean isVisibleLinkTextInfoRebajas(TypeLinkInfo typeLink) {
		String xpathText = getXPathTextInfoRebajas(typeLink);
		return state(Visible, xpathText).check();
	}
	
	public boolean isVisibleInfoRebajasUntil(int seconds) {
		return state(Visible, XP_TEXT_INFO_REBAJAS).wait(seconds).check();
	}
}
