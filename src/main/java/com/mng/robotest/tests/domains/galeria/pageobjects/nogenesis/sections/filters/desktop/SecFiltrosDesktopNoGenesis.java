package com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.desktop;

import java.util.List;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.SecFiltros;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.FilterOrdenacion;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.mobil.FiltroMobil;
import com.mng.robotest.testslegacy.data.Color;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecFiltrosDesktopNoGenesis extends PageBase implements SecFiltros {

	private final SecSelectorPreciosDesktopNoGenesis secSelectorPreciosDesktop = new SecSelectorPreciosDesktopNoGenesis();
	
	private static final String XP_WRAPPER = "//div[@id='catalogMenu']";
	private static final String XP_BUTTON_FILTRAR = "//button[@data-testid='plp.filters.desktop.button']";
	private static final String XP_CAPA_FILTERS = "//*[@data-testid='plp.filters.desktop.panel']";
	private static final String XP_BUTTON_MOSTRAR_ARTICULOS = XP_CAPA_FILTERS + "/div[2]/div[3]/button";	

	private String getXPathLinkOrdenacion(FilterOrdenacion ordenacion) {
		return 
			"//div[@id='generic-order']" + 
			"//input[@id[contains(.,'-order_" + ordenacion.getValue() + "')]]";
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
	
	/** 
	 * @return el número de artículos que aparecen en la galería después de seleccionar el filtro
	 */
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
		boolean visible = secSelectorPreciosDesktop.isVisible();
		hideFilters();
		return visible;
	}
	
	@Override
	public int getMinImportFilter() {
		return secSelectorPreciosDesktop.getMinImport(); 
	}
	
	@Override
	public int getMaxImportFilter() {
		return secSelectorPreciosDesktop.getMaxImport(); 
	}	
	
	@Override
	public void clickIntervalImportFilter(int margenPixelsLeft, int margenPixelsRight) {
		secSelectorPreciosDesktop.clickMinAndMax(margenPixelsLeft, margenPixelsRight);
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

}
