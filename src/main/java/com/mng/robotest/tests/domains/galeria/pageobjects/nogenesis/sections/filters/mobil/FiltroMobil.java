package com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.mobil;

public enum FiltroMobil {
	ORDENAR(
		"//nav[@data-testid[contains(.,'group.order')]]//button",
		//TODO Galería Kondo (18-10-23)
		"//button[@id[contains(.,'Ordenar')]]",
		"//label[@for[contains(.,'generic-order')]]"), 
	FAMILIA(
		"//nav[@data-testid[contains(.,'group.familia')]]//button",
		"//button[@aria-controls]",
		"//label[@for[contains(.,'generic-subfamilies')]]"), 
	COLORES(
		"//nav[@data-testid[contains(.,'group.color')]]//button",
		"//button[@id[contains(.,'Colores')]]",
		"//label[@for[contains(.,'colorGroups')]]"), 
	TALLAS(
		"//nav[@data-testid[contains(.,'group.talla')]]//button",
		"//button[@id[contains(.,'Tallas')]]",
		"//label[@for[contains(.,'sizes')]]"),
	PRECIOS(
		"Inexistent",
		"//button[@id[contains(.,'Precio')]]",
		"//label[@for[contains(.,'onSale')]]");	
	
	static final String XP_FILTRO_MULTI_OLD = "//*[@class[contains(.,'orders-filters-scroll')]]";
	static final String XP_FILTRO_MULTI_NORMAL = SecFiltrosMobil.XP_FILTER_PANEL;
	String xpathOld;
	String xpathNormal;
	String xpathOptionNormal;
	private FiltroMobil(String xpathOld, String xpathNormal, String xpathOptionNormal) {
		this.xpathOld = xpathOld;
		this.xpathNormal = xpathNormal;
		this.xpathOptionNormal = xpathOptionNormal;
	}
	
	public String getXPathOld() {
		return XP_FILTRO_MULTI_OLD + xpathOld;
	}
	public String getXPathNormal() {
		return XP_FILTRO_MULTI_NORMAL + xpathNormal + "/../..";
	}
	public String getXPathOptionNormal() {
		return XP_FILTRO_MULTI_NORMAL + xpathOptionNormal;
	}
	
}
