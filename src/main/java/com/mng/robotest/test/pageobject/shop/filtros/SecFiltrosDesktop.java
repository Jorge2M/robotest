package com.mng.robotest.test.pageobject.shop.filtros;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.Color;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.menus.desktop.SecMenuLateralDesktop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecFiltrosDesktop extends PageObjTM implements SecFiltros {
	
	private static final String TAG_ORDENACION = "@TagOrden";
	private static final String TAG_COLOR = "@TagColor";
	private static final String XPATH_WRAPPER = "//div[@id='stickyMenu']";
	private static final String XPATH_LINK_ORDEN_WITH_TAG = "//a[text()[contains(.,'" + TAG_ORDENACION + "')]]";
	private static final String XPATH_LINK_COLOR_WITH_TAG_OUTLET = "//a[@aria-label[contains(.,'" + TAG_COLOR + "')]]";
	private static final String XPATH_LINK_COLOR_WITH_TAG_SHOP = "//label[(@for[contains(.,'filtercolor')] or @for[contains(.,'multiSelectGroupsColors')]) and text()[contains(.,'" + TAG_COLOR + "')]]";
	private static final String XPATH_LINK_COLOR_WITH_TAG_TABLET_OUTLET	 = "//label[@for[contains(.,'color_" + TAG_COLOR + "')]]";
	
	final PageGaleria pageGaleria;
	final Channel channel;
	final AppEcom app;
	
	private SecFiltrosDesktop(PageGaleria pageGaleria, WebDriver driver) {
		super(driver);
		this.pageGaleria = pageGaleria;
		this.channel = pageGaleria.getChannel();
		this.app = pageGaleria.getApp();
	}
	
	public static SecFiltrosDesktop getInstance(Channel channel, AppEcom app, WebDriver driver) {
		PageGaleria pageGaleria = PageGaleria.getNew(channel, app);
		return (new SecFiltrosDesktop(pageGaleria, driver));
	}
	
	public static SecFiltrosDesktop getInstance(PageGaleria pageGaleria, WebDriver driver) {
		return (new SecFiltrosDesktop(pageGaleria, driver));
	}
	
	private String getXPathLinkOrdenacion(FilterOrdenacion ordenacion) {
		return (XPATH_LINK_ORDEN_WITH_TAG.replace(TAG_ORDENACION, ordenacion.getValueForDesktop()));
	}
	
	private String getXPathLinkColor(Color color) {
		if (app==AppEcom.outlet) {
			if (channel==Channel.tablet) {
				return XPATH_LINK_COLOR_WITH_TAG_TABLET_OUTLET.replace(TAG_COLOR, color.getNameFiltro());
			}
			return (XPATH_LINK_COLOR_WITH_TAG_OUTLET.replace(TAG_COLOR, color.getNameFiltro()));
		}
		return (XPATH_LINK_COLOR_WITH_TAG_SHOP.replace(TAG_COLOR, color.getNameFiltro()));
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
		return (state(Clickable, By.xpath(XPATH_LINK_ORDEN_WITH_TAG), driver)
				.wait(seconds).check());
	}
	
	@Override
	public void selectMenu2onLevel(List<String> listMenus) {
		//TODO
	}
	
	public enum Visibility {Visible, Invisible}
	
	public void makeFilters(Visibility visibility) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(By.xpath(XPATH_WRAPPER));
		String style = "block";
		if (visibility == Visibility.Invisible) {
			style = "none";
		}
		js.executeScript("arguments[0].setAttribute('style', 'display:" + style + "')", element);
	}
	
	private static final String XPathLinkCollectionShop = "//div[@id='navigationContainer']/button";
	public void showLateralMenus() {
		if (app!=AppEcom.outlet) {
			SecMenuLateralDesktop secMenuLateral = SecMenuLateralDesktop.getNew(AppEcom.shop);
			if (!secMenuLateral.isVisibleCapaMenus(1)) {
				click(By.xpath(XPathLinkCollectionShop)).exec();
			}
		}
	}
	
	//TODO hablar con Sergio Campillo para que añada algún id no-react
	private static final String XPathLinkFiltrarShop = "//span[@class[contains(.,'icon-fill-filter')]]";
	private static final String XPathCapaFiltersShop = "//div[@class[contains(.,'filters--')]]";
	private static final String XPathButtonFiltrarShop = XPathCapaFiltersShop + "//button[@class[contains(.,'primary')]]";
	public void showFilters() {
		if (app!=AppEcom.outlet) {
			if (!isFiltersShopVisible(1) &&
				state(State.Clickable, By.xpath(XPathLinkFiltrarShop)).check()) {
				click(By.xpath(XPathLinkFiltrarShop)).exec();
			}
		}
	}
	public void hideFilters() {
		if (app!=AppEcom.outlet) {
			if (isFiltersShopVisible(1) &&
				state(State.Clickable, By.xpath(XPathLinkFiltrarShop)).check()) {
				click(By.xpath(XPathLinkFiltrarShop)).exec();
			}
		}
	}
	public void acceptFilters() {
		if (app!=AppEcom.outlet) {
			click(By.xpath(XPathButtonFiltrarShop)).exec();
		}
	}
	private boolean isFiltersShopVisible(int maxSeconds) {
		return state(State.Visible, By.xpath(XPathCapaFiltersShop)).wait(maxSeconds).check();
	}
}
