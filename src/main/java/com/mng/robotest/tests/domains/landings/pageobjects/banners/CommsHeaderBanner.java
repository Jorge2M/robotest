package com.mng.robotest.tests.domains.landings.pageobjects.banners;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;

public class CommsHeaderBanner extends PageBase implements Banner {

	private static final BannerType TYPE = BannerType.COMMS_HEADER;
	
	private static final String XP_HEADER_BANNER = "//div[@class[contains(.,'CommsBanner_commsBanner_')]]";
	private static final String XP_BANNER_LOYALTY = XP_HEADER_BANNER + "//*[@data-testid='commsBanner.mangoLikesYou']"; 
	
	@Override
	public boolean isBanner() {
		return state(VISIBLE, XP_HEADER_BANNER).check();
	}
	
	@Override
	public Optional<List<DataBanner>> getListDataBanners() {
		var listBanners = getElementsVisible(XP_HEADER_BANNER);
		if (listBanners.isEmpty()) {
			return Optional.empty();
		}
		
		List<DataBanner> listBannersFound = new ArrayList<>();
		for (var bannerElem : listBanners) {
			var dataBanner = new DataBanner();
			dataBanner.setBannerType(TYPE);
			dataBanner.setBannerWeb(bannerElem);
			dataBanner.setUrl(getUrl(bannerElem));
			dataBanner.setSrcImage("");
			dataBanner.setText(getText(bannerElem));
			listBannersFound.add(dataBanner);
		}
		return Optional.of(listBannersFound);
	}
	
	private String getUrl(WebElement banner) {
		return getAncor(banner).getAttribute("href");
	}
	
	private WebElement getAncor(WebElement banner) {
		return getElement(banner, ".//a");
	}
	
	private String getText(WebElement banner) {
		var h2Opt = findElement(banner, ".//h2");
		if (h2Opt.isPresent()) {
			return h2Opt.get().getText();
		}
		return banner.getText();
	}
	
	@Override
	public void clickBanner(WebElement banner) {
		getAncor(banner).click();
	}

	public boolean isHeaderBannerMangoLikesYou(int seconds) {
		return state(VISIBLE, XP_BANNER_LOYALTY).wait(seconds).check(); 
	}
	
}

