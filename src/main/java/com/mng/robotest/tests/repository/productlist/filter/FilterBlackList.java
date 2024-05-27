package com.mng.robotest.tests.repository.productlist.filter;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog;

public class FilterBlackList implements Filter {
	
	private static final List<String> BLACK_LIST = Arrays.asList(
		"67085722" //Tiene problemas en CloudTest
	);
//	private static final List<String> BLACK_LIST = new ArrayList<>();
	
	@Override
	public List<GarmentCatalog> filter(List<GarmentCatalog> garments) throws Exception {
		return garments.stream()
				.filter(g -> !BLACK_LIST.contains(g.getGarmentId()))
				.toList();
	}	
}
