package com.mng.robotest.tests.domains.galeria.pageobjects.filters.desktop;

import com.mng.robotest.tests.domains.galeria.pageobjects.filters.FilterOrdenacion;
import com.mng.robotest.testslegacy.data.Color;

public class SecFiltrosDesktopNormal extends SecFiltrosDesktop {

	private static final String XP_WRAPPER = "//div[@id='stickyMenu']";
	private static final String XP_BUTTON_FILTRAR = "//button[@data-testid='plp.filter-sort.toggle.button']";

	//TODO hablar con Sergio Campillo para que añada algún id no-react
	private static final String XP_CAPA_FILTERS = "//div[@class[contains(.,'filters--')]]";
	private static final String XP_BUTTON_MOSTRAR_ARTICULOS = XP_CAPA_FILTERS + "//button";	
	
	@Override
	String getXPathWrapper() {
		return XP_WRAPPER;
	}

	@Override
	String getXPathCapaFilters() {
		return XP_CAPA_FILTERS;
	}
	
	@Override
	String getXPathButtonFiltrar() {
		return XP_BUTTON_FILTRAR;
	}
	
	@Override
	String getXPathMostrarArticulos() {
		return XP_BUTTON_MOSTRAR_ARTICULOS;
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
	
	@Override
	String getXPathLabel(String label) {
		//Pending
		return "";
	}	
}
