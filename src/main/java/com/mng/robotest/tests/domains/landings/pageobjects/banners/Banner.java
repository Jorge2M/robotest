package com.mng.robotest.tests.domains.landings.pageobjects.banners;

import static com.mng.robotest.tests.domains.landings.pageobjects.banners.Banner.BannerType.*;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.WebElement;

public interface Banner {

	public enum BannerType { COMMS_HEADER, HERO, FAMILY }
	
	public boolean isBanner();
	public Optional<List<DataBanner>> getListDataBanners();
	public void clickBanner(WebElement banner);

	public static void click(DataBanner dataBanner) {
		var banner = make(dataBanner.bannerType);
		banner.clickBanner(dataBanner.getBannerWeb());
	}
	
	public static Banner make(BannerType bannerType) {
		if (bannerType==HERO) {
			return new HeroBanner();
		}
		if (bannerType==FAMILY) {
			return new FamilyBanner();
		}
		return new CommsHeaderBanner();
	}
	
}
