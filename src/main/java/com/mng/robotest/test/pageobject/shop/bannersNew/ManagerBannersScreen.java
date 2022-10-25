package com.mng.robotest.test.pageobject.shop.bannersNew;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;

import com.mng.robotest.domains.transversal.PageBase;

public class ManagerBannersScreen extends PageBase {
	
	private List<DataBanner> listDataBannersOrderedByPosition = new ArrayList<>();
	private int maxBannersToLoad = 100;
	
	public ManagerBannersScreen(int maxBannersToLoad) {
		this.maxBannersToLoad = maxBannersToLoad;
		clearAndStoreNewBannersDataFromScreen();
	}
	
	public ManagerBannersScreen() {
		clearAndStoreNewBannersDataFromScreen();
	}
	
	public List<DataBanner> getListDataBanners() {
		return listDataBannersOrderedByPosition;
	}
	
	public void reloadBanners() {
		clearAndStoreNewBannersDataFromScreen();
	}
	
	public void clearAndStoreNewBannersDataFromScreen() {
		List<BannerType> listTypeBannersToStore = Arrays.asList(BannerType.values());
		clearAndStoreNewBannersDataFromScreen(listTypeBannersToStore);
	}
	
	public void clearAndStoreNewBannersDataFromScreen(List<BannerType> listBannerTypes) {
		clearBannersData(listBannerTypes);
		for (BannerType bannerType : listBannerTypes) {
			BannerObject bannerObject = BannerObjectFactory.make(bannerType);
			if (bannerType==BannerType.Standar) {
				//Para recopilar este tipo de banners hemos de paginar para asegurarnos que se visualizan todos
				new Actions(driver).sendKeys(Keys.PAGE_DOWN).perform();
			}
			List<DataBanner> listBannersOfType = bannerObject.getListBannersDataUntil(maxBannersToLoad, 1);
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
					
					if (locationBanner1.x != locationBanner2.x) {
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
			if (dataBannerAnt!=null &&
				dataBanner.isOverlayedWith(dataBannerAnt)) {
				dataBanner.isOverlayed=true;
				dataBannerAnt.isOverlayed=true;
			}
			dataBannerAnt = dataBanner;
		}
	}
	
	public boolean existBanners() {
		return (!listDataBannersOrderedByPosition.isEmpty());
	}
	
	public static boolean isBanners() {
		int maxBannersToLoad = 1;
		ManagerBannersScreen manager = new ManagerBannersScreen(maxBannersToLoad);
		return manager.existBanners();
	}
	
	public void clickBannerAndWaitLoad(int posBanner) {
		DataBanner dataBanner = getBanner(posBanner);
		clickBannerAndWaitLoad(dataBanner);
	}
	
	public void clickBannerAndWaitLoad(DataBanner dataBanner) {
		BannerObject bannerObject = BannerObjectFactory.make(dataBanner.getBannerType());
		bannerObject.clickBannerAndWaitLoad(dataBanner);
	}
}
