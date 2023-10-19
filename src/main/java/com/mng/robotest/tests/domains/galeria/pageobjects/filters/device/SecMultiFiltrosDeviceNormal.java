package com.mng.robotest.tests.domains.galeria.pageobjects.filters.device;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Clickable;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.javascript;
import static com.mng.robotest.tests.domains.galeria.pageobjects.filters.device.FiltroMobil.*;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.filters.FilterOrdenacion;
import com.mng.robotest.testslegacy.data.Color;

public class SecMultiFiltrosDeviceNormal extends SecMultiFiltrosDevice {

	private static final String XPATH_FILTRAR_Y_ORDENAR_BUTTON = "//button[@data-testid[contains(.,'filter-sort')]]";
	private static final String XPATH_BUTTON_APLICAR_FILTROS = "//button[@class[contains(.,'filters-apply')]]";
	private static final String XPATH_BUTTON_CLOSE = "//div[@class='orders-filters-close' and @role='button']";
	
	/** 
	 * Seleccionamos una ordenación ascendente/descendente
	 */
	@Override
	public void selecOrdenacion(FilterOrdenacion typeOrden) throws Exception {
		selectFiltroAndWaitLoad(ORDENAR, typeOrden.getValue());
	}

	/** 
	 * Seleccionamos un filtro de color
	 * @param codigoColor código asociado al color, p.e. el 01 es el código asociado al color Blanco
	 * @return el número de artículos que aparecen en la galería después de seleccionar el filtro
	 */
	@Override
	public void selecFiltroColores(List<Color> colorsToFilter) {
		selectFiltrosAndWaitLoad(COLORES, Color.getListNamesFiltros(colorsToFilter));
	}
	
	@Override
	public boolean isClickableFiltroUntil(int seconds) {
		return state(Clickable, XPATH_FILTRAR_Y_ORDENAR_BUTTON).wait(seconds).check();
	}	
	
	@Override
	public void selectMenu2onLevel(List<String> listMenus) {
		selectFiltrosAndWaitLoad(FAMILIA, listMenus);
	}
	@Override
	public void selectMenu2onLevel(String menuLabel) {
		selectFiltrosAndWaitLoad(FAMILIA, Arrays.asList(menuLabel));
	}
	
	/**
	 * Selecciona un determinado filtro de la galería de móvil
	 * @param valor atributo 'value' a nivel de la option del filtro (select)
	 */
	private void selectFiltroAndWaitLoad(FiltroMobil typeFiltro, String textFiltro) {
		var listTextFiltros = Arrays.asList(textFiltro);
		selectFiltrosAndWaitLoad(typeFiltro, listTextFiltros);
	}
	
	@Override
	public boolean isAvailableFiltros(FiltroMobil typeFiltro, List<String> listTextFiltros) {
		if (!goAndClickFiltroButton()) {
			return false;
		}
		var filtroLinea = getElement(typeFiltro.getXPathNormal());
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
		WebElement filtroLinea = getElement(typeFiltro.getXPathNormal());
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
		String xpathLineaOrdenar = ORDENAR.getXPathNormal();
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
