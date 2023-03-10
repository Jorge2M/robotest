package com.mng.robotest.getdata.productlist.filter;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.getdata.productlist.entity.GarmentCatalog;

public class FilterStock implements Filter {
	
	@Override
	public List<GarmentCatalog> filter(List<GarmentCatalog> garments) throws Exception {
		List<GarmentCatalog> listGarmentsWithStock = new ArrayList<>();
		for (GarmentCatalog garment : garments) {
			if (garment.getStock() > 0) {
				listGarmentsWithStock.add(garment);
			}
		}
		return listGarmentsWithStock;
	}		
}
