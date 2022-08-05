package com.mng.robotest.test.pageobject.shop.buscador;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;

public interface SecSearch {
	
	public void search(String text);
	public void close();
	
	public static SecSearch getNew(Channel channel, AppEcom app, WebDriver driver) {
		if (channel.isDevice()) {
			if (channel==Channel.tablet) {
				return new SecSearchDeviceShop();
			}
			if (app==AppEcom.outlet) {
				return new SecSearchMobilOutlet();
			}
			return new SecSearchDeviceShop();
		}
		return new SecSearchDesktop();
	}
}
