package com.mng.robotest.tests.domains.manto.pageobjects;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.data.TiendaManto;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageSelTda extends PageBase {

	private static final String XP_CELDA_TEXT_SELECT_ENTORNO = "//td[text()[contains(.,'Seleccion de Entorno')]]";

	private String getXpathLinkTienda(TiendaManto tienda) {
		return ("//a[text()[contains(.,'" + tienda.getLitPantManto() + "')]]");
	}

	public boolean isPage() {
		return isPage(0);
	}
	public boolean isPage(int seconds) {
		return state(Present, XP_CELDA_TEXT_SELECT_ENTORNO).wait(seconds).check();
	}

	public void selectTienda(TiendaManto tienda) {
		click(getXpathLinkTienda(tienda)).type(TypeClick.webdriver).exec();
	}
}
