package com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class ManagerBannersScreen {
	
	List<DataBanner> listDataBannersOrderedByPosition = new ArrayList<>();
	int maxBannersToLoad = 100;
	
	public ManagerBannersScreen(int maxBannersToLoad, WebDriver driver) {
		this.maxBannersToLoad = maxBannersToLoad;
		clearAndStoreNewBannersDataFromScreen(driver);
	}
	
	public ManagerBannersScreen(WebDriver driver) {
		clearAndStoreNewBannersDataFromScreen(driver);
	}
	
	public List<DataBanner> getListDataBanners() {
		return listDataBannersOrderedByPosition;
	}
	
	public void reloadBanners(WebDriver driver) {
		clearAndStoreNewBannersDataFromScreen(driver);
	}
	
	public void clearAndStoreNewBannersDataFromScreen(WebDriver driver) {
		List<BannerType> listTypeBannersToStore = Arrays.asList(BannerType.values());
		clearAndStoreNewBannersDataFromScreen(listTypeBannersToStore, driver);
	}
	
	public void clearAndStoreNewBannersDataFromScreen(List<BannerType> listBannerTypes, WebDriver driver) {
		clearBannersData(listBannerTypes);
		for (BannerType bannerType : listBannerTypes) {
			BannerObject bannerObject = BannerObjectFactory.make(bannerType);
			if (bannerType==BannerType.Standar) {
				//Para recopilar este tipo de banners hemos de paginar para asegurarnos que se visualizan todos
				new Actions(driver).sendKeys(Keys.PAGE_DOWN).perform();
			}
			List<DataBanner> listBannersOfType = bannerObject.getListBannersDataUntil(maxBannersToLoad, 1, driver);
			addBanners(listBannersOfType);
		}
	}
	
	public void clearBannersData(List<BannerType> listBannerTypes) {
		Iterator<DataBanner> itDataBanner = listDataBannersOrderedByPosition.iterator();
		while (itDataBanner.hasNext()) {
			DataBanner dataBanner = itDataBanner.next(); 
			if (listBannerTypes.contains(dataBanner.getBannerType())) {
				itDataBanner.remove();
			}
		}
	}
	
	public DataBanner getBanner(int position) {
		int indexList = position - 1;
		if (indexList>=0 && (listDataBannersOrderedByPosition.size()) > indexList) {
			return listDataBannersOrderedByPosition.get(indexList);
		}
		return null;
	}
	
	private void addBanners(List<DataBanner> listBanners) {
		listDataBannersOrderedByPosition.addAll(listBanners);
		orderBannersByPositionAndIndex();
	}
	
	private void orderBannersByPositionAndIndex() {
		orderBannersByPosition();
		setBannersPositionAndMarkOverlayeds();
	}
	
	private void orderBannersByPosition() {
		Collections.sort(
			listDataBannersOrderedByPosition, 
			new Comparator<DataBanner>() {
				@Override
				public int compare(DataBanner banner1, DataBanner banner2) {
					Point locationBanner1 = banner1.getLocation();
					Point locationBanner2 = banner2.getLocation();
					if (locationBanner1.y != locationBanner2.y) {
						if (locationBanner1.y > locationBanner2.y) {
							return 1;
						}
						return -1;
					}
					
					if (locationBanner1.x != locationBanner1.x) {
						if (locationBanner1.x > locationBanner2.x) {
							return 1;
						}
						return -1;
					}
					
					return 0;
				}
			}
		);
	}
	
	private void setBannersPositionAndMarkOverlayeds() {
		int position = 1;
		DataBanner dataBannerAnt = null;
		for (DataBanner dataBanner : listDataBannersOrderedByPosition) {
			dataBanner.setPosition(position++);
			if (dataBanner.isOverlayedWith(dataBannerAnt)) {
				dataBanner.isOverlayed=true;
				dataBannerAnt.isOverlayed=true;
			}
			
			dataBannerAnt = dataBanner;
		}
	}
	
	/**
	 * Función que indica si hay banners o no en el contenido de la página
	 */
	public boolean existBanners() {
		return (listDataBannersOrderedByPosition.size() > 0);
	}
	
	public static boolean existBanners(WebDriver driver) {
		int maxBannersToLoad = 1;
		ManagerBannersScreen manager = new ManagerBannersScreen(maxBannersToLoad, driver);
		return (manager.existBanners());
	}
	
	public void clickBannerAndWaitLoad(int posBanner, WebDriver driver) throws Exception {
		DataBanner dataBanner = getBanner(posBanner);
		clickBannerAndWaitLoad(dataBanner, driver);
	}
	
	public void clickBannerAndWaitLoad(DataBanner dataBanner, WebDriver driver) throws Exception {
		BannerObject bannerObject = BannerObjectFactory.make(dataBanner.getBannerType());
		bannerObject.clickBannerAndWaitLoad(dataBanner, driver);
	}
}
