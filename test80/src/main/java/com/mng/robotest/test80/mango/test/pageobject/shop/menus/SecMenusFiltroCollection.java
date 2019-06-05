package com.mng.robotest.test80.mango.test.pageobject.shop.menus;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusFiltroCollectionDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenusFiltroCollectionMobil;

public interface SecMenusFiltroCollection {
    abstract public boolean isVisible();
    abstract public boolean isVisibleMenu(FilterCollection typeMenu);
    abstract public void click(FilterCollection typeMenu) throws Exception;
    
    public static SecMenusFiltroCollection make(Channel channel, AppEcom app, WebDriver driver) throws Exception {
    	switch (channel) {
    	case movil_web:
    		return new SecMenusFiltroCollectionMobil(app, driver);
    	case desktop:
    	default:
    		return new SecMenusFiltroCollectionDesktop(driver);
    	}
    }
}