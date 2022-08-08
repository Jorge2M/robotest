package com.mng.robotest.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.filtros.SecFiltrosDesktop;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.menus.MenuLateralDesktop;


public class SecMenuLateralDesktop extends PageObjTM {
	
	private final AppEcom app;
	
	private static String TAG_CONCAT_MENUS = "[@TAG_CONCAT_MENUS]";
	
	private static String XPATH_LINKK_MENU_WITH_TAG_SHOP = 
		"//li[not(@class) or @class='element']" +  
		"/a[@href[contains(.,'" + TAG_CONCAT_MENUS + "')]]";
	
	private static String XPATH_LINK_MENU_WITH_TAG_OUTLET = 
		"//li[@class='_3AcVO' or @class='element']" +  
		"/a[@href[contains(.,'" + TAG_CONCAT_MENUS + "')]]";
	
	private static String XPATH_SELECTED_RELATIVE_MENU_SHOP = 
		"//self::*[@aria-label[contains(.,'seleccionado')]]";

	private SecMenuLateralDesktop(AppEcom app) {
		this.app = app;
	}
	public static SecMenuLateralDesktop getNew(AppEcom app) {
		return (new SecMenuLateralDesktop(app));
	}

	private String getXPathLinkMenu(MenuLateralDesktop menu) {
		String dataGaLabel =  menu.getDataGaLabelMenuLateralDesktop();
		if (app==AppEcom.outlet && menu.getLevel()==1) {
			return (XPATH_LINK_MENU_WITH_TAG_OUTLET
				.replace(TAG_CONCAT_MENUS, dataGaLabel
				.replace(":", "-")
				.replaceFirst("-", "/")));
		}
		return (XPATH_LINKK_MENU_WITH_TAG_SHOP
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
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.desktop, app);
		SecFiltrosDesktop secFiltros = SecFiltrosDesktop.getInstance(pageGaleria, driver);
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
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.desktop, app);
		SecFiltrosDesktop secFiltros = SecFiltrosDesktop.getInstance(pageGaleria, driver);
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
