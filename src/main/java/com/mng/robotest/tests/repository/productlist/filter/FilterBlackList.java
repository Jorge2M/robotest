package com.mng.robotest.tests.repository.productlist.filter;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog;

public class FilterBlackList implements Filter {
	
	public static final List<String> BLACK_LIST = Arrays.asList(
		"77094027",
		"77093267",
		"77093262",
		"77083263",
		"77014034",
		"77014032" //Tienen problemas en CloudTest/PRE (se visualiza la PLP en lugar de la PDP)
	);
//	private static final List<String> BLACK_LIST = new ArrayList<>();
	
	@Override
	public List<GarmentCatalog> filter(List<GarmentCatalog> garments) throws Exception {
		return garments.stream()
				.filter(g -> !BLACK_LIST.contains(g.getGarmentId()))
				.toList();
	}	
}
