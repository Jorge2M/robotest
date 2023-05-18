package com.mng.robotest.test.pageobject.shop.filtros;

import java.util.List;

public enum FilterOrdenacion {
	PRECIO_ASC("scendente", "asc"), 
	PRECIO_DESC("escendente", "desc"), 
	TEMPORADA_ASC("", ""), 
	TEMPORADA_DESC("", ""),  
	NO_ORDENADO("", "");
	
	String valueForDesktop;
	String valueForMobil;
	List<Integer> temporadasIniciales = null;
	private FilterOrdenacion(String valueForDesktop, String valueForMobil) {
		this.valueForDesktop = valueForDesktop;
		this.valueForMobil = valueForMobil;
	}
	
	private FilterOrdenacion(String valueForDesktop, String valueForMobil, List<Integer> temporadasIniciales) {
		this.valueForDesktop = valueForDesktop;
		this.valueForMobil = valueForMobil;
		this.temporadasIniciales = temporadasIniciales;
	}
	
	public String getValueForDesktop() {
		return this.valueForDesktop;
	}
	
	public String getValueForMobil() {
		return this.valueForMobil;
	}
	
	public List<Integer> getTemporadasIniciales() {
		return this.temporadasIniciales;
	}
}
