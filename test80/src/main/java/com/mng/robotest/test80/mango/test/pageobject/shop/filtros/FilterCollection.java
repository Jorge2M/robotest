package com.mng.robotest.test80.mango.test.pageobject.shop.filtros;

import java.util.Arrays;
import java.util.List;

public enum FilterCollection {
	all(Arrays.asList(2,3,4), "true,false", "Todo"), 
	sale(Arrays.asList(2,3), "true", "Rebajas"), 
	nextSeason(Arrays.asList(4), "false", "Nueva Temporada");
	
	List<Integer> listTempArticles;
	String dataFilterValue = "";
	String valueMobil = "";
	private FilterCollection(List<Integer> listTempArticles, String dataFilterValue, String textMobil) {
		this.listTempArticles = listTempArticles;
		this.dataFilterValue = dataFilterValue;
		this.valueMobil = textMobil;
	}
	
	public List<Integer> getListTempArticles() {
		return this.listTempArticles;
	}
	
	public String getDataFilterValue() {
		return dataFilterValue;
	}
	
	public String getValueMobil() {
		return this.valueMobil;
	}
}
