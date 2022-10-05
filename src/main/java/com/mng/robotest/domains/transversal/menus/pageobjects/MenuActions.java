package com.mng.robotest.domains.transversal.menus.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;

public interface MenuActions {

	public void click();
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
