package com.mng.robotest.test.getdata.products;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test.getdata.products.data.Garment;

public class FilterStock implements Filter {
	
	@Override
	public List<Garment> filter(List<Garment> garments) throws Exception {
		List<Garment> listGarmentsWithStock = new ArrayList<>();
		for (Garment garment : garments) {
			if (garment.getStock() > 0) {
				listGarmentsWithStock.add(garment);
			}
		}
		return listGarmentsWithStock;
	}	
}
