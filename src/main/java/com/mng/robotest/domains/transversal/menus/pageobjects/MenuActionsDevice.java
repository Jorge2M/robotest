package com.mng.robotest.domains.transversal.menus.pageobjects;

import java.util.Arrays;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.filtros.FiltroMobil;
import com.mng.robotest.test.pageobject.shop.filtros.SecMultiFiltrosDevice;

public class MenuActionsDevice extends PageBase implements MenuActions {

	private final MenuWeb menu;
	
	private String getXPathMenu() {
		return 
			"//ul/li//a[@data-testid='header.subMenu." + 
			menu.getMenu().toLowerCase() + "_" + menu.getLinea() + "']";
	}
	
	public MenuActionsDevice(MenuWeb menu) {
		this.menu = menu;
	}
	
	@Override
	public void click() {
		clickGroup();
		clickMenu();
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
	
	private void clickMenu() {
		click(getXPathMenu()).exec();
	}
	
	private void clickSubLevelMenu() {
		new SecMultiFiltrosDevice().selectMenu2onLevel(Arrays.asList(menu.getSubMenu()));
	}
}
