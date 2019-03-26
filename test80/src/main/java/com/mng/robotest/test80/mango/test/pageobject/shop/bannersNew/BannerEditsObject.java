package com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;

public class BannerEditsObject extends BannerObject {

	final static String tagAnalytics = "@data-analytics and not(@data-analytics='')";
	final static String XPathBannerV1 = "//div[@class[contains(.,'vsv-')] and " + tagAnalytics + "]//a";
	final static String XPathBannerV2 = "//div[@class[contains(.,'vsv-')]]//a[" + tagAnalytics + "]";
	final static String XPathBannerV3 = "//div[@class[contains(.,'swiper-slide-active')]]//a";
	final static String XPathBannerV4 = "//div[@class[contains(.,'vsv-openQuickLook')]]";
	final static String XPathBanner = "(" + XPathBannerV1 + " | " + XPathBannerV2 + "|" + XPathBannerV3 + "|" + XPathBannerV4 + ")";
	
	public BannerEditsObject(BannerType bannerType) {
		super(bannerType, XPathBanner);
	}
	
	@Override
    protected String getUrlBanner(WebElement bannerScreen) {
    	return (bannerScreen.getAttribute("href"));
    }
    
	@Override
    protected String getSrcImageBanner(WebElement bannerScreen) {
    	List<WebElement> listImgsBanner = UtilsMangoTest.findDisplayedElements(bannerScreen, By.xpath(".//img"));
    	if (listImgsBanner.size() > 0) {
    		return (listImgsBanner.get(0).getAttribute("src"));
    	}	
    	return "";
    }
}
