package com.mng.robotest.tests.domains.landings.pageobjects.banners;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;

public class HeroBanner extends PageBase implements Banner {

	private static final BannerType TYPE = BannerType.HERO;
	
	private static final String XP_BANNER = "//div["
			+ "@class[contains(.,'HeroBannerFullHeight')] or "
			+ "@class[contains(.,'HeroBannerPromo_bannerWrapper')] or "
			+ "@class[contains(.,'HeroBannerCtas_bannerWrapper')]"
			+ "]";
	
	@Override
	public boolean isBanner() {
		return state(VISIBLE, XP_BANNER).check();
	}
	
	@Override
	public Optional<List<DataBanner>> getListDataBanners() {
		var listBanners = getElementsVisible(XP_BANNER);
		if (listBanners.isEmpty()) {
			return Optional.empty();
		}

		waitMillis(500); //The texts are loaded a little after
		List<DataBanner> listBannersFound = new ArrayList<>();
		for (var bannerElem : listBanners) {
			var dataBanner = new DataBanner();
			dataBanner.setBannerType(TYPE);
			dataBanner.setBannerWeb(bannerElem);
			dataBanner.setUrl(getUrlBanner(bannerElem));
			dataBanner.setSrcImage(getImageBanner(bannerElem));
			dataBanner.setText(bannerElem.getText());
			listBannersFound.add(dataBanner);
		}
		return Optional.of(listBannersFound);
	}

	@Override
	public void clickBanner(WebElement banner) {
		getAncor(banner).click();
	}
	
	private String getUrlBanner(WebElement banner) {
		return getAncor(banner).getAttribute("href");
	}
	
	private WebElement getAncor(WebElement banner) {
		var ancorType1Opt = findElement(banner, "./../self::a");
		if (ancorType1Opt.isPresent()) {
			return ancorType1Opt.get();
		}
		return getElement(banner, ".//a");
	}
	
	private String getImageBanner(WebElement banner) {
		var imgBannerOpt = findElement(banner, ".//img[@class[contains(.,'HeroBanner')]]");
		if (imgBannerOpt.isEmpty()) {
			return "";
		}
		var srcSet = imgBannerOpt.get().getAttribute("srcset");
		if (srcSet==null) {
			return "";
		}
		return UtilsBanners.getFirstImageFromSrcset(srcSet);
	}
	
}

