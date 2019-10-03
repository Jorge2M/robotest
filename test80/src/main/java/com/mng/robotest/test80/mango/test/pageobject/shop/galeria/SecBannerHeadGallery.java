package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.testmaker.webdriverwrapper.TypeOfClick;
import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;


public class SecBannerHeadGallery extends WebdrvWrapp {
	public enum TypeLinkInfo {more, less};
    static String XPathBanner = "//div[@class='bannerHead' or @class='firstBanner' or @class='innerBanner']";
    static String XPathBannerWithVideo = XPathBanner + "//div[@data-video]";
    static String XPathBannerWithBackgroundImage = XPathBanner + "//div[@style[contains(.,'background-image')]]";
    static String XPathText = XPathBanner + "//div[@class[contains(.,'textinfo')] or @class='vsv-text' or @class='vsv-content-text']";
    static String XPathTextLinkInfoRebajas = XPathBanner + "//div[@class[contains(.,'infotext')]]";
    static String XPathTextLinkMoreInfoRebajas = XPathTextLinkInfoRebajas + "//self::*[@class[contains(.,'max')]]";
    static String XPathTextLinkLessInfoRebajas = XPathTextLinkInfoRebajas + "//self::*[@class[contains(.,'min')]]";
    static String XPathTextInfoRebajas = XPathBanner + "//div[@class[contains(.,'text3')]]";
    
    public static String getXPathTextInfoRebajas(TypeLinkInfo typeLink) {
    	switch (typeLink) {
    	case more:
    		return XPathTextLinkMoreInfoRebajas;
    	case less:
    	default:
    		return XPathTextLinkLessInfoRebajas;
    	}
    }
    
    public static boolean isVisible(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathBanner)));
    }
    
    public static boolean isSalesBanner(IdiomaPais idioma, WebDriver driver) {
    	boolean isVisibleBanner = isVisible(driver);
    	if (isVisibleBanner) {
    		String textBanner = getText(driver);
        	String saleTraduction = UtilsTestMango.getSaleTraduction(idioma);
    		if (UtilsTestMango.textContainsPercentage(textBanner, idioma) || textBanner.contains(saleTraduction)) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public static boolean isBannerWithoutTextAccesible(WebDriver driver) {
    	return (isElementVisible(driver, By.xpath(XPathBannerWithVideo + " | " + XPathBannerWithBackgroundImage)));
    }
    
    public static boolean isLinkable(WebDriver driver) {
        if (isElementPresent(driver, By.xpath(XPathBanner))) {
            WebElement banner = driver.findElement(By.xpath(XPathBanner));
            return (isElementClickable(banner, By.xpath(".//a[@href]")));
        }
        
        return false;
    }
    
    public static void clickBannerIfClickable(WebDriver driver) throws Exception {
    	if (isLinkable(driver)) {
    		clickAndWaitLoad(driver, By.xpath(XPathBanner));
    	}
    }
    
    public static String getText(WebDriver driver) {
        if (isElementPresent(driver, By.xpath(XPathText))) {
            return (driver.findElement(By.xpath(XPathBanner)).getText());
        }
        return "";    
    }
    
    public static boolean isVisibleLinkInfoRebajas(WebDriver driver) {
    	return (isElementVisible(driver, By.xpath(XPathTextLinkInfoRebajas)));
    }
    
    public static void clickLinkInfoRebajas(WebDriver driver) throws Exception {
    	clickAndWaitLoad(driver, By.xpath(XPathTextLinkInfoRebajas), TypeOfClick.javascript);
    }
    
    public static boolean isVisibleLinkTextInfoRebajas(TypeLinkInfo typeLink, WebDriver driver) throws Exception {
    	String xpathText = getXPathTextInfoRebajas(typeLink);
    	return (isElementVisible(driver, By.xpath(xpathText))); 
    }
    
    public static boolean isVisibleInfoRebajasUntil(int maxSecondsToWait, WebDriver driver) {
    	return (isElementVisibleUntil(driver, By.xpath(XPathTextInfoRebajas), maxSecondsToWait));
    }
}
