package com.mng.robotest.test.pageobject.shop.menus.mobil;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.conftestmaker.AppEcom;

public abstract class SecLineasTablet extends SecLineasDevice {

	public static SecLineasTablet getNew(AppEcom app, WebDriver driver) {
		switch (app) {
		case outlet:
			return new SecLineasTabletOutlet();
		default:
			return new SecLineasTabletShop();
		}
	}
	
}
