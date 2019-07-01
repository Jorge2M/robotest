package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public class SecMenuSuperiorDesktop {
	
	public final SecLineasMenuDesktop secLineas;
	public final SecBloquesMenuDesktop secBlockMenus;
	public final SecCarruselDesktop secCarrusel;
	
	private SecMenuSuperiorDesktop(AppEcom app, WebDriver driver) {
		secLineas = SecLineasMenuDesktop.getNew(app, driver);
		secBlockMenus = SecBloquesMenuDesktop.getNew(app, driver);
		secCarrusel = SecCarruselDesktop.getNew();
	}
	
	public static SecMenuSuperiorDesktop getNew(AppEcom app, WebDriver driver) {
		return (new SecMenuSuperiorDesktop(app, driver));
	}
}
