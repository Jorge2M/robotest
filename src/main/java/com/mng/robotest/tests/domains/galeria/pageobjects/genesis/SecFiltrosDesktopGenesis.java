package com.mng.robotest.tests.domains.galeria.pageobjects.genesis;

import java.util.List;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.SecFiltros;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.FilterOrdenacion;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.mobil.FiltroMobil;
import com.mng.robotest.testslegacy.data.Color;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecFiltrosDesktopGenesis extends PageBase implements SecFiltros {

	private static final String XP_BUTTON_FILTRAR = "//*[@data-testid='plp.filters.desktop.button']"; //
	private static final String XP_WRAPPER = XP_BUTTON_FILTRAR + "/../.."; //
	private static final String XP_CAPA_FILTERS = "//*[@data-testid='plp.filters.desktop.panel']"; //
	private static final String XP_BUTTON_MOSTRAR_ARTICULOS = "//*[@data-testid='plp.filters.apply.button']"; //
	
	private static final String XP_INPUT_MINIMO = "//input[@step='1']";
	private static final String XP_INPUT_MAXIMO = "(" + XP_INPUT_MINIMO + ")[2]";

	private String getXPathLinkOrdenacion(FilterOrdenacion ordenacion) { //
		return "//*[@data-testid='plp.filters.mobile.panel.order-']" + ordenacion.getValue() + "/..";
	}
	
	private String getXPathLinkColor(Color color) {
		return 
			"//label[@for[contains(.,'colorGroups')]]" + 
			"//span[text()[contains(.,'" + color.getNameFiltro() + "')]]";
	}
	
	@Override
	public void selectOrdenacion(FilterOrdenacion ordenacion) {
		String xpathLink = getXPathLinkOrdenacion(ordenacion);
		click(xpathLink).exec();
	}
	
	@Override
	public void selecFiltroColores(List<Color> colorsToSelect) {
		showFilters();
		for (Color color : colorsToSelect) {
			String xpathLinkColor = getXPathLinkColor(color);
			moveToElement(xpathLinkColor);
			click(xpathLinkColor).exec();
		}
		acceptFilters();
	}
	
	@Override
	public boolean isClickableFiltroUntil(int seconds) {
		String xpathFilterDesc = getXPathLinkOrdenacion(FilterOrdenacion.PRECIO_DESC);
		return state(CLICKABLE, xpathFilterDesc).wait(seconds).check();
	}
	
	@Override
	public void selectMenu2onLevel(List<String> listMenus) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void selectMenu2onLevel(String menuLabel) {
		throw new UnsupportedOperationException();
	}	
	
	public void bring(BringTo bringTo) {
		bringElement(getElement(XP_WRAPPER), bringTo);
	}
	
	@Override
	public void showFilters() {
		if (!isFiltersShopVisible(1) &&
			state(CLICKABLE, XP_BUTTON_FILTRAR).check()) {
			clickFilterAndSortButton();
		}
	}
	
	@Override
	public void clickFilterAndSortButton() {
		click(XP_BUTTON_FILTRAR).exec();
	}
	
	private void hideFilters() {
		if (isFiltersShopVisible(1) &&
			state(CLICKABLE, XP_BUTTON_FILTRAR).check()) {
			clickFilterAndSortButton();
		}
	}
	
	@Override
	public void acceptFilters() {
		click(XP_BUTTON_MOSTRAR_ARTICULOS).exec();
		waitMillis(1000);
		waitForPageLoaded(driver);
		PageGaleria pageGaleria = PageGaleria.make(channel, app, dataTest.getPais());
		pageGaleria.isVisibleImageArticle(1, 2);
	}
	
	@Override
	public boolean isVisibleSelectorPrecios() {
		showFilters();
		boolean visible = state(VISIBLE, XP_INPUT_MINIMO).check();
		hideFilters();
		return visible;
	}
	
	@Override
	public int getMinImportFilter() {
		state(VISIBLE, XP_INPUT_MINIMO).wait(2).check();
		var minOpt = findElement(XP_INPUT_MINIMO);
		if (minOpt.isEmpty()) {
			return 0;
		}
		return getImportFilter(minOpt.get().getAttribute("value")); 
	}
	
	@Override
	public int getMaxImportFilter() {
		var maxOpt = findElement(XP_INPUT_MAXIMO);
		if (maxOpt.isEmpty()) {
			return 0;
		}
		return getImportFilter(maxOpt.get().getAttribute("value")); 
	}	
	
	@Override
	public void clickIntervalImportFilter(int margenPixelsLeft, int margenPixelsRight) {
		var minSelector = getElement(XP_INPUT_MINIMO);
		var maxSelector = getElement(XP_INPUT_MAXIMO);
		//TODO
		//...modificar el style --relative-start-position: 0; --relative-end-position: 1;
		drag(minSelector, margenPixelsLeft);
		drag(maxSelector, -margenPixelsRight);
	}

	@Override
	public boolean isVisibleColorTags(List<Color> colors) {
		return colors.stream()
			.map(this::getXPathLinkColor)
			.filter(xpath -> !state(VISIBLE, xpath).check())
			.findAny().isEmpty();
	}
	
	@Override
	public boolean isAvailableFiltros(FiltroMobil typeFiltro, List<String> listTextFiltros) {
		throw new UnsupportedOperationException();
	}
	
	private boolean isFiltersShopVisible(int seconds) {
		return state(VISIBLE, XP_CAPA_FILTERS).wait(seconds).check();
	}	
	
	private int getImportFilter(String importScreen) {
		return (int)(ImporteScreen.getFloatFromImporteMangoScreen(importScreen));		
	}	

}