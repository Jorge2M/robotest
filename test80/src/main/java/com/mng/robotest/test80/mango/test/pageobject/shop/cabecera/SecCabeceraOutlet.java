package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public abstract class SecCabeceraOutlet extends SecCabecera {

	protected SecCabeceraOutlet(Channel channel, AppEcom app, WebDriver driver) {
		super(channel, app, driver);
	}
	
	public static SecCabecera getNew(Channel channel, AppEcom app, WebDriver driver) {
		switch (channel) {
		case desktop:
			return SecCabeceraShop_DesktopMobile.getNew(channel, app, driver);
		case mobile:
		default:
			return SecCabeceraOutlet_Mobil.getNew(channel, app, driver);
		}
	}
	
}
