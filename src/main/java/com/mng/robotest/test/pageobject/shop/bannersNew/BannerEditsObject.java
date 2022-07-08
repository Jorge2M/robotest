package com.mng.robotest.test.pageobject.shop.bannersNew;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test.generic.UtilsMangoTest;

public class BannerEditsObject extends BannerObject {

//	static final String tagAnalytics = "@data-analytics and not(@data-analytics='')";
//	static final String XPathBannerV1 = "//div[@class[contains(.,'vsv-')] and " + tagAnalytics + "]//a";
//	static final String XPathBannerV2 = "//div[@class[contains(.,'vsv-')]]//a[" + tagAnalytics + "]";
//	static final String XPathBannerV3 = "//div[@class[contains(.,'swiper-slide-active')]]//a";
//	static final String XPathBannerV4 = "//div[@class[contains(.,'vsv-openQuickLook')]]";
//	static final String XPathBanner = "(" + XPathBannerV1 + " | " + XPathBannerV2 + "|" + XPathBannerV3 + "|" + XPathBannerV4 + ")";
	
	private static final String XPathBannerCarrusel = "//div[@class='heroSwiper']//div[@class='swiperImage']";
	private static final String XPathBannerPestanyas = "//div[@class='masonryGrid']//div[@class='masonryItem']/a";
	private static final String XPathBanner = "(" + XPathBannerCarrusel + " | " + XPathBannerPestanyas + ")";
	
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
		if (!listImgsBanner.isEmpty()) {
			return (listImgsBanner.get(0).getAttribute("src"));
		}	
		return "";
	}
}
