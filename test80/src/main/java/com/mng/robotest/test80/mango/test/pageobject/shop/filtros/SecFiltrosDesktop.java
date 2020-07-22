package com.mng.robotest.test80.mango.test.pageobject.shop.filtros;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Color;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenuLateralDesktop;


/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección correspondiente a los "Filtros" en Desktop
 * @author jorge.munoz
 *
 */
public class SecFiltrosDesktop extends PageObjTM implements SecFiltros {
    
	final static String TagOrdenacion = "@TagOrden";
	final static String TagColor = "@TagColor";
	final static String XPathLinkOrdenWithTag = "//a[text()[contains(.,'" + TagOrdenacion + "')]]";
	final static String XPathLinkColorWithTagOutlet = "//a[@aria-label[contains(.,'" + TagColor + "')]]";
	final static String XPathLinkColorWithTagShop = 
			"//label[@for[contains(.,'filter_')] and text()[contains(.,'" + TagColor + "')]]";
	
	final PageGaleria pageGaleria;
	final AppEcom app;
	
	private SecFiltrosDesktop(WebDriver driver, PageGaleria pageGaleria) {
		super(driver);
		this.pageGaleria = pageGaleria;
		this.app = pageGaleria.getApp();
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
		if (app==AppEcom.outlet) {
			return (XPathLinkColorWithTagOutlet.replace(TagColor, color.getNameFiltro()));
		}
		return (XPathLinkColorWithTagShop.replace(TagColor, color.getNameFiltro()));
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
		showFilters();
		for (Color color : colorsToSelect) {
			String xpathLinkColor = getXPathLinkColor(color);
			moveToElement(By.xpath(xpathLinkColor), driver);
			click(By.xpath(xpathLinkColor)).exec();
		}
		acceptFilters();
		int maxSecondsToWait = 10;
		int numArticles = pageGaleria.waitForArticleVisibleAndGetNumberOfThem(maxSecondsToWait);
		return numArticles;
	}
	
	@Override
	public boolean isClickableFiltroUntil(int seconds) {
		return (state(Clickable, By.xpath(XPathLinkOrdenWithTag), driver)
				.wait(seconds).check());
	}
	
	
	private static final String XPathLinkCollectionShop = "//div[@id='navigationContainer']/button";
	public void showLateralMenus() {
		if (app!=AppEcom.outlet) {
			SecMenuLateralDesktop secMenuLateral = SecMenuLateralDesktop.getNew(AppEcom.shop, driver);
			if (!secMenuLateral.isVisibleCapaMenus(1)) {
				click(By.xpath(XPathLinkCollectionShop)).exec();
			}
		}
	}
	
	//TODO hablar con Sergio Campillo para que añada algún id no-react
	private static final String XPathCapaFiltersShop = "//div[@class[contains(.,'_1PO8g')]]";
	private static final String XPathLinkFiltrarShop = "//button[@id='filtersBtn']";
	public void showFilters() {
		if (app!=AppEcom.outlet) {
			if (!isFiltersShopVisible(1)) {
				click(By.xpath(XPathLinkFiltrarShop)).exec();
			}
		}
	}
	public void hideFilters() {
		if (app!=AppEcom.outlet) {
			if (isFiltersShopVisible(1)) {
				click(By.xpath(XPathLinkFiltrarShop)).exec();
			}
		}
	}
	public void acceptFilters() {
		if (app!=AppEcom.outlet) {
			click(By.xpath(XPathLinkFiltrarShop)).exec();
		}
	}
	private boolean isFiltersShopVisible(int maxSeconds) {
		return state(State.Visible, By.xpath(XPathCapaFiltersShop)).wait(maxSeconds).check();
	}
}
