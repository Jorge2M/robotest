package com.mng.robotest.tests.domains.transversal.banners.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;

public class BannerEditsObject extends BannerObject {

	private static final String XPATH_BANNER_CARRUSEL = "//div[@class='heroSwiper']//div[@class='swiperImage']";
	private static final String XPATH_BANNER_PESTANYAS = "//div[@class='masonryGrid']//div[@class='masonryItem']/a";
	private static final String XPATH_BANNER = "(" + XPATH_BANNER_CARRUSEL + " | " + XPATH_BANNER_PESTANYAS + ")";
	
	public BannerEditsObject(BannerType bannerType) {
		super(bannerType, XPATH_BANNER);
	}
	
	@Override
	protected String getUrlBanner(WebElement bannerScreen) {
		return (bannerScreen.getAttribute("href"));
	}
	
	@Override
	protected String getSrcImageBanner(WebElement bannerScreen) {
		List<WebElement> listImgsBanner = getElementsVisible(bannerScreen, ".//img");
		if (!listImgsBanner.isEmpty()) {
			return (listImgsBanner.get(0).getAttribute("src"));
		}	
		return "";
	}
}
