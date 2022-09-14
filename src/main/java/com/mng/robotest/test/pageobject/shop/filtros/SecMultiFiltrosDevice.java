package com.mng.robotest.test.pageobject.shop.filtros;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.data.Color;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecMultiFiltrosDevice extends PageBase implements SecFiltros {
	
	private static final String XPATH_FILTRAR_Y_ORDENAR_BUTTON = "//button[@class[contains(.,'-filters-btn')]]";
	private static final String XPATH_BUTTON_APLICAR_FILTROS = "//button[@class[contains(.,'filters-apply')]]";
	
	PageGaleria pageGaleria = null;
	
	private SecMultiFiltrosDevice(PageGaleria pageGaleria) {
		this.pageGaleria = pageGaleria;
	}
	
	public static SecMultiFiltrosDevice getInstance() {
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.mobile);
		return (new SecMultiFiltrosDevice(pageGaleria));
	}
	
	public static SecMultiFiltrosDevice getInstance(PageGaleria pageGaleria) {
		return (new SecMultiFiltrosDevice(pageGaleria));
	}
	
	@Override
	public void selectOrdenacion(FilterOrdenacion ordenacion) throws Exception {
		selectFiltroAndWaitLoad(FiltroMobil.Ordenar, ordenacion.getValueForMobil());
	}
	
	@Override
	public void selectCollection(FilterCollection collection) {
		selectFiltroAndWaitLoad(FiltroMobil.Coleccion, collection.getValueMobil());
	}
	
	@Override
	public boolean isCollectionFilterPresent() throws Exception {
		String xpath = FiltroMobil.Coleccion.getXPathLineaFiltro();
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
		selectFiltrosAndWaitLoad(FiltroMobil.Colores, Color.getListNamesFiltros(colorsToFilter));
		return pageGaleria.waitForArticleVisibleAndGetNumberOfThem(10);
	}
	
	@Override
	public boolean isClickableFiltroUntil(int seconds) {
		return state(Clickable, XPATH_FILTRAR_Y_ORDENAR_BUTTON).wait(seconds).check();
	}	
	
	@Override
	public void selectMenu2onLevel(List<String> listMenus) {
		selectFiltrosAndWaitLoad(FiltroMobil.Familia, listMenus);
	}
	
	/**
	 * Selecciona un determinado filtro de la galería de móvil
	 * @param valor atributo 'value' a nivel de la option del filtro (select)
	 */
	private void selectFiltroAndWaitLoad(FiltroMobil typeFiltro, String textFiltro) {
		List<String> listTextFiltros = Arrays.asList(textFiltro);
		selectFiltrosAndWaitLoad(typeFiltro, listTextFiltros);
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
		WebElement filtroLinea = getElement(typeFiltro.getXPathLineaFiltro());
		filtroLinea.click();
		waitLoadPage();
		By byFiltroOption = By.xpath(getXPathFiltroOption(typeFiltro, textFiltro));
		state(Clickable, byFiltroOption).wait(1).check();
		filtroLinea.findElement(byFiltroOption).click();
		waitLoadPage();
	}
	private String getXPathFiltroOption(FiltroMobil typeFiltro, String textFiltro) {
		String labelFiltro = stripAccents(textFiltro);
		if (typeFiltro==FiltroMobil.Familia) {
			return(
				".//*[@name[contains(.,'" + labelFiltro + "')] or " + 
				"@name[contains(.,'" + upperCaseFirst(labelFiltro) + "')]]/../span");
		} else {
			return (
				".//*[text()[contains(.,'" + textFiltro + "')] or " + 
				"text()[contains(.,'" + upperCaseFirst(labelFiltro) + "')]]");
		}
	}
	
	public static String stripAccents(String text) {
	    String s = Normalizer.normalize(text, Normalizer.Form.NFD);
	    return s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	}
	
	private void clickApplicarFiltrosButton() {
		click(XPATH_BUTTON_APLICAR_FILTROS).exec();
	}
	
	private void goAndClickFiltroButton() {
		if (state(Visible, XPATH_FILTRAR_Y_ORDENAR_BUTTON).check()) {
			moveToElement(XPATH_FILTRAR_Y_ORDENAR_BUTTON);
			waitMillis(500);
			
			//Scrollamos un poquito hacia arriba para asegurar
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-50)", "");
		}
		
		waitAndClickFiltroButton(2);
	}
	
	private void waitAndClickFiltroButton(int seconds) {
		if (!isOpenFiltrosUntil(0)) {
			state(Clickable, XPATH_FILTRAR_Y_ORDENAR_BUTTON).wait(seconds).check();
			click(XPATH_FILTRAR_Y_ORDENAR_BUTTON).type(javascript).exec();
			isOpenFiltrosUntil(seconds);
		}		
	}
	
	private boolean isOpenFiltrosUntil(int seconds) {
		String xpathLineaOrdenar = FiltroMobil.Ordenar.getXPathLineaFiltro();
		return state(Visible, xpathLineaOrdenar).wait(seconds).check();
	}
	
	private String upperCaseFirst(String val) {
		char[] arr = val.toCharArray();
		arr[0] = Character.toUpperCase(arr[0]);
		return new String(arr);
	}
}
