package com.mng.robotest.test.pageobject.shop.menus;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test.pageobject.shop.menus.desktop.SecMenusFiltroCollectionDesktop;
import com.mng.robotest.test.pageobject.shop.menus.mobil.SecMenusFiltroCollectionMobil;

public interface SecMenusFiltroCollection {
	public abstract boolean isVisible();
	public abstract boolean isVisibleMenu(FilterCollection typeMenu);
	public abstract void click(FilterCollection typeMenu);
	
	public static SecMenusFiltroCollection make(Channel channel, AppEcom app, WebDriver driver) {
		switch (channel) {
		case mobile:
			return new SecMenusFiltroCollectionMobil(app, driver);
		case desktop:
		default:
			return new SecMenusFiltroCollectionDesktop(driver);
		}
	}
}