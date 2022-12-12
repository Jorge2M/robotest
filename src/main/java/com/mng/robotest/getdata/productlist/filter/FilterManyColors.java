package com.mng.robotest.getdata.productlist.filter;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.getdata.productlist.entity.GarmentCatalog;

public class FilterManyColors implements Filter {
	
	@Override
	public List<GarmentCatalog> filter(List<GarmentCatalog> garments) throws Exception {
		List<GarmentCatalog> listGarmentsWithManyColors = new ArrayList<>();
		for (GarmentCatalog garment : garments) {
			if (garment.getColors().size() > 1 ||
				garment.getNumberOfColors() > 1) {
				listGarmentsWithManyColors.add(garment);
			}
		}
		return listGarmentsWithManyColors;
	}
	
}