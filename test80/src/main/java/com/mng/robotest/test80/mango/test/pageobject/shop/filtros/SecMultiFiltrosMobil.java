package com.mng.robotest.test80.mango.test.pageobject.shop.filtros;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.data.Color;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;

@SuppressWarnings("javadoc")
/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección correspondiente a los "Filtros"
 * @author jorge.munoz
 *
 */
public class SecMultiFiltrosMobil extends WebdrvWrapp implements SecFiltros {
    
	private final static String XPathFiltrarYOrdenarButton = "//button[@class[contains(.,'-filters-btn')]]";
	private final static String XPathButtonAplicarFiltros = "//button[@class[contains(.,'filters-apply')]]";
	
	WebDriver driver;
	PageGaleria pageGaleria = null;
	
	private SecMultiFiltrosMobil(WebDriver driver, PageGaleria pageGaleria) {
		this.driver = driver;
		this.pageGaleria = pageGaleria;
	}
	
	public static SecMultiFiltrosMobil getInstance(AppEcom app, WebDriver driver) throws Exception {
		PageGaleria pageGaleria = PageGaleria.getInstance(Channel.movil_web, app, driver);
		return (new SecMultiFiltrosMobil(driver, pageGaleria));
	}
	
	public static SecMultiFiltrosMobil getInstance(WebDriver driver, PageGaleria pageGaleria) {
		return (new SecMultiFiltrosMobil(driver, pageGaleria));
	}
    
    @Override
    public void selectOrdenacion(FilterOrdenacion ordenacion) throws Exception {
        selectFiltroAndWaitLoad(FiltroMobil.Ordenar, ordenacion.getValueForMobil(), driver);        
    }
    
    @Override
    public void selectCollection(FilterCollection collection) throws Exception {
        selectFiltroAndWaitLoad(FiltroMobil.Coleccion, collection.getValueMobil(), driver);        
    }
    
    @Override
    public boolean isCollectionFilterPresent() throws Exception {
    	return (WebdrvWrapp.isElementPresent(driver, By.xpath(FiltroMobil.Coleccion.getXPathLineaFiltroMulti())));
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
    public int selecFiltroColoresAndReturnNumArticles(List<Color> colorsToFilter) throws Exception {
        selectFiltrosAndWaitLoad(FiltroMobil.Colores, Color.getListNamesFiltros(colorsToFilter), driver);
        int maxSecondsToWait = 10;
        int numArticles = pageGaleria.waitForArticleVisibleAndGetNumberOfThem(maxSecondsToWait);
        return numArticles;
    }
    
    @Override
    public boolean isClickableFiltroUntil(int seconds) {
        return (isElementClickableUntil(driver, By.xpath(XPathFiltrarYOrdenarButton), seconds));
    }    
    
    /**
     * Selecciona un determinado filtro de la galería de móvil
     * @param valor atributo 'value' a nivel de la option del filtro (select)
     */
    private void selectFiltroAndWaitLoad(FiltroMobil typeFiltro, String textFiltro, WebDriver driver) 
    throws Exception {
    	List<String> listTextFiltros = Arrays.asList(textFiltro);
    	selectFiltrosAndWaitLoad(typeFiltro, listTextFiltros, driver);
    }
    
    private void selectFiltrosAndWaitLoad(FiltroMobil typeFiltro, List<String> listTextFiltros, WebDriver driver) 
    throws Exception {
        goAndClickFiltroButton(driver);
    	for (String textFiltro : listTextFiltros)
    		clickFiltroOption(typeFiltro, textFiltro, driver);
        clickApplicarFiltrosButton(driver);
        pageGaleria.isVisibleArticuloUntil(1, 2);
    }
    
    private void clickFiltroOption(FiltroMobil typeFiltro, String textFiltro, WebDriver driver) throws Exception {
        WebElement filtroLinea = driver.findElement(By.xpath(typeFiltro.getXPathLineaFiltroMulti()));
        filtroLinea.click();
        waitForPageLoaded(driver);
        By byFiltroOption = By.xpath(".//*[text()[contains(.,'" + textFiltro + "')]]");
        isElementClickableUntil(driver, byFiltroOption, 1);
        filtroLinea.findElement(byFiltroOption).click();
        waitForPageLoaded(driver);
    }
    
    private void clickApplicarFiltrosButton(WebDriver driver) throws Exception {
    	clickAndWaitLoad(driver, By.xpath(XPathButtonAplicarFiltros));
    }
    
    private void goAndClickFiltroButton(WebDriver driver) throws Exception {
        if (isElementVisible(driver, By.xpath(XPathFiltrarYOrdenarButton))) {
        	moveToElement(By.xpath(XPathFiltrarYOrdenarButton), driver);
            Thread.sleep(500);
            
            //Scrollamos un poquito hacia arriba para asegurar
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-50)", "");
        }
        
        int maxSecondsWait = 2;
        waitAndClickFiltroButton(maxSecondsWait, driver);
    }
    
    private void waitAndClickFiltroButton(int maxSecondsToWait, WebDriver driver) throws Exception {
        if (!isOpenFiltrosUntil(0/*maxSecondsToWait*/, driver)) {
            isElementClickableUntil(driver, By.xpath(XPathFiltrarYOrdenarButton), maxSecondsToWait);
            WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(XPathFiltrarYOrdenarButton), TypeOfClick.javascript);
            isOpenFiltrosUntil(maxSecondsToWait, driver);
        }        
    }
    
    private boolean isOpenFiltrosUntil(int maxSecondsToWait, WebDriver driver) {
    	String xpathLineaOrdenar = FiltroMobil.Ordenar.getXPathLineaFiltroMulti();
        return (isElementVisibleUntil(driver, By.xpath(xpathLineaOrdenar), maxSecondsToWait));
    }
}
