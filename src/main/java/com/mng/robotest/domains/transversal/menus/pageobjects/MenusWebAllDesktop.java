package com.mng.robotest.domains.transversal.menus.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

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
		return "(" + getXPathMenuItemOld(linea) + " | " + getXPathMenuItemNew(linea) + ")"; 
	}
	
	//TODO se puede eliminar cuando los nuevos menús suban a producción (28-04-23)
	private String getXPathMenuItemOld(LineaType linea) {
		return XPATH_MENU_ITEM + "//self::*[@id[contains(.,'_" + linea.toString().toLowerCase() + "')]]";
	}
	private String getXPathMenuItemNew(LineaType linea) {
		return XPATH_MENU_ITEM + "/*[@data-testid[contains(.,'_" + linea.toString().toLowerCase() + "')]]/..";
	}	
	
	@Override
	public List<MenuWeb> getMenus(GroupWeb groupWeb) {
		groupWeb.click();
		return getVisibleMenus(groupWeb);
	}
	
	private List<MenuWeb> getVisibleMenus(GroupWeb groupWeb) {
		List<MenuWeb> menus = new ArrayList<>();
		List<WebElement> menuElements = getElements(getXPathMenuItem(groupWeb.getLinea()));
		boolean isOldMenu = isOldMenu(menuElements);
		for (WebElement menuElement : menuElements) {
			menus.add(new MenuWeb
					.Builder(getNameMenu(menuElement, isOldMenu))
					.linea(groupWeb.getLinea())
					.sublinea(groupWeb.getSublinea())
					.group(groupWeb.getGroup())
					.build());
		}
		return menus;
	}

	//TODO se puede eliminar la parte del OldMenu cuando los nuevos menús suban a PRO (28-04-23)
	private boolean isOldMenu(List<WebElement> menuElements) {
		if (menuElements.isEmpty()) {
			return false;
		}
		return state(Present, menuElements.get(0)).by(By.xpath(".//span/span")).check();
	}
	private String getNameMenu(WebElement menuElement, boolean isOldMenu) {
		if (isOldMenu) {
			return menuElement.findElement(By.xpath(".//span/span")).getText();	
		}
		return menuElement.findElement(By.xpath(".//a")).getText();
	}
	
	private boolean isMenuOpen(int seconds) {
		return state(Visible, XPATH_WRAPPER_MENU).wait(seconds).check();
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
