package com.mng.robotest.test.pageobject.shop.bannersNew;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test.exceptions.NotFoundException;
import com.mng.robotest.test.generic.UtilsMangoTest;

public class BannerStandarObject extends BannerObject {

	private final WebDriver driver;
	
	//static final String XPathWrapperBanner = "//div[@class[contains(.,'vsv-box')] and @data-id]";
	static final String XPathWrapperBanner = "//div[@class[contains(.,'bannercontainer')] and @data-bannerid]";
	static final String XPathBanner = XPathWrapperBanner + 
		"//div[@data-cta and not(@data-cta='') and " + 
			  "@data-cta[not(contains(.,'op=ayuda'))] and " + 
			  "not(@class='link')]";
	static final String XPathImageRelativeBanner = "//img[@class='img-responsive']";
	static final String XPathMainTextRelativeBanner = "//div[@class[contains(.,'mainText')]]";
	
	public BannerStandarObject(BannerType bannerType) {
		super(bannerType, XPathBanner);
		this.driver = getTestCase().getDriver();
	}
	
	private TestCaseTM getTestCase() throws NotFoundException {
		Optional<TestCaseTM> testCaseOpt = TestMaker.getTestCase();
		if (testCaseOpt.isEmpty()) {
		  throw new NotFoundException("Not found TestCase");
		}
		return testCaseOpt.get();
	}
	
	@Override
	protected String getUrlBanner(WebElement bannerScreen) {
		String urlBanner = bannerScreen.getAttribute("data-cta");
		if (urlBanner==null || "".compareTo(urlBanner)==0) {
			urlBanner = getUrlDestinoSearchingForAnchor(bannerScreen, driver);
		}
		return urlBanner;
	}

	@Override
	protected String getSrcImageBanner(WebElement bannerScreen) {
		List<WebElement> listImgsBanner = UtilsMangoTest.findDisplayedElements(bannerScreen, By.xpath("." + XPathImageRelativeBanner));
		if (!listImgsBanner.isEmpty()) {
			return (listImgsBanner.get(0).getAttribute("src"));
		}
		return "";
	}
}
