package com.mng.robotest.tests.domains.galeria.pageobjects.filters.desktop;

import java.util.List;

import com.mng.robotest.tests.domains.galeria.pageobjects.filters.FilterOrdenacion;
import com.mng.robotest.testslegacy.data.Color;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecFiltrosDesktopKondo extends SecFiltrosDesktop {

	private static final String XP_WRAPPER = "//div[@id='catalogMenu']"; 
	private static final String XP_BUTTON_FILTRAR = "//button[@data-testid='plp.filters.desktop.button']";
	private static final String XP_LABEL_FILTER = XP_WRAPPER + "//li";
	private static final String XP_CAPA_FILTERS = "//*[@data-testid='plp.filters.desktop.panel']";
	private static final String XP_LINK_COLLECTION = "//li//input[@name[contains(.,'onSale')]]";
	private static final String XP_BUTTON_MOSTRAR_ARTICULOS = XP_CAPA_FILTERS + "/div[2]/div[3]/button";

	private String getXPathFilterTag(String tag) {
		return 
			XP_WRAPPER + 
			"//button[@aria-label[contains(.,'" + tag + "')]]";
	}
	
	@Override
	String getXPathWrapper() {
		return XP_WRAPPER;
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
	String getXPathCapaFilters() {
		return XP_CAPA_FILTERS;
	}
	
	@Override
	String getXPathLinkCollection() {
		return XP_LINK_COLLECTION;
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
	
	@Override
	String getXPathLabel(String label) {
		return XP_LABEL_FILTER + "//self::*[text()='" + label + "']";
	}
	
	public boolean isVisibleColorTags(List<Color> colors) {
		return colors.stream()
			.map(color -> getXPathFilterTag(color.getNameFiltro()))
			.filter(xpath -> !state(VISIBLE, xpath).check())
			.findAny().isEmpty();
	}

}
