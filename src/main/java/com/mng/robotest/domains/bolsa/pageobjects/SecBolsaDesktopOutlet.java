package com.mng.robotest.domains.bolsa.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;

public class SecBolsaDesktopOutlet extends SecBolsaCommon {
 
	private final LineasArtBolsa lineasArtBolsa = new LineasArtBolsaNew();
	
	private static final String XPATH_PANEL_BOLSA_DESKTOP = "//*[@id='openedShoppingBag']";
	private static final String XPATH_PANEL_BOLSA_MOBILE = "//div[@class[contains(.,'m_bolsa')]]";
	
	private static final String DATAID_TABLET = "bag.fullPage.checkout.button";
	private static final String DATAID_DESKTOP = "bag.preview.checkout.button";
	private static final String XPATH_BOTON_COMPRAR = 
			"//button[@data-testid='" + DATAID_TABLET + "' or @data-testid='" + DATAID_DESKTOP + "']";

	@Override
	String getXPathPanelBolsa() {
		if (channel==Channel.mobile) {
			return XPATH_PANEL_BOLSA_MOBILE;
		}
		return XPATH_PANEL_BOLSA_DESKTOP;
	}
	
	@Override
	String getXPathBotonComprar() {
		return XPATH_BOTON_COMPRAR; 
	}
	
	@Override
	String getXPathPrecioSubTotal() {
		String xpathBolsa = getXPathPanelBolsa();
		return  
			xpathBolsa + 
			"//div[@class[contains(.,'layout-content')]]" + 
			"//div[@class[contains(.,'right')]]/div";
	}  
	
	@Override
	public LineasArtBolsa getLineasArtBolsa() {
		return lineasArtBolsa;
	}

}
