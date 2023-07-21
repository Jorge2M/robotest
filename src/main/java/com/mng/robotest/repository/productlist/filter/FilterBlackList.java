package com.mng.robotest.repository.productlist.filter;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.repository.productlist.entity.GarmentCatalog;

public class FilterBlackList implements Filter {
	
	private static final List<String> BLACK_LIST = Arrays.asList(
		"47040356" //El artículo está casi agotado en todas las tallas
	);
//	private static final List<String> BLACK_LIST = new ArrayList<>();
	
	@Override
	public List<GarmentCatalog> filter(List<GarmentCatalog> garments) throws Exception {
		return garments.stream()
				.filter(g -> !BLACK_LIST.contains(g.getGarmentId()))
				.toList();
	}	
}
