package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.SecFiltros;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusFiltroCollection;


public class SecMenusFiltroCollectionMobil extends SeleniumUtils implements SecMenusFiltroCollection {

	private final SecFiltros secFiltros;
    
    public SecMenusFiltroCollectionMobil(AppEcom app, WebDriver driver) {
    	this.secFiltros = SecFiltros.make(Channel.mobile, app, driver);
    }
    
    @Override
    public boolean isVisible() {
    	return (secFiltros.isClickableFiltroUntil(0));
    }
    
    @Override
    public boolean isVisibleMenu(FilterCollection typeMenu) {
    	try {
    		return (secFiltros.isCollectionFilterPresent());
    	}
    	catch (Exception e) {
    		return false;
    	}
    }
    
    @Override
    public void click(FilterCollection collection) {
    	secFiltros.selectCollection(collection);
    }
}
