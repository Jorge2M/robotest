package com.mng.robotest.tests.domains.galeria.pageobjects;

public enum FiltroMobil {
	ORDENAR("//nav[@data-testid[contains(.,'group.order')]]//button"), 
	COLECCION("//nav[@data-testid[contains(.,'group.nuevaTemporada')]]//button"),
	FAMILIA("//nav[@data-testid[contains(.,'group.familia')]]//button"), 
	COLORES("//nav[@data-testid[contains(.,'group.color')]]//button"), 
	TALLAS("//nav[@data-testid[contains(.,'group.talla')]]//button");	
	
	static final String XPATH_FILTRO_MULTI = "//*[@class[contains(.,'orders-filters-scroll')]]";
	String xpathLineaFiltro;
	private FiltroMobil(String xpathLineaFiltro) {
		this.xpathLineaFiltro = xpathLineaFiltro;
	}
	
	public String getXPathLineaFiltro() {
		return (XPATH_FILTRO_MULTI + xpathLineaFiltro);
	}
}
