package com.mng.robotest.test.pageobject.shop.filtros;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.Color;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecFiltrosTabletOutlet extends PageObjTM implements SecFiltros {
	
	private final static String TagOrdenacion = "@TagOrden";
	private final static String TagColor = "@TagColor";
	private final static String XPathLinkOrdenWithTag = "//a[text()[contains(.,'" + TagOrdenacion + "')]]";
	private final static String XPathLinkColorWithTag = "//label[@for='color_" + TagColor + "']/a";
	
	private final PageGaleria pageGaleria;
	
	private SecFiltrosTabletOutlet(WebDriver driver, PageGaleria pageGaleria) {
		super(driver);
		this.pageGaleria = pageGaleria;
	}
	
	public static SecFiltrosTabletOutlet getInstance(WebDriver driver) {
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.tablet, AppEcom.outlet, driver);
		return (new SecFiltrosTabletOutlet(driver, pageGaleria));
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
	
	@Override
	public int selecFiltroColoresAndReturnNumArticles(List<Color> colorsToSelect) {
		for (Color color : colorsToSelect) {
			String xpathLinkColor = getXPathLinkColor(color);
			moveToElement(By.xpath(xpathLinkColor), driver);
			click(By.xpath(xpathLinkColor)).exec();
		}
		int numArticles = pageGaleria.waitForArticleVisibleAndGetNumberOfThem(10);
		return numArticles;
	}
	
	@Override
	public boolean isClickableFiltroUntil(int seconds) {
		String xpath = getXPathLinkOrdenacion(FilterOrdenacion.PrecioAsc);
		return (state(Clickable, By.xpath(xpath), driver).wait(seconds).check());
	}
	
	@Override
	public void selectMenu2onLevel(List<String> listMenus) {
		//selectFiltrosAndWaitLoad(FiltroMobil.Familia, Arrays.asList(nameMenu));
		//TODO
	}

}
