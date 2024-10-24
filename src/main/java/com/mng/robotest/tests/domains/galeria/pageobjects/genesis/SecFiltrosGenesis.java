package com.mng.robotest.tests.domains.galeria.pageobjects.genesis;

import java.util.List;
import java.util.regex.Pattern;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.SecFiltros;
import com.mng.robotest.tests.domains.galeria.pageobjects.entity.FilterOrdenacion;
import com.mng.robotest.testslegacy.data.Color;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.genesis.SecFiltrosGenesis.TypeFiltro.*;

public class SecFiltrosGenesis extends PageBase implements SecFiltros {
	
	public enum TypeFiltro {
		ORDENAR("plp.filters.mobile.panel.order"), 
		FAMILIA("plp.filters.mobile.panel.generic"), 
		COLORES("plp.filters.mobile.panel.colorGroups"), 
		TALLAS("plp.filters.mobile.panel.sizes"),
		PRECIOS("plp.filters.mobile.panel.price");
		
		String dataTestId;
		private TypeFiltro(String dataTestId) {
			this.dataTestId = dataTestId;
		}
		
		public String getXPathPanelMobil() {
			return "//*[@data-testid='" + dataTestId + "']";
		}
	}

	private static final String XP_BUTTON_FILTRAR = "//*["
			+ "@data-testid='plp.filters.desktop.button' or " //Old
			+ "@data-testid='plp.filters.mobile.button' or " //Old
			+ "@data-testid='productList.filters.small.button' or " //New
			+ "@data-testid='productList.filters.large.button' or " //New
			+ "@data-testid='productList.filters.button']"; //New menu

	private static final String XP_LABEL_FILTRO = "//*[@data-testid[contains(.,'plp.filters.mobile.panel.')]]//div[@class[contains(.,'Element_subtitle')]]";
	private static final String XP_WRAPPER = "//*[@data-testid='plp.filters.mobile.panel']";
	
	private static final String XP_CAPA_FILTERS = "//*["
			+ "@data-testid='plp.filters.desktop.panel' or "
			+ "@data-testid='plp.filters.mobile.panel']"; //New menus
	
	private static final String XP_BUTTON_MOSTRAR_ARTICULOS = "//*[@data-testid='plp.filters.apply.button']"; //
	
	private static final String XP_INPUT_MINIMO = "//input[@step='1']";
	private static final String XP_INPUT_MAXIMO = "" + XP_INPUT_MINIMO + "[2]";
	
	private static final String XP_ITEM_COLOR = "//input[@id[contains(.,'colorGroups')]]";
	private static final String XP_ITEM_ORDENACION = "//*[@data-testid[contains(.,'plp.filters.mobile.panel.order')]]";
	private static final String XP_ITEM_FAMILIA = "//*[@data-testid[contains(.,'plp.filters.mobile.panel.generic-generic-subfamilies')]]";
	
//	private static final String XP_CLOSE_DESKTOP = "//*[@data-testid='plp.filters.desktop.panel.close']";
	private static final String XP_CLOSE_MOBIL = "//*[@data-testid='modal.close.button']";
	
	private String getXPathClose() {
		return XP_CLOSE_MOBIL;
	}
	
	private String getXPathLinkOrdenacion(FilterOrdenacion ordenacion) {
		return XP_ITEM_ORDENACION + "//self::*[@data-testid[contains(.,-" + ordenacion.getValue() + "]]/..";
	}
	
	private String getXPathLinkColor(Color color) {
		return XP_ITEM_COLOR + "//self::*[@aria-label[contains(.,'" + color.getNameFiltro() + "')]]";
	}
	
	private String getXPathLinkFamily(String family) {
		String familyFirstCapital = family.substring(0, 1).toUpperCase() + family.substring(1);
		return XP_ITEM_FAMILIA + "/../span["
				+ "text()[contains(.,'" + family + "')] or "
				+ "text()[contains(.,'" + familyFirstCapital + "')]]";
	}
	
	@Override
	public void selectOrdenacion(FilterOrdenacion ordenacion) {
		showFilters();
		showPanelFiltroMobil(ORDENAR);
		String xpathLink = getXPathLinkOrdenacion(ordenacion);
		click(xpathLink).exec();
		acceptFilters();
	}
	
