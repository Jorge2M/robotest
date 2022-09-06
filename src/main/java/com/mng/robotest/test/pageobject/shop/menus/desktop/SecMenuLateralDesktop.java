package com.mng.robotest.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test.pageobject.shop.filtros.SecFiltrosDesktop;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.menus.MenuLateralDesktop;

public class SecMenuLateralDesktop extends PageBase {
	
	private static String TAG_CONCAT_MENUS = "[@TAG_CONCAT_MENUS]";
	
	private static String XPATH_LINKK_MENU_WITH_TAG = 
		"//li[not(@class) or @class='element']" +  
		"/a[@href[contains(.,'" + TAG_CONCAT_MENUS + "')]]";
	
//	private static String XPATH_LINK_MENU_WITH_TAG_OUTLET = 
//		"//li[@class='_3AcVO' or @class='element']" +  
//		"/a[@href[contains(.,'" + TAG_CONCAT_MENUS + "')]]";
	
	private static String XPATH_SELECTED_RELATIVE_MENU_SHOP = 
		"//self::*[@aria-label[contains(.,'seleccionado')]]";

	private String getXPathLinkMenu(MenuLateralDesktop menu) {
		String dataGaLabel =  menu.getDataGaLabelMenuLateralDesktop();
//		if (app==AppEcom.outlet && menu.getLevel()==1) {
//			return (XPATH_LINK_MENU_WITH_TAG_OUTLET
//				.replace(TAG_CONCAT_MENUS, dataGaLabel
//				.replace(":", "-")
//				.replaceFirst("-", "/")));
//		}
		return (XPATH_LINKK_MENU_WITH_TAG
			.replace(TAG_CONCAT_MENUS, dataGaLabel
			.replace(":", "-")
			.replaceFirst("-", "/")));
	}
	
	private String getXPathSelectedRelativeMenu() {
		switch (app) {
//		case outlet:
//			return XPathSelectedRelativeMenuOutlet;
		default:
			return XPATH_SELECTED_RELATIVE_MENU_SHOP;
		}
	}
	
	public String getXPathLinkMenuSelected(MenuLateralDesktop menu) {
		return (getXPathLinkMenu(menu) + getXPathSelectedRelativeMenu());
	}

	public boolean isSelectedMenu(MenuLateralDesktop menu, int maxSeconds) {
		PageGaleria pageGaleria = PageGaleria.getNew(channel);
		SecFiltrosDesktop secFiltros = SecFiltrosDesktop.getInstance(pageGaleria);
		secFiltros.showLateralMenus();
		String linkMenuSel = getXPathLinkMenuSelected(menu) ;
		return (state(Visible, By.xpath(linkMenuSel)).wait(maxSeconds).check());
	}

	public void clickMenu(MenuLateralDesktop menu) {
		String xpathMenu1erNivel = getXPathLinkMenu(menu);
		moveToElement(By.xpath(xpathMenu1erNivel), driver);
		click(By.xpath(xpathMenu1erNivel)).exec();
	}

	/**
	 * @return si es o no visible un menú lateral de 1er (menu2oNivel=null) o 2o nivel (menu2oNivel!=null)
	 */
	public boolean isVisibleMenu(MenuLateralDesktop menu) throws Exception {
		PageGaleria pageGaleria = PageGaleria.getNew(channel);
		SecFiltrosDesktop secFiltros = SecFiltrosDesktop.getInstance(pageGaleria);
		secFiltros.showLateralMenus();
		String xpathMenu = getXPathLinkMenu(menu);
		return (state(Visible, By.xpath(xpathMenu)).check());
	}
	
	private static final String XPathCapaMenusShop = "//div[@id='sidebar']/aside[@id='navigation']";
	private static final String XPathCapaMenusOutlet = "//div[@id='sticky']/aside[@id='filters']";
	public boolean isVisibleCapaMenus(int maxSeconds) {
		switch (app) {
		case outlet:
			return state(State.Visible, By.xpath(XPathCapaMenusOutlet)).wait(maxSeconds).check();
		default:
			return state(State.Visible, By.xpath(XPathCapaMenusShop)).wait(maxSeconds).check();
		}
	}
}
