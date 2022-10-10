package com.mng.robotest.test.pageobject.shop.menus.device;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test.pageobject.shop.filtros.SecFiltros;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusFiltroCollection;


public class SecMenusFiltroCollectionMobil extends PageBase implements SecMenusFiltroCollection {

	private final SecFiltros secFiltros;
	
	public SecMenusFiltroCollectionMobil(AppEcom app) {
		this.secFiltros = SecFiltros.make(Channel.mobile, app);
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
