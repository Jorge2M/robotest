package com.mng.robotest.domains.transversal.menus.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;

public interface MenuActions {

	public void click();
	public boolean isVisible();
	
	public static MenuActions make(Menu menu, Channel channel) {
		if (channel.isDevice()) {
			return new MenuActionsDevice(menu);
		}
		return new MenuActionsDesktop();
	}
	
}
