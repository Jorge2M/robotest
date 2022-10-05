package com.mng.robotest.domains.transversal.menus.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.galeria.SecSubmenusGallery;

public class MenuActionsDesktop extends PageBase implements MenuActions {

	private final MenuWeb menu;
	
	private String getXPathMenu() {
		return 
			"//ul/li//a[@data-testid='header.section.link." + 
			menu.getMenu().toLowerCase() + "_" + menu.getLinea() + "']";
	}	
	
	public MenuActionsDesktop(MenuWeb menu) {
		this.menu = menu;
	}
	
	@Override
	public void click() {
		clickGroup();
		clickMenuSuperior();
	}
	@Override
	public void clickSubMenu() {
		new SecSubmenusGallery().clickSubmenu(menu.getSubMenu());
	}
	@Override
	public boolean isVisibleMenu() {
		return state(Visible, getXPathMenu()).check();
	}
	@Override
	public boolean isVisibleSubMenus() {
		var secSubmenusGallery = new SecSubmenusGallery();
		for (String subMenu : menu.getSubMenus()) {
			if (!secSubmenusGallery.isVisibleSubmenu(subMenu)) {
				return false;
			}
		}
		return true;
	}

	private void clickGroup() {
		GroupWeb group = new GroupWeb(menu.getLinea(), menu.getSublinea(), menu.getGroup());
		group.click();
	}
	
	private void clickMenuSuperior() {
		click(getXPathMenu()).exec();
	}	

}
