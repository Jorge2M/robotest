package com.mng.robotest.test80.mango.test.pageobject.shop.filtros;

import java.util.Arrays;
import java.util.List;

public enum FilterOrdenacion {
	PrecioAsc("scendente", "asc"), 
	PrecioDesc("escendente", "desc"), 
	TemporadaAsc("", ""), 
	TemporadaDesc("", ""),  
	BloqueTemporadas_2y3_despues_la_4("", "", Arrays.asList(2,3)),
	BloqueTemporada_4_despues_la_2y3("", "", Arrays.asList(4)),
	Temporada3andAfter1and2("", ""),
	NOordenado("", "");
	
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
