package com.mng.robotest.tests.repository.productlist.filter;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog;

public class FilterPersonalizable implements Filter {
	
	@Override
	public List<GarmentCatalog> filter(List<GarmentCatalog> garments) throws Exception {
		List<GarmentCatalog> listGarmentsPersonalizable = new ArrayList<>();
		for (var garment : garments) {
			if (isGarmentOnline(garment)) {
				listGarmentsPersonalizable.add(garment);
			}
		}
		return listGarmentsPersonalizable;
	}	
	
	private boolean isGarmentOnline(GarmentCatalog garment) {
		for (var productLabel : garment.getLabels().getProductLabels()) {
			if (productLabel!=null && "personalizable".compareTo(productLabel.getKey())==0) {
				return true;
			}
		}
		return false;
	}
	
}
