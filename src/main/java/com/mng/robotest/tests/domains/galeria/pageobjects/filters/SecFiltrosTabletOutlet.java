package com.mng.robotest.tests.domains.galeria.pageobjects.filters;

import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.Color;

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
	
	public static SecFiltrosTabletOutlet getInstance(Pais pais) {
		PageGaleria pageGaleria = PageGaleria.make(Channel.tablet, AppEcom.outlet, pais);
		return (new SecFiltrosTabletOutlet(pageGaleria));
	}
	
	private String getXPathLinkOrdenacion(FilterOrdenacion ordenacion) {
		return (XPATH_LINK_ORDEN_WITH_TAG.replace(TAG_ORDENACION, ordenacion.getValue()));
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
		String xpath = getXPathLinkOrdenacion(FilterOrdenacion.PRECIO_ASC);
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
