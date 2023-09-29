package com.mng.robotest.tests.domains.galeria.pageobjects;

public class PageGaleriaDesktopNormal extends PageGaleriaDesktop {

	private static final String XPATH_ARTICULO = "//li[@id[contains(.,'product-key-id')]]";
	
	public PageGaleriaDesktopNormal() {
		super();
	}
	
	public PageGaleriaDesktopNormal(From from) {
		super(from);
	}
	
	@Override
	protected String getXPathArticulo() {
		return XPATH_ARTICULO;
	}

}
