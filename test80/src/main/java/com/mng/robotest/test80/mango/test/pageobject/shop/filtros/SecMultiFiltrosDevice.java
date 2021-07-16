package com.mng.robotest.test80.mango.test.pageobject.shop.filtros;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Color;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;


/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección correspondiente a los "Filtros"
 * @author jorge.munoz
 *
 */
public class SecMultiFiltrosDevice extends PageObjTM implements SecFiltros {
    
	private final static String XPathFiltrarYOrdenarButton = "//button[@class[contains(.,'-filters-btn')]]";
	private final static String XPathButtonAplicarFiltros = "//button[@class[contains(.,'filters-apply')]]";
	
	PageGaleria pageGaleria = null;
	
	private SecMultiFiltrosDevice(WebDriver driver, PageGaleria pageGaleria) {
		super(driver);
		this.pageGaleria = pageGaleria;
	}
	
	public static SecMultiFiltrosDevice getInstance(AppEcom app, WebDriver driver) {
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.mobile, app, driver);
		return (new SecMultiFiltrosDevice(driver, pageGaleria));
	}
	
	public static SecMultiFiltrosDevice getInstance(WebDriver driver, PageGaleria pageGaleria) {
		return (new SecMultiFiltrosDevice(driver, pageGaleria));
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
    	return (state(Clickable, By.xpath(XPathFiltrarYOrdenarButton)).wait(seconds).check());
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
        By byFiltroOption = By.xpath(
        		".//*[@name[contains(.,'" + textFiltro + "')] or " + 
        			 "@name[contains(.,'" + upperCaseFirst(textFiltro) + "')]]/../span");
        state(Clickable, byFiltroOption, driver).wait(1).check();
        filtroLinea.findElement(byFiltroOption).click();
        waitForPageLoaded(driver);
    }
    
    private void clickApplicarFiltrosButton() {
    	click(By.xpath(XPathButtonAplicarFiltros)).exec();
    }
    
    private void goAndClickFiltroButton() {
    	if (state(Visible, By.xpath(XPathFiltrarYOrdenarButton), driver).check()) {
        	moveToElement(By.xpath(XPathFiltrarYOrdenarButton), driver);
            waitMillis(500);
            
            //Scrollamos un poquito hacia arriba para asegurar
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-50)", "");
        }
        
        waitAndClickFiltroButton(2);
    }
    
    private void waitAndClickFiltroButton(int maxSeconds) {
        if (!isOpenFiltrosUntil(0)) {
        	state(Clickable, By.xpath(XPathFiltrarYOrdenarButton), driver).wait(maxSeconds).check();
        	click(By.xpath(XPathFiltrarYOrdenarButton)).type(javascript).exec();
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
