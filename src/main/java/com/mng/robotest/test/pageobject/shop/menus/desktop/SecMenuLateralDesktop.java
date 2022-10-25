package com.mng.robotest.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.filtros.SecFiltrosDesktop;
import com.mng.robotest.test.pageobject.shop.menus.MenuLateralDesktop;

public class SecMenuLateralDesktop extends PageBase {
	
	private static final String TAG_CONCAT_MENUS = "[@TAG_CONCAT_MENUS]";
	
	private static final String XPATH_LINKK_MENU_WITH_TAG = 
		"//li[not(@class) or @class='element']" +  
		"/a[@href[contains(.,'" + TAG_CONCAT_MENUS + "')]]";
	
	private static final String XPATH_SELECTED_RELATIVE_MENU_SHOP = 
		"//self::*[@aria-label[contains(.,'seleccionado')]]";

	private String getXPathLinkMenu(MenuLateralDesktop menu) {
		String dataGaLabel =  menu.getDataGaLabelMenuLateralDesktop();
		return (XPATH_LINKK_MENU_WITH_TAG
			.replace(TAG_CONCAT_MENUS, dataGaLabel
			.replace(":", "-")
			.replaceFirst("-", "/")));
	}
	
	private String getXPathSelectedRelativeMenu() {
		switch (app) {
		default:
			return XPATH_SELECTED_RELATIVE_MENU_SHOP;
		}
	}
	
	public String getXPathLinkMenuSelected(MenuLateralDesktop menu) {
		return (getXPathLinkMenu(menu) + getXPathSelectedRelativeMenu());
	}

	public boolean isSelectedMenu(MenuLateralDesktop menu, int seconds) {
		PageGaleria pageGaleria = PageGaleria.getNew(channel);
		SecFiltrosDesktop secFiltros = SecFiltrosDesktop.getInstance(pageGaleria);
		secFiltros.showLateralMenus();
		String linkMenuSel = getXPathLinkMenuSelected(menu) ;
		return (state(Visible, By.xpath(linkMenuSel)).wait(seconds).check());
	}

	public void clickMenu(MenuLateralDesktop menu) {
		String xpathMenu1erNivel = getXPathLinkMenu(menu);
		moveToElement(xpathMenu1erNivel);
		click(xpathMenu1erNivel).exec();
	}

	/**
	 * @return si es o no visible un menú lateral de 1er (menu2oNivel=null) o 2o nivel (menu2oNivel!=null)
	 */
	public boolean isVisibleMenu(MenuLateralDesktop menu) {
		PageGaleria pageGaleria = PageGaleria.getNew(channel);
		SecFiltrosDesktop secFiltros = SecFiltrosDesktop.getInstance(pageGaleria);
		secFiltros.showLateralMenus();
		return state(Visible, getXPathLinkMenu(menu)).check();
	}
	
	private static final String XPATH_CAPA_MENUS_SHOP = "//div[@id='sidebar']/aside[@id='navigation']";
	private static final String XPATH_CAPA_MENUS_OUTLET = "//div[@id='sticky']/aside[@id='filters']";
	
	public boolean isVisibleCapaMenus(int seconds) {
		switch (app) {
		case outlet:
			return state(State.Visible, XPATH_CAPA_MENUS_OUTLET).wait(seconds).check();
		default:
			return state(State.Visible, XPATH_CAPA_MENUS_SHOP).wait(seconds).check();
		}
	}
}
