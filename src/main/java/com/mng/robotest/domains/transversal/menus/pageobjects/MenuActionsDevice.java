package com.mng.robotest.domains.transversal.menus.pageobjects;

import java.util.Arrays;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.filtros.FiltroMobil;
import com.mng.robotest.test.pageobject.shop.filtros.SecMultiFiltrosDevice;

public class MenuActionsDevice extends PageBase implements MenuActions {

	private final MenuWeb menu;
	
	private String getXPathMenu() {
		String idLinea = menu.getLinea().name().toLowerCase();
		if (menu.getSublinea()!=null) {
			idLinea = menu.getSublinea().getId(app);
		}
		
		String nameMenu = menu.getMenu().toLowerCase();
		String xpath =  
				"//ul/li//a[@data-testid='header.subMenu.item." + 
			nameMenu + "_" + idLinea + "'";
		
		if (nameMenu.contains(" ")) {
			String menuIni = nameMenu.substring(0, menu.getMenu().indexOf(" "));
			xpath+=" or @data-testid='header.subMenu.item." + menuIni + "_" + idLinea + "'"; 
		}
		xpath+="]";
		
		return xpath;
	}	
	
	public MenuActionsDevice(MenuWeb menu) {
		this.menu = menu;
	}
	
	@Override
	public String click() {
		clickGroup();
		return clickMenu();
	}
	
	@Override
	public void clickSubMenu() {
		click();
		clickSubLevelMenu();
	}
	
	@Override
	public boolean isVisibleMenu() {
		return state(Visible, getXPathMenu()).check();
	}
	@Override
	public boolean isVisibleSubMenus() {
		return new SecMultiFiltrosDevice()
				.isAvailableFiltros(FiltroMobil.Familia, menu.getSubMenus());
	}

	private void clickGroup() {
		GroupWeb group = new GroupWeb(menu.getLinea(), menu.getSublinea(), menu.getGroup());
		group.click();
	}
	
	private String clickMenu() {
		String menu = getElement(getXPathMenu()).getText();
		click(getXPathMenu()).exec();
		return menu;
	}
	
	private void clickSubLevelMenu() {
		new SecMultiFiltrosDevice().selectMenu2onLevel(Arrays.asList(menu.getSubMenu()));
	}
}
