package com.mng.robotest.test.getdata.products;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.getdata.products.data.ProductLabel;

public class FilterPersonalizable implements Filter {
	
	@Override
	public List<GarmentCatalog> filter(List<GarmentCatalog> garments) throws Exception {
		List<GarmentCatalog> listGarmentsPersonalizable = new ArrayList<>();
		for (GarmentCatalog garment : garments) {
			if (isGarmentOnline(garment)) {
				listGarmentsPersonalizable.add(garment);
			}
		}
		return listGarmentsPersonalizable;
	}	
	
	private boolean isGarmentOnline(GarmentCatalog garment) {
		for (ProductLabel productLabel : garment.getLabels().getProductLabels()) {
			if (productLabel!=null && "personalizable".compareTo(productLabel.getKey())==0) {
				return true;
			}
		}
		return false;
	}
}
