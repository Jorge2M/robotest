package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class SecBannerHead extends WebdrvWrapp {
	public enum TypeLinkInfo {more, less};
    static String XPathBanner = "//div[@class='bannerHead' or @class='firstBanner']";
    static String XPathBannerWithVideo = XPathBanner + "//div[@data-video]";
    static String XPathBannerWithBackgroundImage = XPathBanner + "//div[@style[contains(.,'background-image')]]";
    static String XPathText = XPathBanner + "//div[@class='textinfo']";
    static String XPathTextLinkInfoRebajas = XPathBanner + "//div[@class[contains(.,'infotext')]]";
    static String XPathTextLinkMoreInfoRebajas = XPathTextLinkInfoRebajas + "//self::*[@class[contains(.,'max')]]";
    static String XPathTextLinkLessInfoRebajas = XPathTextLinkInfoRebajas + "//self::*[@class[contains(.,'min')]]";
    static String XPathTextInfoRebajas = XPathBanner + "//div[@class='text2']";
    
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
    	if (isLinkable(driver))
    		clickAndWaitLoad(driver, By.xpath(XPathBanner));
    }
    
    public static String getText(WebDriver driver) {
        if (isElementPresent(driver, By.xpath(XPathText)))
            return (driver.findElement(By.xpath(XPathBanner)).getText());
            
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
