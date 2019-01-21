package com.mng.robotest.test80.mango.test.pageobject.shop.filtros;

public enum FiltroMobil {
    Ordenar (
    	"//select[@id='order']",
    	"//nav[@class[contains(.,'orders')]]"), 
    Coleccion (
    	"//select[@id='nuevaTemporadaFilter']", 
        "//nav//button[text()='Colección']//ancestor::nav"),
    Familia (
    	"//select[@id='familiaFilter']",
    	"//button[2]//ancestor::nav"), 
    Colores (
    	"//select[@id='colorFilter']", 
    	"//nav//button[text()='Colores']//ancestor::nav"), 
    Tallas (
    	"//select[@id='tallaFilter']", 
    	"//nav//button[text()='Tallas']//ancestor::nav");
	
	final static String XPathFiltroSimple = "//*[@class[contains(.,'filters-container')]]";
	final static String XPathFiltroMulti = "//*[@class[contains(.,'orders-filters-scroll')]]";
	String xpathLineaFiltroSimple;
	String xpathLineaFiltroMulti;
	private FiltroMobil(String xpathLineaFiltroSimple, String xpathLineaFiltroMulti) {
		this.xpathLineaFiltroSimple = xpathLineaFiltroSimple;
		this.xpathLineaFiltroMulti = xpathLineaFiltroMulti;
	}
	
	public String getXPathLineaFiltroSimple() {
		return (XPathFiltroSimple + xpathLineaFiltroSimple);
	}
	
	public String getXPathLineaFiltroMulti() {
		return (XPathFiltroMulti + xpathLineaFiltroMulti);
	}
}
