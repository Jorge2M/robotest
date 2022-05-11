package com.mng.robotest.test.pageobject.shop.menus.mobil;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;

public abstract class SecLineasTablet extends SecLineasDevice {

	public static SecLineasTablet getNew(AppEcom app, WebDriver driver) {
		switch (app) {
		case outlet:
			return new SecLineasTabletOutlet(driver);
		default:
			return new SecLineasTabletShop(driver);
		}
	}
	
	public SecLineasTablet(AppEcom app, WebDriver driver) {
		super(Channel.tablet, app, driver);
	}

}
