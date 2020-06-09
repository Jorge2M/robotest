package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public abstract class SecCabeceraOutlet extends SecCabecera {

	protected SecCabeceraOutlet(Channel channel, AppEcom app, WebDriver driver) {
		super(channel, app, driver);
	}
	
	public static SecCabeceraOutlet getNew(Channel channel, AppEcom app, WebDriver driver) {
		switch (channel) {
		case desktop:
			return SecCabeceraOutletDesktop.getNew(channel, app, driver);
		case mobile:
		default:
			return SecCabeceraOutletMobil.getNew(channel, app, driver);
		}
	}
	
}
