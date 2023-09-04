package com.mng.robotest.test.pageobject.shop.menus.desktop;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.galeria.pageobjects.FilterCollection;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusFiltroCollection;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecMenusFiltroCollectionDesktop extends PageBase implements SecMenusFiltroCollection {

	private static final String XPATH_DIV_MENUS_DESKTOP = "//div[@id='nuevaTemporadaFilter']";
	
	private static String getXPathMenu(FilterCollection typeMenu) {
		return (XPATH_DIV_MENUS_DESKTOP + "//li[@data-filter-value='" + typeMenu.getDataFilterValue() + "']");
	}
	
	@Override
	public boolean isVisible() {
		return state(Visible, XPATH_DIV_MENUS_DESKTOP).check();
	}
	
	@Override
	public boolean isVisibleMenu(FilterCollection typeMenu) {
		String xpathMenu = getXPathMenu(typeMenu);
		return state(Visible, xpathMenu).check();
	}

	@Override
	public void click(FilterCollection typeMenu) {
		click(getXPathMenu(typeMenu)).exec();
	}
}
