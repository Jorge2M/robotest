package com.mng.robotest.getdata.productlist.filter;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.getdata.productlist.entity.GarmentCatalog;
import com.mng.robotest.getdata.productlist.entity.ProductLabel;

public class FilterOnline implements Filter {
	
	private final boolean reverse;
	
	public FilterOnline() {	
		this.reverse = false;
	}
	
	public FilterOnline(boolean reverse) {
		this.reverse = reverse;
	}
	
	@Override
	public List<GarmentCatalog> filter(List<GarmentCatalog> garments) throws Exception {
		List<GarmentCatalog> listGarmentsNoOnline = new ArrayList<>();
		for (GarmentCatalog garment : garments) {
			if (fitsFilter(garment)) {
				listGarmentsNoOnline.add(garment);
			}
		}
		return listGarmentsNoOnline;
	}	
	
	private boolean fitsFilter(GarmentCatalog garment) {
		boolean online = isGarmentOnline(garment);
		if (online && !reverse) {
			return true;
		}
		return (!online && reverse);
	}
	
	private boolean isGarmentOnline(GarmentCatalog garment) {
		for (ProductLabel productLabel : garment.getLabels().getProductLabels()) {
			if (productLabel!=null && "exclusivo_online".compareTo(productLabel.getKey())==0) {
				return true;
			}
		}
		return false;
	}
}
