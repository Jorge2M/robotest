package com.mng.robotest.test.getdata.products;

import java.util.List;
import java.util.Optional;

import com.mng.robotest.test.getdata.products.data.Garment;

public interface Filter {

	public List<Garment> filter(List<Garment> garments) throws Exception;
	
	public default Optional<Garment> getOne(List<Garment> garments) throws Exception {
		return filter(garments).stream().findFirst();
	}
	
}
