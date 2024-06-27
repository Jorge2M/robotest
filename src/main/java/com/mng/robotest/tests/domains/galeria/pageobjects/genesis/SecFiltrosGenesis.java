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

public class SecFiltrosGenesis extends PageBase implements SecFiltros {

	private static final String XP_BUTTON_FILTRAR_DESKTOP = "//*[@data-testid='plp.filters.desktop.button']";
	private static final String XP_BUTTON_FILTRAR_MOBIL = "//*[@data-testid='plp.filters.mobile.button']";
	private static final String XP_PANEL_FILTRO_MOBIL = "//*[@data-testid='plp.filters.mobile.panel']";
	private static final String XP_LABEL_FILTRO_DESKTOP = XP_BUTTON_FILTRAR_DESKTOP + "//following-sibling::ul/li/span";
	private static final String XP_LABEL_FILTRO_MOBIL = XP_PANEL_FILTRO_MOBIL + "//div[@class[contains(.,'Element_subtitle')]]";
	
	private static final String XP_WRAPPER_DESKTOP = XP_BUTTON_FILTRAR_DESKTOP + "/../..";
	private static final String XP_WRAPPER_MOBIL = "//*[@data-testid='plp.filters.mobile.panel']";
	
	private static final String XP_CAPA_FILTERS = "//*[@data-testid='plp.filters.desktop.panel']"; //
	private static final String XP_BUTTON_MOSTRAR_ARTICULOS = "//*[@data-testid='plp.filters.apply.button']"; //
	
	private static final String XP_INPUT_MINIMO = "//input[@step='1']";
	private static final String XP_INPUT_MAXIMO = "" + XP_INPUT_MINIMO + "[2]";
	private static final String XP_INPUT_COLOR = "//input[@id[contains(.,'colorGroups')]]";
	
	private static final String XP_CLOSE_DESKTOP = "//*[@data-testid='plp.filters.desktop.panel.close']";
	private static final String XP_CLOSE_MOBIL = "//*[@data-testid='modal.close.button']";

	private String getXPathWrapper() {
		if (isDevice()) {
			return XP_WRAPPER_MOBIL;
		}
		return XP_WRAPPER_DESKTOP;
	}
	
	private String getXPathButtonFiltrar() {
		if (isDevice()) {
			return XP_BUTTON_FILTRAR_MOBIL;
		}
		return XP_BUTTON_FILTRAR_DESKTOP;
	}
	
	private String getXPathLabelFiltro() {
		if (isDevice()) {
			return XP_LABEL_FILTRO_MOBIL;
		}
		return XP_LABEL_FILTRO_DESKTOP;
	}
	
	private String getXPathClose() {
		if (isDevice()) {
			return XP_CLOSE_MOBIL;
		}
		return XP_CLOSE_DESKTOP;
	}
	
	private String getXPathLinkOrdenacion(FilterOrdenacion ordenacion) {
		return "//*[@data-testid='plp.filters.mobile.panel.order-']" + ordenacion.getValue() + "/..";
	}
	
	private String getXPathLinkColor(Color color) {
		return XP_INPUT_COLOR + "//self::*[@aria-label[contains(.,'" + color.getNameFiltro() + "')]]";
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
		bringElement(getElement(getXPathWrapper()), bringTo);
	}
	
	@Override
	public void showFilters() {
		if (!isFiltersShopVisible(1) &&
			state(CLICKABLE, getXPathButtonFiltrar()).check()) {
			clickFilterAndSortButton();
		}
	}
	
	@Override
	public void clickFilterAndSortButton() {
		click(getXPathButtonFiltrar()).exec();
	}
	
	private void hideFilters() {
		if (isFiltersShopVisible(1) &&
			state(CLICKABLE, getXPathButtonFiltrar()).check()) {
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
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void selectIntervalImport(int minim, int maxim) {
		//It has not been possible to select the filters from the browser
		driver.get(getCurrentUrl() + "?range=" + minim + "-" + maxim);
	}	

	@Override
	public void close() {
		click(getXPathClose()).exec();
	}
	
	@Override
	public boolean isVisibleLabelFiltroPrecioApplied(int minim, int maxim) {
		var labelExpected = "Desde " + minim + ",00 € hasta " + maxim + ",00 €";
		return isVisibleLabelFiltroApplied(labelExpected);
	}
	
	private boolean isVisibleLabelFiltroApplied(String labelExpected) {
		if (isDevice()) {
			showFilters();
		}
		var labelsFiltro = getElements(getXPathLabelFiltro());
		if (labelsFiltro.isEmpty()) {
			return false;
		}
		boolean found = false;
		for (var labelFiltro : labelsFiltro) {
			if (labelFiltro.getText().compareTo(labelExpected)==0) {
				found = true;
				break;
			}
		}
		if (isDevice()) {
			close();
		}
		return found;
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
