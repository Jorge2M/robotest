package com.mng.robotest.tests.domains.menus.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;

public interface MenuActions {

	public String click();
	public void clickSubMenu();
	public boolean isVisibleMenu();
	public boolean isVisibleSubMenus();
	
	public static MenuActions make(MenuWeb menu, Channel channel) {
		if (channel.isDevice()) {
			return new MenuActionsDevice(menu);
		}
		return new MenuActionsDesktop(menu);
	}
	
}
