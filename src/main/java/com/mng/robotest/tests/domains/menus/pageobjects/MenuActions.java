package com.mng.robotest.tests.domains.menus.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.menus.pageobjects.currentmenus.MenuActionsDesktopCurrent;
import com.mng.robotest.tests.domains.menus.pageobjects.currentmenus.MenuActionsDeviceCurrent;
import com.mng.robotest.tests.domains.menus.pageobjects.newmenus.MenuActionsDesktopNew;
import com.mng.robotest.tests.domains.menus.pageobjects.newmenus.MenuActionsDeviceNew;
import com.mng.robotest.testslegacy.beans.Pais;

public interface MenuActions {

	public String click();
	public void clickSubMenu();
	public boolean isVisibleMenu();
	public boolean isVisibleSubMenus();
	
	public static MenuActions make(MenuWeb menu, Channel channel, Pais pais, AppEcom app) {
		if (pais.isNewmenu(app)) {
			if (channel.isDevice()) {
				return new MenuActionsDeviceNew(menu);
			}
			return new MenuActionsDesktopNew(menu);
		}
		if (channel.isDevice()) {
			return new MenuActionsDeviceCurrent(menu);
		}
		return new MenuActionsDesktopCurrent(menu);
	}
	
}
