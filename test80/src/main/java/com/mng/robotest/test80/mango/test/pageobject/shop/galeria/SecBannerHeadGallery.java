package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;


public class SecBannerHeadGallery {
	public enum TypeLinkInfo {more, less};
    static String XPathBanner = "//div[@class='bannerHead' or @class='firstBanner' or @class='innerBanner' or @class='_2mOAS']"; //React
    static String XPathBannerWithVideo = XPathBanner + "//div[@data-video]";
    static String XPathBannerWithBackgroundImage = XPathBanner + "//div[@style[contains(.,'background-image')]]";
    static String XPathText = XPathBanner + "//*[@class[contains(.,'textinfo')] or @class[contains(.,'vsv-text')] or @class='vsv-content-text' or @class[contains(.,'text-subtitle')]]";
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
    	if (state(Visible, By.xpath(XPathBanner), driver).check()) {
    		Dimension bannerSize = driver.findElement(By.xpath(XPathBanner)).getSize(); 
    		if (bannerSize.height>0 && bannerSize.width>0) {
    			return true;
    		}
    	}
    	return false;
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
    	String xpath = XPathBannerWithVideo + " | " + XPathBannerWithBackgroundImage;
    	return (state(Visible, By.xpath(xpath), driver).check());
    }
    
    public static boolean isLinkable(WebDriver driver) {
    	if (state(Present, By.xpath(XPathBanner), driver).check()) {
            WebElement banner = driver.findElement(By.xpath(XPathBanner));
            return (state(Clickable, banner, driver)
            		.by(By.xpath(".//a[@href]")).check());
        }
        
        return false;
    }
    
    public static void clickBannerIfClickable(WebDriver driver) {
    	if (isLinkable(driver)) {
    		click(By.xpath(XPathBanner), driver).exec();
    	}
    }
    
    public static String getText(WebDriver driver) {
    	if (state(Present, By.xpath(XPathText), driver).check()) {
            return (driver.findElement(By.xpath(XPathBanner)).getText());
        }
        return "";
    }
    
    public static boolean isVisibleLinkInfoRebajas(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathTextLinkInfoRebajas), driver).check());
    }

	public static void clickLinkInfoRebajas(WebDriver driver) {
		click(By.xpath(XPathTextLinkInfoRebajas), driver).type(javascript).exec();
	}

    public static boolean isVisibleLinkTextInfoRebajas(TypeLinkInfo typeLink, WebDriver driver) {
    	String xpathText = getXPathTextInfoRebajas(typeLink);
    	return (state(Visible, By.xpath(xpathText), driver).check());
    }
    
    public static boolean isVisibleInfoRebajasUntil(int maxSeconds, WebDriver driver) {
    	return (state(Visible, By.xpath(XPathTextInfoRebajas), driver)
    			.wait(maxSeconds).check());
    }
}
