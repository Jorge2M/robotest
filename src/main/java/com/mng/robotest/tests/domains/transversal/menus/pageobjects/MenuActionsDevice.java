package com.mng.robotest.tests.domains.transversal.menus.pageobjects;

import java.util.Arrays;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.device.FiltroMobil;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.device.SecMultiFiltrosDevice;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

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
		return SecMultiFiltrosDevice.make(app, dataTest.getPais())
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
		SecMultiFiltrosDevice.make(app, dataTest.getPais())
			.selectMenu2onLevel(Arrays.asList(menu.getSubMenu()));
	}
}
