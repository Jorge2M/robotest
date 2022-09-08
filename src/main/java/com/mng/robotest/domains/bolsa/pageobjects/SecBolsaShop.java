package com.mng.robotest.domains.bolsa.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;

public class SecBolsaShop extends SecBolsaCommon {
 
	private final LineasArtBolsa lineasArtBolsa = new LineasArtBolsaNewNew();
	
	private static final String XPATH_PANEL_BOLSA_DESKTOP = "//*[@data-testid='bag.opened']";
	private static final String XPATH_PANEL_BOLSA_MOBILE = "//*[@data-testid='bagpage.container']";
	private static final String XPATH_BOTON_COMPRAR = "//button[@data-testid[contains(.,'checkout.button')]]";
	private static final String XPATH_PRECIO_SUBTOTAL_DESKTOP = "//*[@data-testid='bag.preview.summary.price']";
	private static final String XPATH_PRECIO_SUBTOTAL_MOBILE = "//*[@data-testid='bag.preview.summary.price']";

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
		if (channel==Channel.mobile) {
			return XPATH_PRECIO_SUBTOTAL_MOBILE;
		}
		return XPATH_PRECIO_SUBTOTAL_DESKTOP;
	}  
	
	@Override
	public LineasArtBolsa getLineasArtBolsa() {
		return lineasArtBolsa;
	}

}
