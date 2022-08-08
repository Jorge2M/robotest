package com.mng.robotest.test.pageobject.shop.filtros;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.mng.robotest.conftestmaker.AppEcom;
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
	
	public static SecMultiFiltrosDevice getInstance(AppEcom app) {
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.mobile, app);
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
		return (state(Present, By.xpath(xpath), driver).check());
	}
	
	/** 
	 * Seleccionamos una ordenación ascendente/descendente
	 */
	@Override
	public int selecOrdenacionAndReturnNumArticles(FilterOrdenacion typeOrden) throws Exception {
		selectOrdenacion(typeOrden);
		int maxSecondsToWait = 10;
		int numArticles = pageGaleria.waitForArticleVisibleAndGetNumberOfThem(maxSecondsToWait);
		return numArticles;
	}

	/** 
	 * Seleccionamos un filtro de color
	 * @param codigoColor código asociado al color, p.e. el 01 es el código asociado al color Blanco
	 * @return el número de artículos que aparecen en la galería después de seleccionar el filtro
	 */
	@Override
	public int selecFiltroColoresAndReturnNumArticles(List<Color> colorsToFilter) {
		selectFiltrosAndWaitLoad(FiltroMobil.Colores, Color.getListNamesFiltros(colorsToFilter));
		int maxSecondsToWait = 10;
		int numArticles = pageGaleria.waitForArticleVisibleAndGetNumberOfThem(maxSecondsToWait);
		return numArticles;
	}
	
	@Override
	public boolean isClickableFiltroUntil(int seconds) {
		return (state(Clickable, By.xpath(XPATH_FILTRAR_Y_ORDENAR_BUTTON)).wait(seconds).check());
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
		WebElement filtroLinea = driver.findElement(By.xpath(typeFiltro.getXPathLineaFiltro()));
		filtroLinea.click();
		waitForPageLoaded(driver);
		By byFiltroOption = By.xpath(getXPathFiltroOption(typeFiltro, textFiltro));
		state(Clickable, byFiltroOption, driver).wait(1).check();
		filtroLinea.findElement(byFiltroOption).click();
		waitForPageLoaded(driver);
	}
	private String getXPathFiltroOption(FiltroMobil typeFiltro, String textFiltro) {
		if (typeFiltro==FiltroMobil.Familia) {
			return(
				".//*[@name[contains(.,'" + textFiltro + "')] or " + 
				"@name[contains(.,'" + upperCaseFirst(textFiltro) + "')]]/../span");
		} else {
			return (
				".//*[text()[contains(.,'" + textFiltro + "')] or " + 
				"text()[contains(.,'" + upperCaseFirst(textFiltro) + "')]]");
		}
	}
	
	private void clickApplicarFiltrosButton() {
		click(By.xpath(XPATH_BUTTON_APLICAR_FILTROS)).exec();
	}
	
	private void goAndClickFiltroButton() {
		if (state(Visible, By.xpath(XPATH_FILTRAR_Y_ORDENAR_BUTTON), driver).check()) {
			moveToElement(By.xpath(XPATH_FILTRAR_Y_ORDENAR_BUTTON), driver);
			waitMillis(500);
			
			//Scrollamos un poquito hacia arriba para asegurar
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-50)", "");
		}
		
		waitAndClickFiltroButton(2);
	}
	
	private void waitAndClickFiltroButton(int maxSeconds) {
		if (!isOpenFiltrosUntil(0)) {
			state(Clickable, By.xpath(XPATH_FILTRAR_Y_ORDENAR_BUTTON), driver).wait(maxSeconds).check();
			click(By.xpath(XPATH_FILTRAR_Y_ORDENAR_BUTTON)).type(javascript).exec();
			isOpenFiltrosUntil(maxSeconds);
		}		
	}
	
	private boolean isOpenFiltrosUntil(int maxSeconds) {
		String xpathLineaOrdenar = FiltroMobil.Ordenar.getXPathLineaFiltro();
		return (state(Visible, By.xpath(xpathLineaOrdenar), driver).wait(maxSeconds).check());
	}
	
	private String upperCaseFirst(String val) {
		char[] arr = val.toCharArray();
		arr[0] = Character.toUpperCase(arr[0]);
		return new String(arr);
	}
}
