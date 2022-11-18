package com.mng.robotest.test.pageobject.shop.filtros;

import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.data.Color;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecFiltrosTabletOutlet extends PageBase implements SecFiltros {
	
	private static final String TAG_ORDENACION = "@TagOrden";
	private static final String TAG_COLOR = "@TagColor";
	private static final String XPATH_LINK_ORDEN_WITH_TAG = "//a[text()[contains(.,'" + TAG_ORDENACION + "')]]";
	private static final String XPATH_LINK_COLOR_WITH_TAG = "//label[@for='color_" + TAG_COLOR + "']/a";
	
	private final PageGaleria pageGaleria;
	
	private SecFiltrosTabletOutlet(PageGaleria pageGaleria) {
		this.pageGaleria = pageGaleria;
	}
	
	public static SecFiltrosTabletOutlet getInstance() {
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.tablet);
		return (new SecFiltrosTabletOutlet(pageGaleria));
	}
	
	private String getXPathLinkOrdenacion(FilterOrdenacion ordenacion) {
		return (XPATH_LINK_ORDEN_WITH_TAG.replace(TAG_ORDENACION, ordenacion.getValueForDesktop()));
	}
	
	private String getXPathLinkColor(Color color) {
		return (XPATH_LINK_COLOR_WITH_TAG.replace(TAG_COLOR, color.getNameFiltro()));
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
	
	@Override
	public int selecFiltroColoresAndReturnNumArticles(List<Color> colorsToSelect) {
		for (Color color : colorsToSelect) {
			String xpathLinkColor = getXPathLinkColor(color);
			moveToElement(xpathLinkColor);
			click(xpathLinkColor).exec();
		}
		return pageGaleria.waitForArticleVisibleAndGetNumberOfThem(10);
	}
	
	@Override
	public boolean isClickableFiltroUntil(int seconds) {
		String xpath = getXPathLinkOrdenacion(FilterOrdenacion.PrecioAsc);
		return state(Clickable, xpath).wait(seconds).check();
	}
	
	@Override
	public void selectMenu2onLevel(List<String> listMenus) {
		//selectFiltrosAndWaitLoad(FiltroMobil.Familia, Arrays.asList(nameMenu));
		//TODO
	}
	@Override
	public void selectMenu2onLevel(String menuLabel) {
		//TODO
	}	

}
