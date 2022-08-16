package com.mng.robotest.test.pageobject.shop.bannersNew;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public abstract class BannerObject extends PageBase {

	public BannerType bannerType;
	public String XPathBanner;
	
	public BannerObject(BannerType bannerType, String XPathBanner) {
		this.bannerType = bannerType;
		this.XPathBanner = XPathBanner;
	}
	
	abstract protected String getUrlBanner(WebElement bannerScreen);
	abstract protected String getSrcImageBanner(WebElement bannerScreen);
	
	public List<DataBanner> getListBannersData(int maxBannersToLoad) {
		List<DataBanner> listDataBannersReturn = new ArrayList<>();
		List<WebElement> listBannersScreen = getDisplayedBannersInOrder();
		Iterator<WebElement> itBannerScreen = listBannersScreen.iterator();
		int i=0;
		while (itBannerScreen.hasNext() && i<maxBannersToLoad) {
			WebElement bannerScreen = itBannerScreen.next();
			DataBanner dataBanner = new DataBanner();
			dataBanner.setBannerType(getBannerType());
			dataBanner.setBannerWeb(bannerScreen);
			dataBanner.setLocation(bannerScreen.getLocation());
			dataBanner.setSize(bannerScreen.getSize());
			dataBanner.setUrlBanner(getUrlBanner(bannerScreen));
			dataBanner.setSrcImage(getSrcImageBanner(bannerScreen));
			dataBanner.setDirectText(getTextBanner(bannerScreen));
			dataBanner.setFloatingText(getFloatingTextBanner(bannerScreen));
			listDataBannersReturn.add(dataBanner);
			i+=1;
		}
		
		return listDataBannersReturn;
	}
	
	public List<DataBanner> getListBannersDataUntil(int maxBannersToLoad, int maxSeconds) {
		List<DataBanner> listBanners = new ArrayList<>();
		for (int i=0; i<maxSeconds; i++) {
			listBanners = getListBannersData(maxBannersToLoad);
			if (!listBanners.isEmpty()) {
				break;
			}
			myWait(1000);
		}
		
		return listBanners;
	}
	
	private void myWait(int milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		}
		catch (InterruptedException e) {
			Log4jTM.getGlobal().warn("Interrupted!", e);
		    Thread.currentThread().interrupt();
		}
	}
	
	public String getTextBanner(WebElement bannerScreen) {
		String texto = bannerScreen.getAttribute("textContent"); 	
		return (getTextBannerNormalized(texto));
	}
	
	public String getFloatingTextBanner(WebElement bannerScreen) {
		String texto = bannerScreen.findElement(By.xpath("./..")).getText();
		return (getTextBannerNormalized(texto));
	}
	
	private String getTextBannerNormalized(String texto) {
		texto = texto.replaceAll("\t", "").trim();
		while (texto.contains("\n\n") || texto.contains("\n ")) {
			texto = texto.replaceAll("\n\n", "\n");
			texto = texto.replaceAll("\n ", "\n");
		}
		
		return texto;
	}
	
	public boolean isVisibleAnyBanner() {
		List<WebElement> listBanners = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathBanner));
		return !listBanners.isEmpty();
	}
	
	protected List<WebElement> getDisplayedBannersInOrder() {
		List<WebElement> listBanners = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathBanner));
		SeleniumUtils.orderElementsByPositionInScreen(listBanners);
		return listBanners;
	}

	protected String getUrlDestinoSearchingForAnchor(WebElement banner) {
		if (state(Present, banner).by(By.xpath(".//a")).check()) {
			return (banner.findElement(By.xpath(".//a")).getAttribute("href"));
		}
		return "";
	}

	public BannerType getBannerType() {
		return this.bannerType;
	}

	public void clickBannerAndWaitLoad(DataBanner dataBanner) throws Exception {
		WebElement bannerWeb = dataBanner.getBannerWeb();
		click(bannerWeb, driver).type(TypeClick.javascript).waitLoadPage(10).exec();
	}
}
