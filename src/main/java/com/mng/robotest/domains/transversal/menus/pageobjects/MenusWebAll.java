package com.mng.robotest.domains.transversal.menus.pageobjects;

import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;

public interface MenusWebAll {
	
	public boolean isMenuInState(boolean open, int seconds);
	public List<MenuWeb> getMenus(GroupWeb group);
	
	public static MenusWebAll make(Channel channel) {
		if (channel.isDevice()) {
			return new MenusWebAllDevice();
		}
		return new MenusWebAllDesktop();
	}
}
