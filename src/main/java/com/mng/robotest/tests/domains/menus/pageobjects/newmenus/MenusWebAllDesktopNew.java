package com.mng.robotest.tests.domains.menus.pageobjects.newmenus;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.menus.pageobjects.GroupWeb;
import com.mng.robotest.tests.domains.menus.pageobjects.MenuWeb;
import com.mng.robotest.tests.domains.menus.pageobjects.MenusWebAll;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class MenusWebAllDesktopNew extends PageBase implements MenusWebAll {

	private static final String XP_WRAPPER_MENU = "//div[@id[contains(.,'subMenu')]]";
	
	private static final String XP_MENU_ITEM = 
			"//li[@data-testid[contains(.,'menu.family.')] and " + 
			     "string-length(normalize-space(@class))>0]/a[@data-testid[contains(.,'.link')]]/..";	
	
	@Override
	public boolean isMenuInState(boolean open, int seconds) {
		if (open) {
			return isMenuOpen(seconds);
		}
		return isMenuClose(seconds);
	}
	
	private String getXPathMenuItem(String idLinea) {
		return XP_MENU_ITEM + "/*[@data-testid[contains(.,'_" + idLinea + "')]]/.."; 
	}
	
	@Override
	public List<MenuWeb> getMenus(GroupWeb groupWeb) {
		groupWeb.click();
		return getVisibleMenus(groupWeb);
	}
	
	private List<MenuWeb> getVisibleMenus(GroupWeb groupWeb) {
		List<MenuWeb> menus = new ArrayList<>();
		var menuElements = getElements(getXPathMenuItem(getIdLinea(groupWeb)));
		for (var menuElement : menuElements) {
			String name = getNameMenu(menuElement);
			if ("".compareTo(name)!=0) {
				menus.add(new MenuWeb
						.Builder(getNameMenu(menuElement))
						.linea(groupWeb.getLinea())
						.sublinea(groupWeb.getSublinea())
						.group(groupWeb.getGroup())
						.build());
			}
		}
		return menus;
	}
	
	public String getIdLinea(GroupWeb group) {
		var linea = group.getLinea();
		var sublinea = group.getSublinea();
		if (sublinea!=null) {
			return sublinea.getId(AppEcom.shop);
		}
		return linea.getId2();
	}

	private String getNameMenu(WebElement menuElement) {
		return menuElement.findElement(By.xpath(".//a")).getText();
	}
	
	private boolean isMenuOpen(int seconds) {
		return state(VISIBLE, XP_WRAPPER_MENU).wait(seconds).check();
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
