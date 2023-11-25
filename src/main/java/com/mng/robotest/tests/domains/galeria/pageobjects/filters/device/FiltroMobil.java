package com.mng.robotest.tests.domains.galeria.pageobjects.filters.device;

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
	
	static final String XP_FILTRO_MULTI_NORMAL = "//*[@class[contains(.,'orders-filters-scroll')]]";
	static final String XP_FILTRO_MULTI_KONDO = SecMultiFiltrosDeviceKondo.XP_FILTER_PANEL;
	String xpathNormal;
	String xpathKondo;
	String xpathOptionKondo;
	private FiltroMobil(String xpathNormal, String xpathKondo, String xpathOptionKondo) {
		this.xpathNormal = xpathNormal;
		this.xpathKondo = xpathKondo;
		this.xpathOptionKondo = xpathOptionKondo;
	}
	
	public String getXPathNormal() {
		return XP_FILTRO_MULTI_NORMAL + xpathNormal;
	}
	public String getXPathKondo() {
		return XP_FILTRO_MULTI_KONDO + xpathKondo + "/../..";
	}
	public String getXPathOptionKondo() {
		return XP_FILTRO_MULTI_KONDO + xpathOptionKondo;
	}
	
}
