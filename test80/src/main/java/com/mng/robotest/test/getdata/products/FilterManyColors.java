package com.mng.robotest.test.getdata.products;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test.getdata.products.data.Garment;

public class FilterManyColors implements Filter {
	
	@Override
	public List<Garment> filter(List<Garment> garments) throws Exception {
		List<Garment> listGarmentsWithManyColors = new ArrayList<>();
		for (Garment garment : garments) {
			if (garment.getColors().size() > 1) {
				listGarmentsWithManyColors.add(garment);
			}
		}
		return listGarmentsWithManyColors;
	}
	
}
