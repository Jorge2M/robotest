package com.mng.robotest.tests.domains.galeria.pageobjects.filters.device;

import java.util.List;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.FilterOrdenacion;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.SecFiltros;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.Color;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecFiltrosTabletOutlet extends PageBase implements SecFiltros {
	
	private static final String TAG_ORDENACION = "@TagOrden";
	private static final String TAG_COLOR = "@TagColor";
	private static final String XP_LINK_ORDEN_WITH_TAG = "//a[text()[contains(.,'" + TAG_ORDENACION + "')]]";
	private static final String XP_LINK_COLOR_WITH_TAG = "//label[@for='color_" + TAG_COLOR + "']/a";
	
	public static SecFiltrosTabletOutlet make(Pais pais) {
		return new SecFiltrosTabletOutlet();
	}
	
	private String getXPathLinkOrdenacion(FilterOrdenacion ordenacion) {
		return (XP_LINK_ORDEN_WITH_TAG.replace(TAG_ORDENACION, ordenacion.getValue()));
	}
	
	private String getXPathLinkColor(Color color) {
		return (XP_LINK_COLOR_WITH_TAG.replace(TAG_COLOR, color.getNameFiltro()));
	}
	
	private void selectOrdenacion(FilterOrdenacion ordenacion) {
		String xpathLink = getXPathLinkOrdenacion(ordenacion);
		click(xpathLink).exec();
	}
	
	@Override
	public void selecOrdenacion(FilterOrdenacion typeOrden) throws Exception {
		selectOrdenacion(typeOrden);
	}
	
	@Override
	public void selecFiltroColores(List<Color> colorsToSelect) {
		for (Color color : colorsToSelect) {
			String xpathLinkColor = getXPathLinkColor(color);
			moveToElement(xpathLinkColor);
			click(xpathLinkColor).exec();
		}
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
