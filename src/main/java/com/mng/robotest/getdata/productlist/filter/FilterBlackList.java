package com.mng.robotest.getdata.productlist.filter;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.getdata.productlist.entity.GarmentCatalog;

public class FilterBlackList implements Filter {
	
	//private static final List<String> BLACK_LIST = Arrays.asList("47100065", "47110064", "47020218", "47091313", "47057124", "37000328", "37050458", "37020080", "37020079", "37020375", "37000324", "37000347", "37000327");
	private static final List<String> BLACK_LIST = Arrays.asList("47100065", "47110064", "47020218", "47091313", "47057124");
	//private static final List<String> BLACK_LIST = new ArrayList<>();
	
	@Override
	public List<GarmentCatalog> filter(List<GarmentCatalog> garments) throws Exception {
		return garments.stream()
				.filter(g -> !BLACK_LIST.contains(g.getGarmentId()))
				.toList();
	}	
}
