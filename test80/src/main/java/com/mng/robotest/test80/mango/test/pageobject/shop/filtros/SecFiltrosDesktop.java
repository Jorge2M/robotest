package com.mng.robotest.test80.mango.test.pageobject.shop.filtros;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Color;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;


/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección correspondiente a los "Filtros" en Desktop
 * @author jorge.munoz
 *
 */
public class SecFiltrosDesktop extends PageObjTM implements SecFiltros {
    
	final static String TagOrdenacion = "@TagOrden";
	final static String TagColor = "@TagColor";
	final static String XPathLinkOrdenWithTag = "//a[text()[contains(.,'" + TagOrdenacion + "')]]";
	final static String XPathLinkColorWithTag = "//a[@aria-label[contains(.,'" + TagColor + "')]]";
	
	PageGaleria pageGaleria = null;
	
	private SecFiltrosDesktop(WebDriver driver, PageGaleria pageGaleria) {
		super(driver);
		this.pageGaleria = pageGaleria;
	}
	
	public static SecFiltrosDesktop getInstance(AppEcom app, WebDriver driver) {
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.desktop, app, driver);
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
    public void selectCollection(FilterCollection collection) {
		//TODO
    }
	
    @Override
    public boolean isCollectionFilterPresent() throws Exception {
    	//TODO
    	return false;
    }
	
	@Override
    public void selectOrdenacion(FilterOrdenacion ordenacion) {
    	String xpathLink = getXPathLinkOrdenacion(ordenacion);
    	click(By.xpath(xpathLink)).exec();
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
    public int selecFiltroColoresAndReturnNumArticles(List<Color> colorsToSelect) {
		for (Color color : colorsToSelect) {
			String xpathLinkColor = getXPathLinkColor(color);
			moveToElement(By.xpath(xpathLinkColor), driver);
			click(By.xpath(xpathLinkColor)).exec();
		}
		
		int maxSecondsToWait = 10;
		int numArticles = pageGaleria.waitForArticleVisibleAndGetNumberOfThem(maxSecondsToWait);
		return numArticles;
	}
	
	@Override
	public boolean isClickableFiltroUntil(int seconds) {
		return (state(Clickable, By.xpath(XPathLinkOrdenWithTag), driver)
				.wait(seconds).check());
	}
}
