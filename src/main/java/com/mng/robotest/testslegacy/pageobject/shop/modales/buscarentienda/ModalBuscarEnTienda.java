package com.mng.robotest.testslegacy.pageobject.shop.modales.buscarentienda;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.footer.pageobjects.PageFromFooter;
import com.mng.robotest.testslegacy.beans.Pais;

public abstract class ModalBuscarEnTienda extends PageBase implements PageFromFooter {

	public abstract boolean isVisible(int seconds);
	public abstract boolean isInvisible(int seconds);
	public abstract boolean isPresentAnyTiendaUntil(int seconds);
	public abstract void close();
	
	public static ModalBuscarEnTienda make() {
		return new ModalBuscarEnTiendaGenesis();
	}
	
	@Override
	public String getName() {
		return "Encuentra tu tienda";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return isVisible(seconds);
	}

}
