package com.mng.robotest.test80.mango.test.pageobject.shop.buscador;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public interface SecSearch {
	
	public void search(String text);
	public void close();
	
	public static SecSearch getNew(Channel channel, AppEcom app, WebDriver driver) {
		if (channel.isDevice()) {
			if (channel==Channel.tablet) {
				return SecSearchDeviceShop.getNew(driver);
			}
			if (app==AppEcom.outlet) {
				return SecSearchMobilOutlet.getNew(driver);
			}
			return SecSearchDeviceShop.getNew(driver);
		}
		return SecSearchDesktopShop.getNew(app, driver);
	}
}
