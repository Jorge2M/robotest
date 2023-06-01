package com.mng.robotest.domains.transversal.menus.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;

public class MenusWebAllDevice extends PageBase implements MenusWebAll {

	//TODO eliminar el OLD cuando suba la nueva versión a PRO (31-05-2023)
	private static final String XPATH_MENU_ITEM_OLD = "//a[@data-testid[contains(.,'header.subMenu.item')]]";
	private static final String XPATH_MENU_ITEM_NEW = "//li[@data-testid]/a[@data-testid[contains(.,'menu.family.')]]";
	
	private String getXPathMenuItem() {
		return "(" + XPATH_MENU_ITEM_OLD + " | " + XPATH_MENU_ITEM_NEW + ")";
	}
	
	@Override
	public boolean isMenuInState(boolean open, int seconds) {
		if (open) {
			return isMenuOpen(seconds);
		}
		return isMenuClose(seconds);
	}
	
	@Override
	public List<MenuWeb> getMenus(GroupWeb groupWeb) {
		groupWeb.click();
		return getVisibleMenus(groupWeb);
	}
	
	private List<MenuWeb> getVisibleMenus(GroupWeb groupWeb) {
		List<MenuWeb> menus = new ArrayList<>();
		var menuElements = getElements(getXPathMenuItem());
		for (WebElement menuElement : menuElements) {
			menus.add(new MenuWeb
					.Builder(menuElement.getText())
					.linea(groupWeb.getLinea())
					.sublinea(groupWeb.getSublinea())
					.group(groupWeb.getGroup())
					.build());
		}
		return menus;
	}
	
	private boolean isMenuOpen(int seconds) {
		return (new LineaWeb(LineaType.SHE).isLineaPresent(seconds));
	}
	private boolean isMenuClose(int seconds) {
		for (int i=0; i<seconds; i++) {
			if (!isMenuOpen(0)) {
				return true;
			}
		}
		return false;
	}
}
