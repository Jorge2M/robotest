package com.mng.robotest.test80.mango.test.pageobject.shop.buscador;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public interface SecSearch {
	
	public void search(String text) throws Exception;
	public void close() throws Exception;
	
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
		case movil_web:
			switch (app) {
			case shop:
			case votf:
				return SecSearchMobilShop.getNew(driver);
			case outlet:
				return SecSearchMobilShop.getNew(driver);
			}
		}
		
		return null;
	}
}
