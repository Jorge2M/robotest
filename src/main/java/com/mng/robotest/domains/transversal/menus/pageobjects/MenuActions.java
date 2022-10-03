package com.mng.robotest.domains.transversal.menus.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;

public interface MenuActions {

	public void click(Menu menu);
	public boolean isVisible(Menu menu);
	
	public static MenuActions make(Channel channel) {
		if (channel.isDevice()) {
			return new MenuActionsDevice();
		}
		return new MenuActionsDesktop();
	}
	
}
