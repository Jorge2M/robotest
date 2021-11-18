package com.mng.robotest.test80.mango.test.pageobject.shop.filtros;

public enum FiltroMobil {
	Ordenar ("//nav[@class[contains(.,'orders')]]"), 
	Coleccion ("//nav[not(@class[contains(.,'orders')])]//button[text()='Colección']//ancestor::nav"),
	Familia ("//nav[@class='nav-filters']//button[not(text()='Colores') and not(text()='Tallas') and not(text()='Colección')]//ancestor::nav"), 
	Colores ("//nav//button[text()='Colores']//ancestor::nav"), 
	Tallas ("//nav//button[text()='Tallas']//ancestor::nav");
	
	final static String XPathFiltroMulti = "//*[@class[contains(.,'orders-filters-scroll')]]";
	String xpathLineaFiltro;
	private FiltroMobil(String xpathLineaFiltro) {
		this.xpathLineaFiltro = xpathLineaFiltro;
	}
	
	public String getXPathLineaFiltro() {
		return (XPathFiltroMulti + xpathLineaFiltro);
	}
}
