package com.mng.robotest.test.pageobject.shop.bannersNew;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;

public class BannerCabeceraObject extends BannerObject {

	private final WebDriver driver;
	
	static final String XPathWrapperBanner = "//div[@class='vsv-binteriorwrap' or @class='vsv-bannercontainer']//div[@class='vsv-box' and @data-id]";
	static final String XPathBanner = XPathWrapperBanner + "//div[@class[contains(.,'vsv-content-web')]]";
	static final String XPathAncorRelativeBanner = "../a[@href]";
	
	public BannerCabeceraObject(BannerType bannerType) {
		super(bannerType, XPathBanner);
		this.driver = TestMaker.getTestCase().get().getDriver();
	}
	
	@Override
	protected String getUrlBanner(WebElement bannerScreen) {
		String urlBanner = "";
		if (state(Present, bannerScreen, driver).by(By.xpath(XPathAncorRelativeBanner)).check()) {
			WebElement ancor = bannerScreen.findElement(By.xpath(XPathAncorRelativeBanner));
			urlBanner = ancor.getAttribute("href");
		}
		
		if (urlBanner==null || "".compareTo(urlBanner)==0) {
			urlBanner = getUrlDestinoSearchingForAnchor(bannerScreen, driver);
		}
		return urlBanner;
	}

	@Override
	protected String getSrcImageBanner(WebElement bannerScreen) {	
		return "";
	}
	
	@Override
	public void clickBannerAndWaitLoad(DataBanner dataBanner, WebDriver driver) throws Exception {
		WebElement bannerWeb = dataBanner.getBannerWeb();
		By byLink = By.xpath(XPathAncorRelativeBanner);
		WebElement bannerLink;
		if (state(Present, bannerWeb, driver).by(byLink).check()) {
			bannerLink = bannerWeb.findElement(byLink);
		} else {
			bannerLink = bannerWeb;
		}
		click(bannerLink, driver).waitLoadPage(10).type(TypeClick.javascript).exec();
	}
}
