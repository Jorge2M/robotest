package com.mng.robotest.test.pageobject.shop.filtros;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.test.data.Color;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecMultiFiltrosDevice extends PageBase implements SecFiltros {
	
	private static final String XPATH_FILTRAR_Y_ORDENAR_BUTTON = "//button[@data-testid[contains(.,'filter-sort')]]";
	private static final String XPATH_BUTTON_APLICAR_FILTROS = "//button[@class[contains(.,'filters-apply')]]";
	private static final String XPATH_BUTTON_CLOSE = "//div[@class='orders-filters-close' and @role='button']";
	
	private final PageGaleria pageGaleria = PageGaleria.getNew(channel);
	
	@Override
	public void selectOrdenacion(FilterOrdenacion ordenacion) throws Exception {
		selectFiltroAndWaitLoad(FiltroMobil.ORDENAR, ordenacion.getValueForMobil());
	}
	
	@Override
	public void selectCollection(FilterCollection collection) {
		selectFiltroAndWaitLoad(FiltroMobil.COLECCION, collection.getValueMobil());
	}
	
	@Override
	public boolean isCollectionFilterPresent() throws Exception {
		String xpath = FiltroMobil.COLECCION.getXPathLineaFiltro();
		return state(Present, xpath).check();
	}
	
	/** 
	 * Seleccionamos una ordenación ascendente/descendente
	 */
	@Override
	public int selecOrdenacionAndReturnNumArticles(FilterOrdenacion typeOrden) throws Exception {
		selectOrdenacion(typeOrden);
		return pageGaleria.waitForArticleVisibleAndGetNumberOfThem(10);
	}

	/** 
	 * Seleccionamos un filtro de color
	 * @param codigoColor código asociado al color, p.e. el 01 es el código asociado al color Blanco
	 * @return el número de artículos que aparecen en la galería después de seleccionar el filtro
	 */
	@Override
	public int selecFiltroColoresAndReturnNumArticles(List<Color> colorsToFilter) {
		selectFiltrosAndWaitLoad(FiltroMobil.COLORES, Color.getListNamesFiltros(colorsToFilter));
		return pageGaleria.waitForArticleVisibleAndGetNumberOfThem(10);
	}
	
	@Override
	public boolean isClickableFiltroUntil(int seconds) {
		return state(Clickable, XPATH_FILTRAR_Y_ORDENAR_BUTTON).wait(seconds).check();
	}	
	
	@Override
	public void selectMenu2onLevel(List<String> listMenus) {
		selectFiltrosAndWaitLoad(FiltroMobil.FAMILIA, listMenus);
	}
	@Override
	public void selectMenu2onLevel(String menuLabel) {
		selectFiltrosAndWaitLoad(FiltroMobil.FAMILIA, Arrays.asList(menuLabel));
	}
	
	/**
	 * Selecciona un determinado filtro de la galería de móvil
	 * @param valor atributo 'value' a nivel de la option del filtro (select)
	 */
	private void selectFiltroAndWaitLoad(FiltroMobil typeFiltro, String textFiltro) {
		List<String> listTextFiltros = Arrays.asList(textFiltro);
		selectFiltrosAndWaitLoad(typeFiltro, listTextFiltros);
	}
	
	public boolean isAvailableFiltros(FiltroMobil typeFiltro, List<String> listTextFiltros) {
		if (!goAndClickFiltroButton()) {
			return false;
		}
		WebElement filtroLinea = getElement(typeFiltro.getXPathLineaFiltro());
		filtroLinea.click();
		waitLoadPage();
		for (String textFiltro : listTextFiltros) {
			String xpathFiltroOption = getXPathFiltroOption(textFiltro);
			if (!state(Visible, xpathFiltroOption).check()) {
				close();
				return false;
			}
		}
		close();
		return true;
	}
	
	private void selectFiltrosAndWaitLoad(FiltroMobil typeFiltro, List<String> listTextFiltros) {
		goAndClickFiltroButton();
		for (String textFiltro : listTextFiltros) {
			clickFiltroOption(typeFiltro, textFiltro);
		}
		clickApplicarFiltrosButton();
		pageGaleria.isVisibleArticuloUntil(1, 2);
	}

	private void clickFiltroOption(FiltroMobil typeFiltro, String textFiltro) {
		try {
			clickFiltroOptionStaleNotSafe(typeFiltro, textFiltro);
		}
		catch (StaleElementReferenceException e) {
			waitMillis(500);
			clickFiltroOptionStaleNotSafe(typeFiltro, textFiltro);
		}
	}
	
	private void clickFiltroOptionStaleNotSafe(FiltroMobil typeFiltro, String textFiltro) {
		WebElement filtroLinea = getElement(typeFiltro.getXPathLineaFiltro());
		filtroLinea.click();
		waitLoadPage();
		By byFiltroOption = By.xpath(getXPathFiltroOption(textFiltro));
		click(filtroLinea).by(byFiltroOption).waitLink(1).exec();
		waitLoadPage();
	}
	private String getXPathFiltroOption(String textFiltro) {
		return "//a[@class='filter-option' or @class='order-option']//span[" + 
					"text()[contains(.,'" + textFiltro + "')] or " +
					"text()[contains(.,'" + upperCaseFirst(textFiltro) + "')]]/..";
	}
	
	public static String stripAccents(String text) {
	    String s = Normalizer.normalize(text, Normalizer.Form.NFD);
	    return s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	}
	
	private void clickApplicarFiltrosButton() {
		click(XPATH_BUTTON_APLICAR_FILTROS).exec();
	}
	
	private boolean goAndClickFiltroButton() {
		if (state(Visible, XPATH_FILTRAR_Y_ORDENAR_BUTTON).check()) {
			moveToElement(XPATH_FILTRAR_Y_ORDENAR_BUTTON);
			waitMillis(500);
			scrollVertical(-50);
		}
		
		return waitAndClickFiltroButton(2);
	}
	
	private boolean waitAndClickFiltroButton(int seconds) {
		if (!isOpenFiltrosUntil(0)) {
			if (state(Clickable, XPATH_FILTRAR_Y_ORDENAR_BUTTON).wait(seconds).check()) {
				click(XPATH_FILTRAR_Y_ORDENAR_BUTTON).type(javascript).exec();
				return isOpenFiltrosUntil(seconds);
			}
		}		
		return false;
	}
	
	private boolean isOpenFiltrosUntil(int seconds) {
		String xpathLineaOrdenar = FiltroMobil.ORDENAR.getXPathLineaFiltro();
		return state(Visible, xpathLineaOrdenar).wait(seconds).check();
	}
	private boolean isCloseFiltrosUntil(int seconds) {
		for (int i=0; i<seconds; i++) {
			if (!isOpenFiltrosUntil(0)) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	
	private void close() {
		click(XPATH_BUTTON_CLOSE).exec();
		isCloseFiltrosUntil(1);
	}
	
	private String upperCaseFirst(String val) {
		char[] arr = val.toCharArray();
		arr[0] = Character.toUpperCase(arr[0]);
		return new String(arr);
	}
}
