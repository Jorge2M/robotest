package com.mng.robotest.test80.mango.test.getdata.products;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.getdata.products.data.ProductLabel;

public class FilterOnline implements Filter {
	
	private final boolean reverse;
	
	public FilterOnline() {	
		this.reverse = false;
	}
	
	public FilterOnline(boolean reverse) {
		this.reverse = true;
	}
	
	@Override
	public List<Garment> filter(List<Garment> garments) throws Exception {
		List<Garment> listGarmentsNoOnline = new ArrayList<>();
		for (Garment garment : garments) {
			if (fitsFilter(garment)) {
				listGarmentsNoOnline.add(garment);
			}
		}
		return listGarmentsNoOnline;
	}	
	
	private boolean fitsFilter(Garment garment) {
		boolean online = isGarmentOnline(garment);
		if (online && !reverse) {
			return true;
		}
		if (!online && reverse) {
			return true;
		}
		return false;
	}
	
	private boolean isGarmentOnline(Garment garment) {
		for (ProductLabel productLabel : garment.getLabels().getProductLabels()) {
			if (productLabel!=null && "exclusivo_online".compareTo(productLabel.getKey())==0) {
				return true;
			}
		}
		return false;
	}
}
