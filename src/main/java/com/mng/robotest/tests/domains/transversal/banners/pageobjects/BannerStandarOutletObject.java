package com.mng.robotest.tests.domains.transversal.banners.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;

public class BannerStandarOutletObject extends BannerObject {

	private static final String XP_BANNER = "//div[@class[contains(.,'bannerWrapper')]]//a";
	private static final String XP_IMAGE_BANNER = "//img[@class[contains(.,'Promo_image')]]"; 
	
	public BannerStandarOutletObject(BannerType bannerType) {
		super(bannerType, XP_BANNER);
	}
	
	@Override
	protected String getUrlBanner(WebElement bannerScreen) {
		String urlBanner = bannerScreen.getAttribute("data-cta");
		if (urlBanner==null || "".compareTo(urlBanner)==0) {
			urlBanner = getUrlDestinoSearchingForAnchor(bannerScreen);
		}
		return urlBanner;
	}

	@Override
	protected String getSrcImageBanner(WebElement bannerScreen) {
		List<WebElement> listImgsBanner = getElementsVisible(bannerScreen, "." + XP_IMAGE_BANNER);
		if (!listImgsBanner.isEmpty()) {
			return (listImgsBanner.get(0).getAttribute("src"));
		}
		return "";
	}
}
