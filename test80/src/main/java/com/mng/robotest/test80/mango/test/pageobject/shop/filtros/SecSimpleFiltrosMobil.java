package com.mng.robotest.test80.mango.test.pageobject.shop.filtros;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Color;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección correspondiente a los "Filtros"
 * @author jorge.munoz
 *
 */
public class SecSimpleFiltrosMobil extends PageObjTM implements SecFiltros {

	final static String XPathFiltrarYOrdenarButton = "//button[@id[contains(.,'orderFiltersBtn')]]";

	PageGaleria pageGaleria = null;
	
	private SecSimpleFiltrosMobil(WebDriver driver, PageGaleria pageGaleria) {
		super(driver);
		this.pageGaleria = pageGaleria;
	}
	
	public static SecSimpleFiltrosMobil getInstance(AppEcom app, WebDriver driver) {
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.mobile, app, driver);
		return (new SecSimpleFiltrosMobil(driver, pageGaleria));
	}
	
	public static SecSimpleFiltrosMobil getInstance(WebDriver driver, PageGaleria pageGaleria) {
		return (new SecSimpleFiltrosMobil(driver, pageGaleria));
	}
	
	public boolean isPresent(WebDriver driver) {
		return (state(Visible, By.xpath(XPathFiltrarYOrdenarButton)).check());
	}
	
    @Override
    public void selectOrdenacion(FilterOrdenacion ordenacion) throws Exception {
        selectFiltroAndWaitLoad(FiltroMobil.Ordenar, ordenacion.getValueForMobil(), driver);
    }
    
    @Override
    public void selectCollection(FilterCollection collection) {
        selectFiltroAndWaitLoad(FiltroMobil.Coleccion, collection.getValueMobil(), driver);
    }
    
    @Override
    public boolean isCollectionFilterPresent() throws Exception {
    	String xpath = FiltroMobil.Coleccion.getXPathLineaFiltroSimple();
    	return (state(Present, By.xpath(xpath)).check());
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
    	String valueFiltro = colorsToFilter.get(0).getCodigoColor();
        selectFiltroAndWaitLoad(FiltroMobil.Colores, valueFiltro, driver);
        int maxSecondsToWait = 10;
        int numArticles = pageGaleria.waitForArticleVisibleAndGetNumberOfThem(maxSecondsToWait);
        return numArticles;
    }
    
    @Override
    public boolean isClickableFiltroUntil(int seconds) {
    	return (state(Clickable, By.xpath(XPathFiltrarYOrdenarButton)).wait(seconds).check());
    }
    
    /**
     * Selecciona un determinado filtro de la galería de móvil
     * @param valor atributo 'value' a nivel de la option del filtro (select)
     */
    private void selectFiltroAndWaitLoad(FiltroMobil typeFiltro, String valorFiltro, WebDriver driver) {
        goAndClickFiltroButton(driver);
        By byLineaFiltro = By.xpath(typeFiltro.getXPathLineaFiltroSimple());
        state(Visible, byLineaFiltro).wait(1).check();
        WebElement filtroLineaSelect = driver.findElement(byLineaFiltro);
        Select selectFiltro = new Select(filtroLineaSelect);
        selectFiltro.selectByValue(valorFiltro);
        waitForPageLoaded(driver);
    }
    
    private void goAndClickFiltroButton(WebDriver driver) {
    	if (state(Visible, By.xpath(XPathFiltrarYOrdenarButton)).check()) {
        	moveToElement(By.xpath(XPathFiltrarYOrdenarButton), driver);
            waitMillis(500);
            
            //Scrollamos un poquito hacia arriba para asegurar
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-50)", "");
        }
        
        int maxSecondsToWait = 2;
        waitAndClickFiltroButton(maxSecondsToWait, driver);
    }
    
    private void waitAndClickFiltroButton(int maxSeconds, WebDriver driver) {
        if (!isOpenFiltrosUntil(0, driver)) {
        	state(Clickable, By.xpath(XPathFiltrarYOrdenarButton)).wait(maxSeconds).check();
            driver.findElement(By.xpath(XPathFiltrarYOrdenarButton)).click();
            isOpenFiltrosUntil(maxSeconds, driver);
        }
    }
    
    private boolean isOpenFiltrosUntil(int maxSeconds, WebDriver driver) {
    	String xpathLineaOrdenar = FiltroMobil.Ordenar.getXPathLineaFiltroSimple();
    	return (state(Visible, By.xpath(xpathLineaOrdenar)).wait(maxSeconds).check());
    }
}
