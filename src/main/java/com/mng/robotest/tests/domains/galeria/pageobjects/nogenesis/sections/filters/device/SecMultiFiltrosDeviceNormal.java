package com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.device;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.device.FiltroMobil.*;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.FilterOrdenacion;
import com.mng.robotest.testslegacy.data.Color;

public class SecMultiFiltrosDeviceNormal extends SecMultiFiltrosDevice {

	private static final String XP_FILTRAR_Y_ORDENAR_BUTTON = "//*[@data-testid='plp.filters.mobile.button']";
	public static final String XP_FILTER_PANEL = "//*[@data-testid='plp.filters.mobile.panel']";
	
	//TODO Galería Kondo (19-10-23)
	private static final String XP_BUTTON_MOSTRAR_ARTICULOS = "//button/span[text()[contains(.,'Mostrar artículos')]]";
	private static final String XP_BUTTON_CLOSE = XP_FILTER_PANEL + "//button[@aria-label='close']";

	private String getXPathFiltroOption(FiltroMobil typeFiltro, String textFiltro) {
		String textXPath = 
			"text()[contains(.,'" + textFiltro + "')] or " +
			"text()[contains(.,'" + upperCaseFirst(textFiltro) + "')]";

		return typeFiltro.getXPathOptionNormal() + "//self::*[" + textXPath+ "]";
	}
	
	private String getXPathFiltroTag(FiltroMobil typeFiltro, String name) {
		return typeFiltro.getXPathNormal() + "/../div[text()='" + name + "']"; 
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
		return state(CLICKABLE, XP_FILTRAR_Y_ORDENAR_BUTTON).wait(seconds).check();
	}	

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
	
	@Override
	public boolean isAvailableFiltros(FiltroMobil typeFiltro, List<String> listTextFiltros) {
		if (!goAndClickFiltroButton()) {
			return false;
		}
		getElement(typeFiltro.getXPathNormal()).click();
		for (String textFiltro : listTextFiltros) {
			if (!state(VISIBLE, getXPathFiltroOption(typeFiltro, textFiltro)).wait(1).check()) {
				close();
				return false;
			}
		}
		close();
		return true;
	}
	
	public boolean isVisibleColorTags(List<Color> colors) {
		return colors.stream()
			.map(color -> getXPathFiltroTag(FiltroMobil.COLORES, color.getNameFiltro()))
			.filter(xpath -> !state(PRESENT, xpath).check())
			.findAny().isEmpty();
	}

	private void selectFiltrosAndWaitLoad(FiltroMobil typeFiltro, List<String> listTextFiltros) {
		goAndClickFiltroButton();
		state(VISIBLE, typeFiltro.getXPathNormal()).wait(2).check();
		getElement(typeFiltro.getXPathNormal()).click();
		waitLoadPage();
		for (String textFiltro : listTextFiltros) {
			clickFiltroOption(typeFiltro, textFiltro);
		}
		clickMostrarArticulosButton();
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
		var filtroLinea = getElement(typeFiltro.getXPathNormal());
		By byFiltroOption = By.xpath(getXPathFiltroOption(typeFiltro, textFiltro));
		state(CLICKABLE, byFiltroOption).wait(2).check();
		click(filtroLinea).by(byFiltroOption).exec();
		waitLoadPage();
	}

	private void clickMostrarArticulosButton() {
		click(XP_BUTTON_MOSTRAR_ARTICULOS).exec();
	}
	
	private boolean goAndClickFiltroButton() {
		if (!isOpenFiltrosUntil(0)) {
			if (state(CLICKABLE, XP_FILTRAR_Y_ORDENAR_BUTTON).wait(2).check()) {
				click(XP_FILTRAR_Y_ORDENAR_BUTTON).exec();
				return isOpenFiltrosUntil(3);
			}
		}		
		return false;
	}
	
	private boolean isOpenFiltrosUntil(int seconds) {
		return state(VISIBLE, ORDENAR.getXPathNormal()).wait(seconds).check();
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
		click(XP_BUTTON_CLOSE).exec();
		isCloseFiltrosUntil(1);
	}
	
	private String upperCaseFirst(String val) {
		char[] arr = val.toCharArray();
		arr[0] = Character.toUpperCase(arr[0]);
		return new String(arr);
	}
	
}
