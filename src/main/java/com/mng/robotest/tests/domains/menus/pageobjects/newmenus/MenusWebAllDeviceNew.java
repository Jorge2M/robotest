package com.mng.robotest.tests.domains.menus.pageobjects.newmenus;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.menus.pageobjects.GroupWeb;
import com.mng.robotest.tests.domains.menus.pageobjects.MenuWeb;
import com.mng.robotest.tests.domains.menus.pageobjects.MenusWebAll;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class MenusWebAllDeviceNew extends PageBase implements MenusWebAll {

	private static final String XP_MENU_OPEN = "//*[@data-testid='inspirational.menuBackdrop']"; 
	private static final String XP_MENU_ITEM = "//li[@data-testid]/a[@data-testid[contains(.,'menu.family.')]]";
	
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
		var menuElements = getElements(XP_MENU_ITEM);
		for (var menuElement : menuElements) {
			String name = menuElement.getText();
			if ("".compareTo(name)!=0) {
				menus.add(new MenuWeb
						.Builder(name)
						.linea(groupWeb.getLinea())
						.sublinea(groupWeb.getSublinea())
						.group(groupWeb.getGroup())
						.build());
			}
		}
		return menus;
	}

	private boolean isMenuOpen(int seconds) {
		return state(VISIBLE, XP_MENU_OPEN).wait(seconds).check();
	}
	
	private boolean isMenuClose(int seconds) {
		for (int i=0; i<seconds; i++) {
			if (!isMenuOpen(0)) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	
}
