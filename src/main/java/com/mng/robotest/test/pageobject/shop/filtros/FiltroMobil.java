package com.mng.robotest.test.pageobject.shop.filtros;

public enum FiltroMobil {
//	Ordenar ("//nav[@class[contains(.,'orders')]]"), 
//	Coleccion ("//nav[not(@class[contains(.,'orders')])]//button[text()='Colección']//ancestor::nav"),
//	Familia ("//nav[@class='nav-filters']//button[not(text()='Colores') and not(text()='Tallas') and not(text()='Colección')]//ancestor::nav"), 
//	Colores ("//nav//button[text()='Colores']//ancestor::nav"), 
//	Tallas ("//nav//button[text()='Tallas']//ancestor::nav");
	
	Ordenar("//nav[@data-testid[contains(.,'group.order')]]//button"), 
	Coleccion("//nav[@data-testid[contains(.,'group.nuevaTemporada')]]//button"),
	Familia("//nav[@data-testid[contains(.,'group.familia')]]//button"), 
	Colores("//nav[@data-testid[contains(.,'group.color')]]//button"), 
	Tallas("//nav[@data-testid[contains(.,'group.talla')]]//button");	
	
	static final String XPATH_FILTRO_MULTI = "//*[@class[contains(.,'orders-filters-scroll')]]";
	String xpathLineaFiltro;
	private FiltroMobil(String xpathLineaFiltro) {
		this.xpathLineaFiltro = xpathLineaFiltro;
	}
	
	public String getXPathLineaFiltro() {
		return (XPATH_FILTRO_MULTI + xpathLineaFiltro);
	}
}
