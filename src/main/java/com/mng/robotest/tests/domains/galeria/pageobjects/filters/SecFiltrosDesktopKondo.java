package com.mng.robotest.tests.domains.galeria.pageobjects.filters;

import com.mng.robotest.testslegacy.data.Color;

public class SecFiltrosDesktopKondo extends SecFiltrosDesktop {

	private static final String XPATH_WRAPPER = "//div[@id='catalogMenu']"; 
	private static final String XPATH_BUTTON_FILTRAR = "//button[@data-testid='plp.filters.desktop.button']";
	private static final String XPATH_CAPA_FILTERS = "//*[@data-testid='plp.filters.desktop.panel']";
	private static final String XPATH_LINK_COLLECTION = "//li//input[@name[contains(.,'onSale')]]";
	private static final String XPATH_BUTTON_MOSTRAR_ARTICULOS = XPATH_CAPA_FILTERS + "/div[2]/div[4]/button";
	
	@Override
	String getXPathWrapper() {
		return XPATH_WRAPPER;
	}
	
	@Override
	String getXPathButtonFiltrar() {
		return XPATH_BUTTON_FILTRAR;
	}
	
	@Override
	String getXPathMostrarArticulos() {
		return XPATH_BUTTON_MOSTRAR_ARTICULOS;
	}

	@Override
	String getXPathCapaFilters() {
		return XPATH_CAPA_FILTERS;
	}
	
	@Override
	String getXPathLinkCollection() {
		return XPATH_LINK_COLLECTION;
	}
	
	@Override
	String getXPathLinkOrdenacion(FilterOrdenacion ordenacion) {
		return 
			"//*[@data-testid='plp.filter-group.order.nav']" + 
			"//input[@id='" + ordenacion.getValue() + "']";
	}
	
	@Override
	String getXPathLinkColor(Color color) {
		return 
			"//label[@for[contains(.,'colorGroups')]]" + 
			"//span[text()[contains(.,'" + color.getNameFiltro() + "')]]";
	}

}
