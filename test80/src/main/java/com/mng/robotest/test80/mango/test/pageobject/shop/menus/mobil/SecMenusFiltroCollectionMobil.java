package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.SecFiltros;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusFiltroCollection;

@SuppressWarnings("javadoc")
public class SecMenusFiltroCollectionMobil extends WebdrvWrapp implements SecMenusFiltroCollection {

	private final SecFiltros secFiltros;
    
    public SecMenusFiltroCollectionMobil(AppEcom app, WebDriver driver) throws Exception {
    	this.secFiltros = SecFiltros.newInstance(Channel.movil_web, app, driver);
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
    public void click(FilterCollection collection) throws Exception {
    	secFiltros.selectCollection(collection);
    }
}
