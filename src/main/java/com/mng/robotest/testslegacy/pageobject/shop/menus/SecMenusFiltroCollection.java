package com.mng.robotest.testslegacy.pageobject.shop.menus;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.galeria.pageobjects.FilterCollection;
import com.mng.robotest.testslegacy.pageobject.shop.menus.desktop.SecMenusFiltroCollectionDesktop;
import com.mng.robotest.testslegacy.pageobject.shop.menus.device.SecMenusFiltroCollectionMobil;

public interface SecMenusFiltroCollection {
	
	public abstract boolean isVisible();
	public abstract boolean isVisibleMenu(FilterCollection typeMenu);
	public abstract void click(FilterCollection typeMenu);
	
	public static SecMenusFiltroCollection make(Channel channel) {
		switch (channel) {
		case mobile:
			return new SecMenusFiltroCollectionMobil();
		case desktop:
		default:
			return new SecMenusFiltroCollectionDesktop();
		}
	}
}