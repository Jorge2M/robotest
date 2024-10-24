package com.mng.robotest.testslegacy.pageobject.shop.menus.desktop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenuLateralDesktop;

public class SecMenuLateralDesktop extends PageBase {
	
	private static final String TAG_CONCAT_MENUS = "[@TAG_CONCAT_MENUS]";
	
	private static final String XP_LINKK_MENU_WITH_TAG = 
		"//li[not(@class) or @class='element']" +  
		"/a[@href[contains(.,'" + TAG_CONCAT_MENUS + "')]]";
	
	private static final String XP_SELECTED_RELATIVE_MENU_SHOP = 
		"//self::*[@aria-label[contains(.,'seleccionado')]]";

	private String getXPathLinkMenu(MenuLateralDesktop menu) {
		String dataGaLabel =  menu.getDataGaLabelMenuLateralDesktop();
		return (XP_LINKK_MENU_WITH_TAG
			.replace(TAG_CONCAT_MENUS, dataGaLabel
			.replace(":", "-")
			.replaceFirst("-", "/")));
	}
	
	public String getXPathLinkMenuSelected(MenuLateralDesktop menu) {
		return (getXPathLinkMenu(menu) + XP_SELECTED_RELATIVE_MENU_SHOP);
	}

	public void clickMenu(MenuLateralDesktop menu) {
		String xpathMenu1erNivel = getXPathLinkMenu(menu);
		moveToElement(xpathMenu1erNivel);
		click(xpathMenu1erNivel).exec();
	}

	private static final String XP_CAPA_MENUS_SHOP = "//div[@id='sidebar']/aside[@id='navigation']";
	private static final String XP_CAPA_MENUS_OUTLET = "//div[@id='sticky']/aside[@id='filters']";
	
	public boolean isVisibleCapaMenus(int seconds) {
		if (isOutlet()) {
			return state(VISIBLE, XP_CAPA_MENUS_OUTLET).wait(seconds).check();
		}
		else {
			return state(VISIBLE, XP_CAPA_MENUS_SHOP).wait(seconds).check();
		}
	}
}
