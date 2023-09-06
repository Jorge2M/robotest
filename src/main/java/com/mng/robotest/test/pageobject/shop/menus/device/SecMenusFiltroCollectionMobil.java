package com.mng.robotest.test.pageobject.shop.menus.device;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.galeria.pageobjects.FilterCollection;
import com.mng.robotest.domains.galeria.pageobjects.SecFiltros;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusFiltroCollection;

public class SecMenusFiltroCollectionMobil extends PageBase implements SecMenusFiltroCollection {

	private final SecFiltros secFiltros = SecFiltros.make(channel, app, dataTest.getPais());
	
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
