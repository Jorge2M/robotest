package com.mng.robotest.tests.domains.transversal.banners.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class BannerCabeceraObject extends BannerObject {

	private static final String XPATH_WRAPPER_BANNER = "//div[@class='vsv-binteriorwrap' or @class='vsv-bannercontainer']//div[@class='vsv-box' and @data-id]";
	private static final String XPATH_BANNER = XPATH_WRAPPER_BANNER + "//div[@class[contains(.,'vsv-content-web')]]";
	private static final String XPATH_ANCOR_RELATIVE_BANNER = "../a[@href]";
	
	public BannerCabeceraObject(BannerType bannerType) {
		super(bannerType, XPATH_BANNER);
	}
	
	@Override
	protected String getUrlBanner(WebElement bannerScreen) {
		String urlBanner = "";
		if (state(Present, bannerScreen).by(By.xpath(XPATH_ANCOR_RELATIVE_BANNER)).check()) {
			WebElement ancor = bannerScreen.findElement(By.xpath(XPATH_ANCOR_RELATIVE_BANNER));
			urlBanner = ancor.getAttribute("href");
		}
		
		if (urlBanner==null || "".compareTo(urlBanner)==0) {
			urlBanner = getUrlDestinoSearchingForAnchor(bannerScreen);
		}
		return urlBanner;
	}

	@Override
	protected String getSrcImageBanner(WebElement bannerScreen) {	
		return "";
	}
	
	@Override
	public void clickBannerAndWaitLoad(DataBanner dataBanner) {
		WebElement bannerWeb = dataBanner.getBannerWeb();
		By byLink = By.xpath(XPATH_ANCOR_RELATIVE_BANNER);
		WebElement bannerLink;
		if (state(Present, bannerWeb, driver).by(byLink).check()) {
			bannerLink = bannerWeb.findElement(byLink);
		} else {
			bannerLink = bannerWeb;
		}
		click(bannerLink, driver).waitLoadPage(10).type(TypeClick.javascript).exec();
	}
}
