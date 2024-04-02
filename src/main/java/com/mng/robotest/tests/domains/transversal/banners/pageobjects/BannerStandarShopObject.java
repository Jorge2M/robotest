package com.mng.robotest.tests.domains.transversal.banners.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;

public class BannerStandarShopObject extends BannerObject {

	private static final String XP_WRAPPER_BANNER = "//div[@class[contains(.,'bannercontainer')] and @data-bannerid]";
	private static final String XP_BANNER = XP_WRAPPER_BANNER + 
		"//div[@data-cta and not(@data-cta='') and " + 
			  "@data-cta[not(contains(.,'op=ayuda'))] and " + 
			  "not(@class='link')]";
	
	private static final String XP_IMAGE_RELATIVE_BANNER = "//img[@class='img-responsive']";
	
	public BannerStandarShopObject(BannerType bannerType) {
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
		List<WebElement> listImgsBanner = getElementsVisible(bannerScreen, "." + XP_IMAGE_RELATIVE_BANNER);
		if (!listImgsBanner.isEmpty()) {
			return (listImgsBanner.get(0).getAttribute("src"));
		}
		return "";
	}
}
