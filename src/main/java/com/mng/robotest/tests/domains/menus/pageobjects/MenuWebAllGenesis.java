package com.mng.robotest.tests.domains.menus.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;

public class MenuWebAllGenesis extends PageBase implements MenusWebAll {

	private static final String XP_MENU_ITEM = "//li/a[@data-testid[contains(.,'menu.family')]]";
	
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
