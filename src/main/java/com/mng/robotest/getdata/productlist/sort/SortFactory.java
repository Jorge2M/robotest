package com.mng.robotest.getdata.productlist.sort;

import java.util.Comparator;

import com.mng.robotest.getdata.productlist.entity.GarmentCatalog;

public class SortFactory {

	public enum SortBy {
		STOCK,
		STOCK_DESCENDENT;
	}
	
	public static Comparator<GarmentCatalog> get(SortBy sort) {
		switch (sort) {
		case STOCK:
			return ((o1, o2) -> o1.getStock() - o2.getStock());
		case STOCK_DESCENDENT:
		default:
			return ((o1, o2) -> o2.getStock() - o1.getStock());
		}
	}

}
