package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public abstract class SecCabeceraShop extends SecCabecera {

	protected SecCabeceraShop(Channel channel, AppEcom app, WebDriver driver) {
		super(channel, app, driver);
	}
	
	public static SecCabecera getNew(Channel channel, AppEcom app, WebDriver driver) {
		switch (channel) {
		case tablet:
			return SecCabeceraShop_Tablet.getNew(driver);
		case desktop:
		case mobile:
		default:
			return SecCabeceraShop_DesktopMobile.getNew(channel, driver);
		}
	}
	
}
