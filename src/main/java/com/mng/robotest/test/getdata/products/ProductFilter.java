package com.mng.robotest.test.getdata.products;

import java.util.List;
import java.util.Optional;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.getdata.products.data.ProductList;
import com.mng.robotest.test.getdata.products.filter.Filter;
import com.mng.robotest.test.getdata.products.filter.FilterManyColors;
import com.mng.robotest.test.getdata.products.filter.FilterOnline;
import com.mng.robotest.test.getdata.products.filter.FilterPersonalizable;
import com.mng.robotest.test.getdata.products.filter.FilterStock;
import com.mng.robotest.test.getdata.products.filter.FilterTotalLook;
import com.mng.robotest.test.getdata.products.sort.SortFactory.SortBy;

public class ProductFilter {

	public enum FilterType {
		TOTAL_LOOK, 
		MANY_COLORS, 
		ONLINE, 
		NO_ONLINE, 
		STOCK,
		PERSONALIZABLE
	};
	
	private final ProductList productList;
	private final AppEcom app;
	private final String urlForJavaCall;
	
	public ProductFilter(ProductList productList, AppEcom app, String urlForJavaCall) {
		this.productList = productList;
		this.app = app;
		this.urlForJavaCall = urlForJavaCall;
	}
	
	private List<GarmentCatalog> getAll(SortBy sortBy) {
		return productList.getAllGarments(sortBy);
	}
	
	public Optional<GarmentCatalog> getOneFiltered(List<FilterType> filters, SortBy sortBy) 
			throws Exception {
		List<GarmentCatalog> listFiltered = getAll(sortBy);
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
	
	public List<GarmentCatalog> getListFiltered(List<FilterType> filters, SortBy sortBy) throws Exception {
		List<GarmentCatalog> listFiltered = getAll(sortBy);
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
		case TOTAL_LOOK:
			return new FilterTotalLook(urlForJavaCall, app, productList.getStockId());
		case MANY_COLORS:
			return new FilterManyColors();
		case ONLINE:
			return new FilterOnline();
		case NO_ONLINE:
			return new FilterOnline(true);
		case STOCK:
			return new FilterStock();
		case PERSONALIZABLE:
			return new FilterPersonalizable();
		default:
			return null;
		}
	}
}
