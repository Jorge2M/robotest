package com.mng.robotest.tests.domains.galeria.pageobjects.filters;

import com.mng.robotest.testslegacy.data.Color;

public class SecFiltrosDesktopNormal extends SecFiltrosDesktop {

	private static final String XPATH_WRAPPER = "//div[@id='stickyMenu']";
	private static final String XPATH_BUTTON_FILTRAR = "//button[@data-testid='plp.filter-sort.toggle.button']";

	//TODO hablar con Sergio Campillo para que añada algún id no-react
	private static final String XPATH_CAPA_FILTERS = "//div[@class[contains(.,'filters--')]]";
	private static final String XPATH_BUTTON_MOSTRAR_ARTICULOS = XPATH_CAPA_FILTERS + "//button";	
	private static final String XPATH_LINK_COLLECTION = "//div[@id='navigationContainer']/button";
	
	@Override
	String getXPathWrapper() {
		return XPATH_WRAPPER;
	}

	@Override
	String getXPathCapaFilters() {
		return XPATH_CAPA_FILTERS;
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
			"//label[(@for[contains(.,'filtercolor')] or @for[contains(.,'multiSelectfilter_GroupsColors')]) and " + 
			"text()[contains(.,'" + color.getNameFiltro() + "')]]";		
	}	
	
}
