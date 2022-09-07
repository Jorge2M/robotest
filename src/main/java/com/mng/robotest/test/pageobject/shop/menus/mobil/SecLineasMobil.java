package com.mng.robotest.test.pageobject.shop.menus.mobil;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea.LineaType;

public abstract class SecLineasMobil extends SecLineasDevice {

	protected static final String[] TAGS_REBAJAS_MOBIL = {"mujer", "hombre", "ninos", "teen"};

	protected static final String XPATH_HEADER_MOBILE = "//div[@id='headerMobile']";
	protected static final String XPATH_CAPA_LEVEL_LINEA = "//div[@class[contains(.,'menu-section')]]";
	
	public static SecLineasMobil getNew(AppEcom app) {
		switch (app) {
		case outlet:
			return new SecLineasMobilOutlet();
		default:
			return new SecLineasMobilShop();
		}
	}
	
	public SecLineasMobil() {
		super();
	}
	
	public String getXPathHeaderMobile() {
		return XPATH_HEADER_MOBILE;
	}

	public boolean isLineaPresentUntil(LineaType lineaType, int maxSeconds) {
		String xpathLinea = getXPathLineaLink(lineaType);
		return state(Present, xpathLinea).wait(maxSeconds).check();
	}

}
