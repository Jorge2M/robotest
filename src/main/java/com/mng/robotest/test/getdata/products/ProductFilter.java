package com.mng.robotest.test.getdata.products;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.getdata.products.data.ProductList;


public class ProductFilter {

	public enum FilterType {
		TotalLook, 
		ManyColors, 
		Online, 
		NoOnline, 
		Stock,
		Personalizable};
	
	private final ProductList productList;
	private final AppEcom app;
	private final String urlForJavaCall;
	
	public ProductFilter(ProductList productList, AppEcom app, String urlForJavaCall) {
		this.productList = productList;
		this.app = app;
		this.urlForJavaCall = urlForJavaCall;
	}
	
	private List<GarmentCatalog> getAll() {
		return productList.getGroups().stream()
				.map(g -> g.getGarments())
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}
	
	public Optional<GarmentCatalog> getOneFiltered(List<FilterType> filters) throws Exception {
		List<GarmentCatalog> listFiltered = getAll();
		for (int i=0; i<filters.size(); i++) {
			FilterType filterType = filters.get(i);
			Filter filter = factoryFilter(filterType);
			if (i==filters.size()-1) { //Último filtro
				return filter.getOne(listFiltered);
			}
			listFiltered = filter.filter(listFiltered);
			if (listFiltered==null || listFiltered.isEmpty()) {
				return Optional.empty();
			}
		}
		return Optional.empty();
	}
	
	public List<GarmentCatalog> getListFiltered(List<FilterType> filters) throws Exception {
		List<GarmentCatalog> listFiltered = getAll();
		for (FilterType filterType : filters) {
			Filter filter = factoryFilter(filterType);
			listFiltered = filter.filter(listFiltered);
			if (listFiltered==null || listFiltered.isEmpty()) {
				return listFiltered;
			}
		}
		return listFiltered;
	}
	
	public Filter factoryFilter(FilterType filter) {
		switch (filter) {
		case TotalLook:
			return new FilterTotalLook(urlForJavaCall, app, productList.getStockId());
		case ManyColors:
			return new FilterManyColors();
		case Online:
			return new FilterOnline();
		case NoOnline:
			return new FilterOnline(true);
		case Stock:
			return new FilterStock();
		case Personalizable:
			return new FilterPersonalizable();
		default:
			return null;
		}
	}
}
