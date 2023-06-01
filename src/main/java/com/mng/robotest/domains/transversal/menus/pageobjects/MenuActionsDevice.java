package com.mng.robotest.domains.transversal.menus.pageobjects;

import java.util.Arrays;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.test.pageobject.shop.filtros.FiltroMobil;
import com.mng.robotest.test.pageobject.shop.filtros.SecMultiFiltrosDevice;

public class MenuActionsDevice extends PageBase implements MenuActions {

	private final MenuWeb menu;
	
	//TODO eliminar el OLD cuando suba la nueva versión a PRO (31-05-2023)
	private String getXPathMenu() {
		return "(" + 
				getXPathMenu("header.subMenu.item", "") + " | " + 
				getXPathMenu("menu.family", ".link") + ")";
	}
	
	private String getXPathMenu(String dataTestid, String sufix) {
		String idLinea = menu.getLinea().name().toLowerCase();
		if (menu.getSublinea()!=null) {
			idLinea = menu.getSublinea().getId(app);
		}
		
		String nameMenu = menu.getMenu().toLowerCase();
		String xpath =  
				"//ul/li//a[@data-testid='" + dataTestid + "." + 
			nameMenu + "_" + idLinea + sufix + "' or text()='" + menu.getMenu() + "'";
		
		if (nameMenu.contains(" ")) {
			String menuIni = nameMenu.substring(0, menu.getMenu().indexOf(" "));
			xpath+=" or @data-testid='" + dataTestid + "." + menuIni + "_" + idLinea + sufix + "'"; 
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
				.isAvailableFiltros(FiltroMobil.FAMILIA, menu.getSubMenus());
	}

	private void clickGroup() {
		new GroupWeb(menu.getLinea(), menu.getSublinea(), menu.getGroup())
			.click();
	}
	
	private String clickMenu() {
		String menuItem = getElement(getXPathMenu()).getText();
		click(getXPathMenu()).exec();
		return menuItem;
	}
	
	private void clickSubLevelMenu() {
		new SecMultiFiltrosDevice().selectMenu2onLevel(Arrays.asList(menu.getSubMenu()));
	}
}
