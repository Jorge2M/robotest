package com.mng.robotest.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.conftestmaker.AppEcom;

public class SecMenuSuperiorDesktop {
	
	public final SecLineasMenuDesktop secLineas;
	public final SecBloquesMenuDesktop secBlockMenus;
	
	private SecMenuSuperiorDesktop(AppEcom app, WebDriver driver) {
		secLineas = SecLineasMenuDesktop.factory(app, driver);
		secBlockMenus = SecBloquesMenuDesktop.factory(app, driver);
	}
	
	public static SecMenuSuperiorDesktop getNew(AppEcom app, WebDriver driver) {
		return (new SecMenuSuperiorDesktop(app, driver));
	}
}
