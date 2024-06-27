package com.mng.robotest.tests.domains.landings.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.landings.pageobjects.banners.CommsHeaderBanner;
import com.mng.robotest.tests.domains.landings.pageobjects.banners.DataBanner;
import com.mng.robotest.tests.domains.landings.pageobjects.banners.DataBanners;
import com.mng.robotest.tests.domains.landings.pageobjects.banners.Banner;
import com.mng.robotest.tests.domains.landings.pageobjects.banners.Banner.BannerType;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.ArrayList;
import java.util.List;

public class PageLanding extends PageBase {

	private DataBanners dataBanners = loadDataBanners();
	
	private static final String XP_LANDING_MULTIMARCA = "//*[@data-testid='landings.home.multibrand']";
	private static final String XP_LANDING_LINEA = "//*[@data-testid='landings.home.brand']";
	private static final String XP_LOYALTY_ELEMENT = "//*[@data-testid[contains(.,'Likes')] or @id[contains(.,'Likes')]]";
	
	public boolean isPage(int seconds) {
		for (int i=0; i<=seconds; i++) {
			if (isLandingLinea(0) || isLandingMultimarca()) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	
	public boolean isLandingMultimarca() {
		return isLandingMultimarca(0);
	}
	
	public boolean isLandingMultimarca(int seconds) {
		return state(PRESENT, XP_LANDING_MULTIMARCA).wait(seconds).check();
	}
	
	public boolean isLandingLinea(int seconds) {
		return state(PRESENT, XP_LANDING_LINEA).wait(seconds).check();
	}	
	
	public boolean isVisibleAnyElementLoyalty() {
		return state(VISIBLE, XP_LOYALTY_ELEMENT).check();
	}	
	
	public boolean isVisibleCommsHeaderBannerLoyalty(int seconds) {
		return new CommsHeaderBanner().isHeaderBannerMangoLikesYou(seconds);
	}
	
	public boolean isBanners() {
		for (var bannerType : BannerType.values()) {
			if (isBanner(bannerType)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isBanner() {
		return dataBanners.isBanner();
	}
	
	public boolean isBanner(BannerType bannerType) {
		return dataBanners.isBanner(bannerType);
	}
	
	public void clickBanner(int posBanner) {
		dataBanners.clickBanner(posBanner);
	}
	
	public void clickBannerAndWaitLoad(DataBanner dataBanner) {
		Banner.click(dataBanner);
		waitLoadPage();
	}
	
	public DataBanner getDataBanner(int posBanner) {
		return getListDataBanners().get(posBanner-1);
	}
	
	public List<DataBanner> getListDataBanners() {
		if (dataBanners==null) {
			reloadDataBanners();
		}
		return dataBanners.getList();
	}
	
	public void reloadDataBanners() {
		dataBanners = loadDataBanners();
	}
	
	public boolean checkUrlNotMatch(String url, int seconds) {
		int secondsCount = 0;
		do {
			if (url.compareTo(getCurrentUrl())!=0) {
				return true;
			}
			waitMillis(1000);
			secondsCount+=1;
		}
		while (secondsCount<seconds);
		return false;
	}
	
	public boolean checkElementsNotEquals(int elementosPagina, int margin, int maxSeconds) {
		int seconds = 0;
		do {
			if (Math.abs(elementosPagina - getElements("//*").size()) > margin) {
				return true;
			}
			waitMillis(1000);
			seconds+=1;
		}
		while (seconds<maxSeconds);
		return false;
	}
	
	private DataBanners loadDataBanners() {
		var dataBannersNew = new DataBanners();
		List<DataBanner> listDataBanners = new ArrayList<>();
		for (var bannerType : BannerType.values()) {
			var banner = Banner.make(bannerType);
			var listDataBannersOpt = banner.getListDataBanners();
			if (listDataBannersOpt.isPresent()) {
				listDataBanners.addAll(listDataBannersOpt.get());
			}
		}
		dataBannersNew.setList(listDataBanners);
		return dataBannersNew;
	}
	
}
