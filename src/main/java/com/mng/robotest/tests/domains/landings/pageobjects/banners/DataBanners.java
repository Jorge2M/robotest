package com.mng.robotest.tests.domains.landings.pageobjects.banners;

import java.util.List;
import java.util.Comparator;

import com.mng.robotest.tests.domains.landings.pageobjects.banners.Banner.BannerType;

public class DataBanners {

	private List<DataBanner> list;
	
	public List<DataBanner> getList() {
		return getListSorted();
	}
	private List<DataBanner> getListSorted() {
		List<DataBanner> sortedList = list.stream()
	    	.sorted(Comparator.comparingInt((DataBanner data) -> data.getRectangle().y)
	    					  .thenComparingInt(data -> data.getRectangle().x))
            .toList();
	    
        for (int i = 0; i < list.size(); i++) {
            sortedList.get(i).setPosition(i + 1);
        }
	    return sortedList;
	}
	
	public void setList(List<DataBanner> list) {
		this.list = list;
	}
	
	public boolean isBanner() {
		return !list.isEmpty();
	}
	
	public boolean isBanner(BannerType bannerType) {
		for (var banner : list) {
			if (banner.getBannerType()==bannerType) {
				return true;
			}
		}
		return false;
	}
	
	public void clickBanner(int posBanner) {
		getBanner(posBanner).click();
	}
	
	private DataBanner getBanner(int posBanner) {
		return getList().get(posBanner);
	}
	
}
