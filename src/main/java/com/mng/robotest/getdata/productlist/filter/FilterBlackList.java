package com.mng.robotest.getdata.productlist.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mng.robotest.getdata.productlist.entity.GarmentCatalog;

public class FilterBlackList implements Filter {
	
	private static final List<String> BLACK_LIST = Arrays.asList("47100065", "47110064");
	
	@Override
	public List<GarmentCatalog> filter(List<GarmentCatalog> garments) throws Exception {
		List<GarmentCatalog> listGarmentsWithStock = new ArrayList<>();
		for (GarmentCatalog garment : garments) {
			if (!BLACK_LIST.contains(garment.getGarmentId())) {
				listGarmentsWithStock.add(garment);
			}
		}
		return listGarmentsWithStock;
	}	
}
