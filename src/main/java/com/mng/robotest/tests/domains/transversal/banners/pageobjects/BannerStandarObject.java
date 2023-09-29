package com.mng.robotest.tests.domains.transversal.banners.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;

public class BannerStandarObject extends BannerObject {

	private static final String XPATH_WRAPPER_BANNER = "//div[@class[contains(.,'bannercontainer')] and @data-bannerid]";
	private static final String XPATH_BANNER = XPATH_WRAPPER_BANNER + 
		"//div[@data-cta and not(@data-cta='') and " + 
			  "@data-cta[not(contains(.,'op=ayuda'))] and " + 
			  "not(@class='link')]";
	
	private static final String XPATH_IMAGE_RELATIVE_BANNER = "//img[@class='img-responsive']";
	
	public BannerStandarObject(BannerType bannerType) {
		super(bannerType, XPATH_BANNER);
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
		List<WebElement> listImgsBanner = getElementsVisible(bannerScreen, "." + XPATH_IMAGE_RELATIVE_BANNER);
		if (!listImgsBanner.isEmpty()) {
			return (listImgsBanner.get(0).getAttribute("src"));
		}
		return "";
	}
}
