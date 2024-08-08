package com.mng.robotest.tests.domains.galeria.pageobjects.entity;

import java.util.List;

public enum FilterOrdenacion {
	PRECIO_ASC("asc"), 
	PRECIO_DESC("desc"), 
	RECOMENDADOS("recommended"); 
	
	String value;
	List<Integer> temporadasIniciales = null;
	private FilterOrdenacion(String value) {
		this.value = value;
	}
	
	private FilterOrdenacion(String value, List<Integer> temporadasIniciales) {
		this.value = value;
		this.temporadasIniciales = temporadasIniciales;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public List<Integer> getTemporadasIniciales() {
		return this.temporadasIniciales;
	}
}
