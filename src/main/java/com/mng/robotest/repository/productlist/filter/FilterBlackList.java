package com.mng.robotest.repository.productlist.filter;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.repository.productlist.entity.GarmentCatalog;

public class FilterBlackList implements Filter {
	
//	private static final List<String> BLACK_LIST = Arrays.asList(
//		"57029404" //Al intentar darlo de alta en la bolsa aparece un error de artículo agotado
//	);
	private static final List<String> BLACK_LIST = new ArrayList<>();
	
	@Override
	public List<GarmentCatalog> filter(List<GarmentCatalog> garments) throws Exception {
		return garments.stream()
				.filter(g -> !BLACK_LIST.contains(g.getGarmentId()))
				.toList();
	}	
}
