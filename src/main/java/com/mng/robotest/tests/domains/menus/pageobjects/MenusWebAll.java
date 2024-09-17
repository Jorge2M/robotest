package com.mng.robotest.tests.domains.menus.pageobjects;

import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.menus.pageobjects.currentmenus.MenusWebAllDeviceCurrent;
import com.mng.robotest.tests.domains.menus.pageobjects.newmenus.MenusWebAllDesktopNew;
import com.mng.robotest.tests.domains.menus.pageobjects.newmenus.MenusWebAllDeviceNew;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.menus.pageobjects.currentmenus.MenusWebAllDesktopCurrent;
import com.mng.robotest.testslegacy.beans.Pais;

public interface MenusWebAll {
	
	public boolean isMenuInState(boolean open, int seconds);
	public List<MenuWeb> getMenus(GroupWeb group);
	
	public static MenusWebAll make(Channel channel, Pais pais, AppEcom app) {
		if (pais.isNewmenu(app)) {
			if (channel.isDevice()) {
				return new MenusWebAllDeviceNew();
			}
			return new MenusWebAllDesktopNew();			
		}
		if (channel.isDevice()) {
			return new MenusWebAllDeviceCurrent();
		}
		return new MenusWebAllDesktopCurrent();
	}
	
}
