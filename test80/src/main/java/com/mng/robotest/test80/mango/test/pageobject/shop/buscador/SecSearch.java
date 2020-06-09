package com.mng.robotest.test80.mango.test.pageobject.shop.buscador;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public interface SecSearch {
	
	public void search(String text);
	public void close();
	
	public static SecSearch getNew(Channel channel, AppEcom app, WebDriver driver) {
		switch (channel) {
		case desktop:
			switch (app) {
			case shop:
			case votf:
				return SecSearchDesktopShop.getNew(driver);
			case outlet:
				return SecSearchDesktopOutlet.getNew(driver);
			}
		case mobile:
			switch (app) {
			case shop:
			case votf:
				return SecSearchMobilShop.getNew(driver);
			case outlet:
				return SecSearchMobilOutlet.getNew(driver);
			}
		}
		
		return null;
	}
}
