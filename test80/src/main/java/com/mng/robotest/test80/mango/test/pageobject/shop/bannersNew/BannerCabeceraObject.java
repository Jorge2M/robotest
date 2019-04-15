package com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.pageobject.TypeOfClick;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public class BannerCabeceraObject extends BannerObject {

	final static String XPathWrapperBanner = "//div[@class='vsv-box' and @data-id]";
	final static String XPathBanner = XPathWrapperBanner + "//div[@class[contains(.,'vsv-content-web')]]";
	final static String XPathAncorRelativeBanner = "../a[@href]";
	
	public BannerCabeceraObject(BannerType bannerType) {
		super(bannerType, XPathBanner);
	}
	
	@Override
    protected String getUrlBanner(WebElement bannerScreen) {
    	String urlBanner = "";
    	if (WebdrvWrapp.isElementPresent(bannerScreen, By.xpath(XPathAncorRelativeBanner))) {
    		WebElement ancor = bannerScreen.findElement(By.xpath(XPathAncorRelativeBanner));
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
	public void clickBannerAndWaitLoad(DataBanner dataBanner, WebDriver driver) throws Exception {
		WebElement bannerWeb = dataBanner.getBannerWeb();
		WebElement bannerLink = bannerWeb.findElement(By.xpath(XPathAncorRelativeBanner));
    	WebdrvWrapp.clickAndWaitLoad(driver, bannerLink, 10, TypeOfClick.javascript);
	}
}
