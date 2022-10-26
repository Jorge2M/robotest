package com.mng.robotest.getdata.productlist.filter;

import java.util.List;
import java.util.Optional;

import com.mng.robotest.getdata.productlist.entity.GarmentCatalog;

public interface Filter {

	public List<GarmentCatalog> filter(List<GarmentCatalog> garments) throws Exception;
	
	public default Optional<GarmentCatalog> getOne(List<GarmentCatalog> garments) throws Exception {
		return filter(garments).stream().findFirst();
	}
	
}
