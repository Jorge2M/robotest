package com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;

public class BannerStandarObject extends BannerObject {

	final static String XPathWrapperBanner = "//div[@class[contains(.,'vsv-box')] and @data-id]";
	final static String XPathBanner = 
						    XPathWrapperBanner + 
						    "//div[@data-cta and not(@data-cta='') and " + 
						    	  "@data-cta[not(contains(.,'op=ayuda'))] and " + 
						    	  "not(@class='link')]";
	final static String XPathImageRelativeBanner = "//img[@class='img-responsive']";
	final static String XPathMainTextRelativeBanner = "//div[@class[contains(.,'mainText')]]";
	
	public BannerStandarObject(BannerType bannerType) {
		super(bannerType, XPathBanner);
	}
	
	@Override
    protected String getUrlBanner(WebElement bannerScreen) {
        String urlBanner = bannerScreen.getAttribute("data-cta");
        if (urlBanner==null || "".compareTo(urlBanner)==0)
        	urlBanner = getUrlDestinoSearchingForAnchor(bannerScreen);
        
        return urlBanner;
    }
    
	@Override
    protected String getSrcImageBanner(WebElement bannerScreen) {
    	List<WebElement> listImgsBanner = UtilsMangoTest.findDisplayedElements(bannerScreen, By.xpath("." + XPathImageRelativeBanner));
    	if (listImgsBanner.size() > 0)
    		return (listImgsBanner.get(0).getAttribute("src"));
    		
    	return "";
    }
}
