package com.mng.robotest.test80.mango.test.pageobject.shop.filtros;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.Color;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import org.openqa.selenium.WebElement;


/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección correspondiente a los "Filtros" en Desktop
 * @author jorge.munoz
 *
 */
public class SecFiltrosDesktop extends WebdrvWrapp implements SecFiltros {
    
	final static String TagOrdenacion = "@TagOrden";
	final static String TagColor = "@TagColor";
	final static String XPathLinkOrdenWithTag = "//a[text()[contains(.,'" + TagOrdenacion + "')]]";
	final static String XPathLinkColorWithTag = "//a[text()[contains(.,'" + TagColor + "')]]";
	
	WebDriver driver;
	PageGaleria pageGaleria = null;
	
	private SecFiltrosDesktop(WebDriver driver, PageGaleria pageGaleria) {
		this.driver = driver;
		this.pageGaleria = pageGaleria;
	}
	
	public static SecFiltrosDesktop getInstance(AppEcom app, WebDriver driver) throws Exception {
		PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, app, driver);
		return (new SecFiltrosDesktop(driver, pageGaleria));
	}
	
	public static SecFiltrosDesktop getInstance(WebDriver driver, PageGaleria pageGaleria) {
		return (new SecFiltrosDesktop(driver, pageGaleria));
	}
	
	private String getXPathLinkOrdenacion(FilterOrdenacion ordenacion) {
		return (XPathLinkOrdenWithTag.replace(TagOrdenacion, ordenacion.getValueForDesktop()));
	}
	
	private String getXPathLinkColor(Color color) {
		return (XPathLinkColorWithTag.replace(TagColor, color.getNameFiltro()));
	}
	
	@Override
    public void selectCollection(FilterCollection collection) throws Exception {
		//TODO
    }
	
    @Override
    public boolean isCollectionFilterPresent() throws Exception {
    	//TODO
    	return false;
    }
	
	@Override
    public void selectOrdenacion(FilterOrdenacion ordenacion) throws Exception {
    	String xpathLink = getXPathLinkOrdenacion(ordenacion);
        clickAndWaitLoad(driver, By.xpath(xpathLink));        
    }
	
	@Override
    public int selecOrdenacionAndReturnNumArticles(FilterOrdenacion typeOrden) throws Exception {
        selectOrdenacion(typeOrden);
        int maxSecondsToWait = 10;
        int numArticles = pageGaleria.waitForArticleVisibleAndGetNumberOfThem(maxSecondsToWait);
        return numArticles;
    }
    
    /** 
     * @return el número de artículos que aparecen en la galería después de seleccionar el filtro
     */
	@Override
    public int selecFiltroColoresAndReturnNumArticles(List<Color> colorsToSelect) 
    throws Exception {
		for (Color color : colorsToSelect) {
			String xpathLinkColor = getXPathLinkColor(color);
			moveToElement(By.xpath(xpathLinkColor), driver);
			clickAndWaitLoad(driver, By.xpath(xpathLinkColor));
		}
		
        int maxSecondsToWait = 10;
        int numArticles = pageGaleria.waitForArticleVisibleAndGetNumberOfThem(maxSecondsToWait);
        return numArticles;
    }
	
    @Override
    public boolean isClickableFiltroUntil(int seconds) {
        return (isElementClickableUntil(driver, By.xpath(XPathLinkOrdenWithTag), seconds));
    }    
}
