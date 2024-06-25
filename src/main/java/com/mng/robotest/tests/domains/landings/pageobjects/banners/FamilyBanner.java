package com.mng.robotest.tests.domains.landings.pageobjects.banners;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;

public class FamilyBanner extends PageBase implements Banner {

	private static final BannerType TYPE = BannerType.FAMILY;
	
	private static final String XP_BANNER = "//div["
			+ "@class[contains(.,'FamilyBannerShop')] or "
			+ "@class[contains(.,'FamilyBanner_imageContainer')]]"
			+ "//ancestor::a";
	
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
		
		List<DataBanner> listBannersFound = new ArrayList<>();
		for (var bannerElem : listBanners) {
			var dataBanner = new DataBanner();
			dataBanner.setBannerType(TYPE);
			dataBanner.setBannerWeb(bannerElem);
			dataBanner.setUrl(getUrlBanner(bannerElem));
			dataBanner.setSrcImage(getImageBanner(bannerElem));
			dataBanner.setText(getText(bannerElem));
			listBannersFound.add(dataBanner);
		}
		return Optional.of(listBannersFound);
	}
	
	@Override
	public void clickBanner(WebElement banner) {
		banner.click();
	}	
	
	private String getText(WebElement banner) {
		var textElemOpt = findElement(banner, ".//h2");
		if (textElemOpt.isEmpty()) {
			return "";
		}
		var textElem = textElemOpt.get();
		if ("".compareTo(textElem.getText())!=0) {
			return textElem.getText();
		}
		return textElem.getAttribute("innerHTML");
	}
	
	private String getUrlBanner(WebElement banner) {
		return banner.getAttribute("href");
	}
	
	private String getImageBanner(WebElement banner) {
		var imgBannerOpt = findElement(banner, ".//img");
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

