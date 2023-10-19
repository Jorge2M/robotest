package com.mng.robotest.tests.domains.galeria.pageobjects.filters.device;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Clickable;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;
import static com.mng.robotest.tests.domains.galeria.pageobjects.filters.device.FiltroMobil.*;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import com.mng.robotest.tests.domains.galeria.pageobjects.filters.FilterOrdenacion;
import com.mng.robotest.testslegacy.data.Color;

public class SecMultiFiltrosDeviceKondo extends SecMultiFiltrosDevice {

	//
	private static final String XPATH_FILTRAR_Y_ORDENAR_BUTTON = "//*[@data-testid='plp.filters.mobile.button']";
	
	//
	public static final String XPATH_FILTER_PANEL = "//*[@data-testid='plp.filters.mobile.panel']";
	
	//TODO Galería Kondo (19-10-23)
	private static final String XPATH_BUTTON_MOSTRAR_ARTICULOS = "//button/span[text()[contains(.,'Mostrar artículos')]]";
	
	//
	private static final String XPATH_BUTTON_CLOSE = XPATH_FILTER_PANEL + "//button[@aria-label='close']";

	//
	private String getXPathFiltroOption(FiltroMobil typeFiltro, String textFiltro) {
		String textXPath = 
			"text()[contains(.,'" + textFiltro + "')] or " +
			"text()[contains(.,'" + upperCaseFirst(textFiltro) + "')]";

		return typeFiltro.getXPathOptionKondo() + "//self::*[" + textXPath+ "]";
	}
	
	@Override
	public void selecOrdenacion(FilterOrdenacion typeOrden) throws Exception {
		selectFiltroAndWaitLoad(ORDENAR, typeOrden.getValue());
	}

	@Override
	public void selecFiltroColores(List<Color> colorsToFilter) {
		selectFiltrosAndWaitLoad(COLORES, Color.getListNamesFiltros(colorsToFilter));
	}
	
	@Override
	public boolean isClickableFiltroUntil(int seconds) {
		return state(Clickable, XPATH_FILTRAR_Y_ORDENAR_BUTTON).wait(seconds).check();
	}	
	//..
	@Override
	public void selectMenu2onLevel(List<String> listMenus) {
		selectFiltrosAndWaitLoad(FAMILIA, listMenus);
	}
	@Override
	public void selectMenu2onLevel(String menuLabel) {
		selectFiltrosAndWaitLoad(FAMILIA, Arrays.asList(menuLabel));
	}
	
	private void selectFiltroAndWaitLoad(FiltroMobil typeFiltro, String textFiltro) {
		var listTextFiltros = Arrays.asList(textFiltro);
		selectFiltrosAndWaitLoad(typeFiltro, listTextFiltros);
	}
	
	//
	@Override
	public boolean isAvailableFiltros(FiltroMobil typeFiltro, List<String> listTextFiltros) {
		if (!goAndClickFiltroButton()) {
			return false;
		}
		getElement(typeFiltro.getXPathKondo()).click();
		for (String textFiltro : listTextFiltros) {
			if (!state(Visible, getXPathFiltroOption(typeFiltro, textFiltro)).check()) {
				close();
				return false;
			}
		}
		close();
		return true;
	}
	//
	private void selectFiltrosAndWaitLoad(FiltroMobil typeFiltro, List<String> listTextFiltros) {
		goAndClickFiltroButton();
		for (String textFiltro : listTextFiltros) {
			clickFiltroOption(typeFiltro, textFiltro);
		}
		clickMostrarArticulosButton();
	}
	//
	private void clickFiltroOption(FiltroMobil typeFiltro, String textFiltro) {
		try {
			clickFiltroOptionStaleNotSafe(typeFiltro, textFiltro);
		}
		catch (StaleElementReferenceException e) {
			waitMillis(500);
			clickFiltroOptionStaleNotSafe(typeFiltro, textFiltro);
		}
	}
	//
	private void clickFiltroOptionStaleNotSafe(FiltroMobil typeFiltro, String textFiltro) {
		var filtroLinea = getElement(typeFiltro.getXPathKondo());
		filtroLinea.click();
		waitLoadPage();
		By byFiltroOption = By.xpath(getXPathFiltroOption(typeFiltro, textFiltro));
		click(filtroLinea).by(byFiltroOption).waitLink(1).exec();
		waitLoadPage();
	}
	//
	private void clickMostrarArticulosButton() {
		click(XPATH_BUTTON_MOSTRAR_ARTICULOS).exec();
	}
	
	//
	private boolean goAndClickFiltroButton() {
		if (!isOpenFiltrosUntil(0)) {
			if (state(Clickable, XPATH_FILTRAR_Y_ORDENAR_BUTTON).wait(2).check()) {
				click(XPATH_FILTRAR_Y_ORDENAR_BUTTON).exec();
				return isOpenFiltrosUntil(3);
			}
		}		
		return false;
	}
	
	//
	private boolean isOpenFiltrosUntil(int seconds) {
		return state(Visible, ORDENAR.getXPathKondo()).wait(seconds).check();
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
	
	//
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