	@Override
	public void selecFiltroColores(List<Color> colorsToSelect) {
		showFilters();
		showPanelFiltroMobil(COLORES);
		for (Color color : colorsToSelect) {
			String xpathLinkColor = getXPathLinkColor(color);
			moveToElement(xpathLinkColor);
			click(xpathLinkColor).exec();
		}
		acceptFilters();
	}
	
	@Override
	public void selectMenu2onLevelDevice(String menuLabel) {
		openSubfamilyFilter();
		click(getXPathLinkFamily(menuLabel)).exec();
		acceptFilters();
	}
	
	@Override
	public void selectMenu2onLevelDevice(List<String> listMenus) {
		showFilters();
		showPanelFiltroMobil(FAMILIA);
		for (var menu : listMenus) {
			click(getXPathLinkFamily(menu)).exec();
		}
		acceptFilters();
	}

	@Override
	public void selectIntervalImport(int minim, int maxim) {
		//It has not been possible to select the filters from the browser
		driver.get(getCurrentUrl() + "?range=" + minim + "-" + maxim);
	}	
	
	private void showPanelFiltroMobil(TypeFiltro typeFiltro) {
		click(typeFiltro.getXPathPanelMobil()).waitLink(1).exec();
	}
	
	@Override
	public boolean isClickableFiltroUntil(int seconds) {
		String xpathFilterDesc = getXPathLinkOrdenacion(FilterOrdenacion.PRECIO_DESC);
		return state(CLICKABLE, xpathFilterDesc).wait(seconds).check();
	}
	
	public void bring(BringTo bringTo) {
		bringElement(getElement(XP_WRAPPER), bringTo);
	}
	
	@Override
	public void showFilters() {
		if (!isFiltersShopVisible(1) &&
			state(CLICKABLE, XP_BUTTON_FILTRAR).check()) {
			clickFilterAndSortButton();
			isFiltersShopVisible(1);
		}
	}
	
	@Override
	public void clickFilterAndSortButton() {
		click(XP_BUTTON_FILTRAR).exec();
	}
	
	private void hideFilters() {
		close();
	}
	
	@Override
	public void acceptFilters() {
		click(XP_BUTTON_MOSTRAR_ARTICULOS).exec();
		waitMillis(1000);
		waitForPageLoaded(driver);
		PageGaleria pageGaleria = PageGaleria.make(channel);
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
	public void close() {
		if (state(PRESENT, getXPathClose()).check()) {
			click(getXPathClose()).exec();
		}
	}
	
	@Override
	public boolean isVisibleLabelFiltroPrecioApplied(int minim, int maxim) {
		Pattern pattern = Pattern.compile(
				".*\\d{1,3}(\\.\\d{3})*,00 €.*\\d{1,3}(\\.\\d{3})*,00 €.*");
		return isVisibleLabelFiltroApplied(pattern);
	}
	
	@Override
	public boolean isVisibleLabelFiltroColorApplied(List<Color> colorsSelected) {
		for (var color : colorsSelected) {
			if (!isVisibleLabelFiltroApplied(color.getNameFiltro())) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean isAvailableFiltrosFamilia(List<String> submenus) {
		openSubfamilyFilter();
		for (var submenu : submenus) {
			if (!state(VISIBLE, getXPathLinkFamily(submenu)).check()) {
				return false;
			}
		}
		if (isDevice()) {
			close();
		}
		return true;
	}
	
	private void openSubfamilyFilter() {
		showFilters();
		showPanelFiltroMobil(FAMILIA);
	}
	
	private boolean isVisibleLabelFiltroApplied(String labelExpected) {
		return isVisibleLabelFiltroApplied(Pattern.compile(labelExpected));
	}
	
	private boolean isVisibleLabelFiltroApplied(Pattern labelExpected) {
		showFilters();
		var labelsFiltro = getElements(XP_LABEL_FILTRO);
		if (labelsFiltro.isEmpty()) {
			return false;
		}
		boolean found = false;
		for (var labelFiltro : labelsFiltro) {
			String filtroLabel = labelFiltro.getText();
			var matcher = labelExpected.matcher(labelFiltro.getText());
			if (filtroLabel.contains(labelExpected.toString()) || matcher.matches()) {
				found = true;
				break;
			}
		}
		close();
		return found;
	}

	private boolean isFiltersShopVisible(int seconds) {
		return state(VISIBLE, XP_CAPA_FILTERS).wait(seconds).check();
	}	
	
	private int getImportFilter(String importScreen) {
		return (int)(ImporteScreen.getFloatFromImporteMangoScreen(importScreen));		
	}	

}
