package com.mng.robotest.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;

public class SecMenuSuperiorDesktop {
	
	public final SecLineasMenuDesktop secLineas;
	public final SecBloquesMenuDesktop secBlockMenus;
	
	private SecMenuSuperiorDesktop(AppEcom app, Channel channel, WebDriver driver) {
		secLineas = SecLineasMenuDesktop.factory(app, channel, driver);
		secBlockMenus = SecBloquesMenuDesktop.factory(app, channel, driver);
	}
	
	public static SecMenuSuperiorDesktop getNew(AppEcom app, Channel channel, WebDriver driver) {
		return (new SecMenuSuperiorDesktop(app, channel, driver));
	}
}
