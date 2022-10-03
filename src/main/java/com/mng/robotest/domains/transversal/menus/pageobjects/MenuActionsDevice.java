package com.mng.robotest.domains.transversal.menus.pageobjects;

import com.mng.robotest.domains.transversal.PageBase;

public class MenuActionsDevice extends PageBase implements MenuActions {

	
	private final Menu menu;
	
	public MenuActionsDevice(Menu menu) {
		this.menu = menu;
	}
	
	@Override
	public void click() {
		clickGroup();
		clickMenu();
	}
	
	@Override
	public boolean isVisible() {
		return true;
	}

	private void clickGroup() {
		Group group = new Group(menu.getLinea(), menu.getSublinea(), menu.getGroup());
		group.click();
	}
	
	private void clickMenu() {
		
	}
}
