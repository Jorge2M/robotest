package com.mng.robotest.test.pageobject.shop.filtros;

import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.test.data.Color;
import com.mng.robotest.test.pageobject.shop.menus.desktop.SecMenuLateralDesktop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecFiltrosDesktop extends PageBase implements SecFiltros {
	
	private static final String TAG_ORDENACION = "@TagOrden";
	private static final String TAG_COLOR = "@TagColor";
	private static final String XPATH_WRAPPER = "//div[@id='stickyMenu']";
	private static final String XPATH_LINK_ORDEN_WITH_TAG = "//a[text()[contains(.,'" + TAG_ORDENACION + "')]]";
	private static final String XPATH_LINK_COLOR_WITH_TAG_SHOP = "//label[(@for[contains(.,'filtercolor')] or @for[contains(.,'multiSelectfilter_GroupsColors')]) and text()[contains(.,'" + TAG_COLOR + "')]]";
	private static final String XPATH_LINK_COLOR_WITH_TAG_TABLET_OUTLET	 = "//label[@for[contains(.,'color_" + TAG_COLOR + "')]]";
	
	private final PageGaleria pageGaleria;
	
	private SecFiltrosDesktop(PageGaleria pageGaleria) {
		this.pageGaleria = pageGaleria;
	}
	
	public static SecFiltrosDesktop getInstance(Channel channel) {
		PageGaleria pageGaleria = PageGaleria.getNew(channel);
		return new SecFiltrosDesktop(pageGaleria);
	}
	
	public static SecFiltrosDesktop getInstance(PageGaleria pageGaleria) {
		return new SecFiltrosDesktop(pageGaleria);
	}
	
	private String getXPathLinkOrdenacion(FilterOrdenacion ordenacion) {
		return (XPATH_LINK_ORDEN_WITH_TAG.replace(TAG_ORDENACION, ordenacion.getValueForDesktop()));
	}
	
	private String getXPathLinkColor(Color color) {
		if (app==AppEcom.outlet && channel==Channel.tablet) {
			return XPATH_LINK_COLOR_WITH_TAG_TABLET_OUTLET.replace(TAG_COLOR, color.getNameFiltro());
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
		click(xpathLink).exec();
	}
	
	@Override
	public int selecOrdenacionAndReturnNumArticles(FilterOrdenacion typeOrden) throws Exception {
		selectOrdenacion(typeOrden);
		return pageGaleria.waitForArticleVisibleAndGetNumberOfThem(10);
	}
	
	/** 
	 * @return el número de artículos que aparecen en la galería después de seleccionar el filtro
	 */
	@Override
	public int selecFiltroColoresAndReturnNumArticles(List<Color> colorsToSelect) {
		showFilters();
		for (Color color : colorsToSelect) {
			String xpathLinkColor = getXPathLinkColor(color);
			moveToElement(xpathLinkColor);
			click(xpathLinkColor).exec();
		}
		acceptFilters();
		return pageGaleria.waitForArticleVisibleAndGetNumberOfThem(10);
	}
	
	@Override
	public boolean isClickableFiltroUntil(int seconds) {
		return state(Clickable, XPATH_LINK_ORDEN_WITH_TAG).wait(seconds).check();
	}
	
	@Override
	public void selectMenu2onLevel(List<String> listMenus) {
		//TODO
	}
	@Override
	public void selectMenu2onLevel(String menuLabel) {
		//TODO
	}	
	
	public void bring(BringTo bringTo) {
		bringElement(getElement(XPATH_WRAPPER), bringTo);
	}
	
	private static final String XPATH_LINK_COLLECTION = "//div[@id='navigationContainer']/button";
	public void showLateralMenus() {
		if (!new SecMenuLateralDesktop().isVisibleCapaMenus(1)) {
			click(XPATH_LINK_COLLECTION).exec();
		}
	}
	
	//TODO hablar con Sergio Campillo para que añada algún id no-react
	private static final String XPATH_LINK_FILTRAR = "//span[@class[contains(.,'icon-fill-filter')]]";
	private static final String XPATH_CAPA_FILTERS = "//div[@class[contains(.,'filters--')]]";
	private static final String XPATH_BUTTON_FILTRAR = XPATH_CAPA_FILTERS + "//button[@class[contains(.,'primary')]]";
	
	public void showFilters() {
		if (!isFiltersShopVisible(1) &&
			state(Clickable, XPATH_LINK_FILTRAR).check()) {
			click(XPATH_LINK_FILTRAR).exec();
		}
	}
	public void hideFilters() {
		if (isFiltersShopVisible(1) &&
			state(Clickable, XPATH_LINK_FILTRAR).check()) {
			click(XPATH_LINK_FILTRAR).exec();
		}
	}
	
	public void acceptFilters() {
		click(XPATH_BUTTON_FILTRAR).exec();
		waitMillis(1000);
		waitForPageLoaded(driver);
		pageGaleria.isVisibleImageArticle(1, 2);
	}
	
	private boolean isFiltersShopVisible(int seconds) {
		return state(Visible, XPATH_CAPA_FILTERS).wait(seconds).check();
	}
	
}
