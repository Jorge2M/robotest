package com.mng.robotest.test.getdata.products.filter;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test.getdata.products.data.GarmentCatalog;

public class FilterManyColors implements Filter {
	
	@Override
	public List<GarmentCatalog> filter(List<GarmentCatalog> garments) throws Exception {
		List<GarmentCatalog> listGarmentsWithManyColors = new ArrayList<>();
		for (GarmentCatalog garment : garments) {
			if (garment.getColors().size() > 1) {
				listGarmentsWithManyColors.add(garment);
			}
		}
		return listGarmentsWithManyColors;
	}
	
}
