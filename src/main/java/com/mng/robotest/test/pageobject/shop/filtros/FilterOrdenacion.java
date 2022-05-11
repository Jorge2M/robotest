package com.mng.robotest.test.pageobject.shop.filtros;

import java.util.Arrays;
import java.util.List;

public enum FilterOrdenacion {
	PrecioAsc("scendente", "asc"), 
	PrecioDesc("escendente", "desc"), 
	TemporadaAsc("", ""), 
	TemporadaDesc("", ""),  
	BloqueTemporadas_3y4_despues_la_5("", "", Arrays.asList(3,4)),
	BloqueTemporada_5_despues_la_3y4("", "", Arrays.asList(5)),
	Temporada4andAfter2and3("", ""),
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
