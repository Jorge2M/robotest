package com.mng.robotest.test.pageobject.shop.bolsa;

import com.github.jorge2m.testmaker.conf.Channel;

public class SecBolsaNew extends SecBolsaDesktop {
 
	private final LineasArtBolsa lineasArtBolsa = new LineasArtBolsaNew();
	
	private static final String XPATH_PANEL_BOLSA_DESKTOP = "//div[@id='openedShoppingBag']";
	private static final String XPATH_PANEL_BOLSA_MOBILE = "//div[@class[contains(.,'m_bolsa')]]";
	
	//TODO eliminar el 1o cuando suba a PRO
	private static final String XPATH_BOTON_COMPRAR = 
		"//button[@data-testid='bag.fullpage.checkout.button' or @data-testid='bag.preview.checkout.button']";

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
