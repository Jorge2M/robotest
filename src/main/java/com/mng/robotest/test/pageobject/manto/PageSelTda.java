package com.mng.robotest.test.pageobject.manto;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.test.data.TiendaManto;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageSelTda extends PageBase {

	private static final String XPATH_CELDA_TEXT_SELECT_ENTORNO = "//td[text()[contains(.,'Seleccion de Entorno')]]";

	private String getXpathLinkTienda(TiendaManto tienda) {
		return ("//a[text()[contains(.,'" + tienda.getLitPantManto() + "')]]");
	}

	public boolean isPage() {
		return isPage(0);
	}
	public boolean isPage(int seconds) {
		return state(Present, XPATH_CELDA_TEXT_SELECT_ENTORNO).wait(seconds).check();
	}

	public void selectTienda(TiendaManto tienda) {
		click(getXpathLinkTienda(tienda)).type(TypeClick.webdriver).exec();
	}
}
