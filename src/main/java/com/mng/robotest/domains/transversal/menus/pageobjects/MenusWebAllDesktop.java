package com.mng.robotest.domains.transversal.menus.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;

public class MenusWebAllDesktop extends PageBase implements MenusWebAll {

	private static final String XPATH_WRAPPER_MENU = "//div[@id[contains(.,'SubMenu')]]";
	private static final String XPATH_MENU_ITEM = 
			"//li[@data-testid[contains(.,'header.section.link')] and " + 
			     "string-length(normalize-space(@class))>0]";
	
	@Override
	public boolean isMenuInState(boolean open, int seconds) {
		if (open) {
			return isMenuOpen(seconds);
		}
		return isMenuClose(seconds);
	}
	
	private String getXPathMenuItem(LineaType linea) {
		return XPATH_MENU_ITEM + "//self::*[@id[contains(.,'_" + linea + "')]]";
	}
	
	@Override
	public List<MenuWeb> getMenus(GroupWeb groupWeb) {
		groupWeb.click();
		return getVisibleMenus(groupWeb);
	}
	
	private List<MenuWeb> getVisibleMenus(GroupWeb groupWeb) {
		List<MenuWeb> menus = new ArrayList<>();
		List<WebElement> menuElements = getElements(getXPathMenuItem(groupWeb.getLinea()));
		for (WebElement menuElement : menuElements) {
			menus.add(new MenuWeb
					.Builder(menuElement.findElement(By.xpath(".//span/span")).getText())
					.linea(groupWeb.getLinea())
					.sublinea(groupWeb.getSublinea())
					.group(groupWeb.getGroup())
					.build());
		}
		return menus;
	}
	
	private boolean isMenuOpen(int seconds) {
		return state(State.Visible, XPATH_WRAPPER_MENU).wait(seconds).check();
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
