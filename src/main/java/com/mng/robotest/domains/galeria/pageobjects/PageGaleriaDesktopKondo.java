package com.mng.robotest.domains.galeria.pageobjects;

public class PageGaleriaDesktopKondo extends PageGaleriaDesktop {

	private static final String XPATH_ARTICULO = "//div[@data-testid='plp.product.figure']/..";	
	
	public PageGaleriaDesktopKondo() {
		super();
	}
	
	public PageGaleriaDesktopKondo(From from) {
		super(from);
	}

	@Override
	protected String getXPathArticulo() {
		return XPATH_ARTICULO;
	}
}
