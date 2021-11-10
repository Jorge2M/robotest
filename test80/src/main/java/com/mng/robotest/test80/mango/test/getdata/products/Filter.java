package com.mng.robotest.test80.mango.test.getdata.products;

import java.util.List;

import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;

public interface Filter {

	public List<Garment> filter(List<Garment> garments) throws Exception;
	
	public default Garment getOne(List<Garment> garments) throws Exception {
		return filter(garments).get(0);
	}
	
}
