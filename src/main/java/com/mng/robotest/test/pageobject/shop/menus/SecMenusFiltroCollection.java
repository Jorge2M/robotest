package com.mng.robotest.test.pageobject.shop.menus;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.domains.galeria.pageobjects.FilterCollection;
import com.mng.robotest.test.pageobject.shop.menus.desktop.SecMenusFiltroCollectionDesktop;
import com.mng.robotest.test.pageobject.shop.menus.device.SecMenusFiltroCollectionMobil;

public interface SecMenusFiltroCollection {
	
	public abstract boolean isVisible();
	public abstract boolean isVisibleMenu(FilterCollection typeMenu);
	public abstract void click(FilterCollection typeMenu);
	
	public static SecMenusFiltroCollection make(Channel channel, AppEcom app) {
		switch (channel) {
		case mobile:
			return new SecMenusFiltroCollectionMobil(app);
		case desktop:
		default:
			return new SecMenusFiltroCollectionDesktop();
		}
	}
}