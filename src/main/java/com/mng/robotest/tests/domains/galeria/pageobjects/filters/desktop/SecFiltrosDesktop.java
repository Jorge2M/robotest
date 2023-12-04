package com.mng.robotest.tests.domains.galeria.pageobjects.filters.desktop;

import java.util.List;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.FilterOrdenacion;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.SecFiltros;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.Color;
import com.mng.robotest.testslegacy.pageobject.shop.menus.desktop.SecMenuLateralDesktop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public abstract class SecFiltrosDesktop extends PageBase implements SecFiltros {
	
	private final SecSelectorPreciosDesktop secSelectorPreciosDesktop = SecSelectorPreciosDesktop.make(dataTest.getPais(), app);
	
	abstract String getXPathButtonFiltrar();
	abstract String getXPathWrapper();
	abstract String getXPathLinkOrdenacion(FilterOrdenacion ordenacion);
	abstract String getXPathLinkColor(Color color);
	abstract String getXPathLinkCollection();
	abstract String getXPathCapaFilters();
	abstract String getXPathMostrarArticulos();
	abstract String getXPathLabel(String label);
	
	public static SecFiltrosDesktop make(AppEcom app, Pais pais) {
		if (pais.isGaleriaKondo(app)) {
			return new SecFiltrosDesktopKondo();
		}
		return new SecFiltrosDesktopNormal();
	}
	
	private void selectOrdenacion(FilterOrdenacion ordenacion) {
		String xpathLink = getXPathLinkOrdenacion(ordenacion);
		click(xpathLink).exec();
	}
	
	@Override
	public void selecOrdenacion(FilterOrdenacion typeOrden) throws Exception {
		selectOrdenacion(typeOrden);
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
		//TODO
	}
	@Override
	public void selectMenu2onLevel(String menuLabel) {
		//TODO
	}	
	
	public void bring(BringTo bringTo) {
		String xpathWrapper = getXPathWrapper(); 
		bringElement(getElement(xpathWrapper), bringTo);
	}
	
	public void showLateralMenus() {
		if (!new SecMenuLateralDesktop().isVisibleCapaMenus(1)) {
			click(getXPathLinkCollection()).exec();
		}
	}
	
	public void showFilters() {
		if (!isFiltersShopVisible(1) &&
			state(CLICKABLE, getXPathButtonFiltrar()).check()) {
			click(getXPathButtonFiltrar()).exec();
		}
	}
	public void hideFilters() {
		if (isFiltersShopVisible(1) &&
			state(CLICKABLE, getXPathButtonFiltrar()).check()) {
			click(getXPathButtonFiltrar()).exec();
		}
	}
	
	public void acceptFilters() {
		click(getXPathMostrarArticulos()).exec();
		waitMillis(1000);
		waitForPageLoaded(driver);
		var pageGaleria = PageGaleria.make(channel, app, dataTest.getPais());
		pageGaleria.isVisibleImageArticle(1, 2);
	}
	
	public boolean isVisibleSelectorPrecios() {
		showFilters();
		boolean visible = secSelectorPreciosDesktop.isVisible();
		hideFilters();
		return visible;
	}
	
	public int getMinImportFilter() {
		return secSelectorPreciosDesktop.getMinImport(); 
	}
	public int getMaxImportFilter() {
		return secSelectorPreciosDesktop.getMaxImport(); 
	}	
	public void clickIntervalImportFilter(int margenPixelsLeft, int margenPixelsRight) {
		secSelectorPreciosDesktop.clickMinAndMax(margenPixelsLeft, margenPixelsRight);
	}

	public boolean isVisibleLabel(String label) {
		return state(VISIBLE, getXPathLabel(label)).check();
	}	
	
	private boolean isFiltersShopVisible(int seconds) {
		return state(VISIBLE, getXPathCapaFilters()).wait(seconds).check();
	}
	
}
