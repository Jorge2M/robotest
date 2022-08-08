package com.mng.robotest.test.pageobject.shop.menus.desktop;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;


public class SecMenuSuperiorDesktop {
	
	public final SecLineasMenuDesktop secLineas;
	public final SecBloquesMenuDesktop secBlockMenus;
	
	private SecMenuSuperiorDesktop(AppEcom app, Channel channel) {
		secLineas = SecLineasMenuDesktop.factory(app, channel);
		secBlockMenus = SecBloquesMenuDesktop.factory(app, channel);
	}
	
	public static SecMenuSuperiorDesktop getNew(AppEcom app, Channel channel) {
		return (new SecMenuSuperiorDesktop(app, channel));
	}
}
