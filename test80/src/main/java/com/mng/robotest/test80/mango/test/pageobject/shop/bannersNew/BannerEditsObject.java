package com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;

public class BannerEditsObject extends BannerObject {

	final static String XPathWrapperBanner = "//div[@class[contains(.,'vsv-')]]";
	final static String XPathBanner = XPathWrapperBanner + "//a[@data-analytics and not(@data-analytics='')]";
	final static String XPathImageRelativeBanner = "//img";
	
	public BannerEditsObject(BannerType bannerType) {
		super(bannerType, XPathBanner);
	}
	
	@Override
    protected String getUrlBanner(WebElement bannerScreen) {
    	return (bannerScreen.getAttribute("href"));
    }
    
	@Override
    protected String getSrcImageBanner(WebElement bannerScreen) {
    	List<WebElement> listImgsBanner = UtilsMangoTest.findDisplayedElements(bannerScreen, By.xpath("." + XPathImageRelativeBanner));
    	if (listImgsBanner.size() > 0)
    		return (listImgsBanner.get(0).getAttribute("src"));
    		
    	return "";
    }
}
