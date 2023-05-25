package com.mng.robotest.test.pageobject.shop.bannersnew;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.domains.base.PageBase;
import com.github.jorge2m.testmaker.conf.Log4jTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public abstract class BannerObject extends PageBase {

	public BannerType bannerType;
	public String xpathBanner;
	
	protected BannerObject(BannerType bannerType, String xpathBanner) {
		this.bannerType = bannerType;
		this.xpathBanner = xpathBanner;
	}
	
	protected abstract String getUrlBanner(WebElement bannerScreen);
	protected abstract String getSrcImageBanner(WebElement bannerScreen);
	
	public List<DataBanner> getListBannersData(int maxBannersToLoad) {
		List<DataBanner> listDataBannersReturn = new ArrayList<>();
		List<WebElement> listBannersScreen = getDisplayedBannersInOrder();
		Iterator<WebElement> itBannerScreen = listBannersScreen.iterator();
		int i=0;
		while (itBannerScreen.hasNext() && i<maxBannersToLoad) {
			WebElement bannerScreen = itBannerScreen.next();
			var dataBanner = new DataBanner();
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
	
	public List<DataBanner> getListBannersDataUntil(int maxBannersToLoad, int seconds) {
		List<DataBanner> listBanners = new ArrayList<>();
		for (int i=0; i<seconds; i++) {
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
		return getTextBannerNormalized(texto);
	}
	
	public String getFloatingTextBanner(WebElement bannerScreen) {
		String texto = bannerScreen.findElement(By.xpath("./..")).getText();
		return getTextBannerNormalized(texto);
	}
	
	private String getTextBannerNormalized(String texto) {
		texto = texto.replace("\t", "").trim();
		while (texto.contains("\n\n") || texto.contains("\n ")) {
			texto = texto.replace("\n\n", "\n");
			texto = texto.replace("\n ", "\n");
		}
		return texto;
	}
	
	public boolean isVisibleAnyBanner() {
		return !getElementsVisible(xpathBanner).isEmpty();
	}
	
	protected List<WebElement> getDisplayedBannersInOrder() {
		List<WebElement> listBanners = getElementsVisible(xpathBanner);
		PageObjTM.orderElementsByPositionInScreen(listBanners);
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

	public void clickBannerAndWaitLoad(DataBanner dataBanner) {
		WebElement bannerWeb = dataBanner.getBannerWeb();
		click(bannerWeb, driver).type(TypeClick.javascript).waitLoadPage(10).exec();
	}
}
