package com.mng.robotest.test80.mango.test.getdata.products;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.getdata.products.data.ProductList;


public class ProductFilter {

	public enum FilterType {TotalLook, ManyColors, Online, NoOnline, Stock};
	
	private final ProductList productList;
	private final AppEcom app;
	private final String urlForJavaCall;
	
	public ProductFilter(ProductList productList, AppEcom app, String urlForJavaCall) {
		this.productList = productList;
		this.app = app;
		this.urlForJavaCall = urlForJavaCall;
	}
	
	private List<Garment> getAll() {
		return productList.getGroups().stream()
				.map(g -> g.getGarments())
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}
	
	public Optional<Garment> getOneFiltered(List<FilterType> filters) throws Exception {
		List<Garment> listFiltered = getAll();
		for (int i=0; i<filters.size(); i++) {
			FilterType filterType = filters.get(i);
			Filter filter = factoryFilter(filterType);
			if (i==filters.size()-1) { //Último filtro
				return filter.getOne(listFiltered);
			}
			listFiltered = filter.filter(listFiltered);
			if (listFiltered==null || listFiltered.size()==0) {
				return Optional.empty();
			}
		}
		return Optional.empty();
	}
	
	public List<Garment> getListFiltered(List<FilterType> filters) throws Exception {
		List<Garment> listFiltered = getAll();
		for (FilterType filterType : filters) {
			Filter filter = factoryFilter(filterType);
			listFiltered = filter.filter(listFiltered);
			if (listFiltered==null || listFiltered.size()==0) {
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
		default:
			return null;
		}
	}
}
