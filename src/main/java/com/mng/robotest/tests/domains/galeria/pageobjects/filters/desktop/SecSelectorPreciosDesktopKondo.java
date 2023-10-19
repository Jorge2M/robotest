package com.mng.robotest.tests.domains.galeria.pageobjects.filters.desktop;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class SecSelectorPreciosDesktopKondo extends SecSelectorPreciosDesktop {

	private static final String XPATH_LEFT_CORNER = "//*[@data-testid='min-selector']";
	private static final String XPATH_RIGHT_CORNER = "//*[@data-testid='max-selector']";
	private static final String XPATH_LINEA_FILTRO = XPATH_LEFT_CORNER + "/..";
	private static final String XPATH_FILTRO_WRAPPER = XPATH_LINEA_FILTRO;	
	private static final String XPATH_IMPORTE_MINIMO = XPATH_LINEA_FILTRO + "/div[3]";
	private static final String XPATH_IMPORTE_MAXIMO = XPATH_LINEA_FILTRO + "/div[4]";
	
	@Override
	String getXPathLineaFiltro() {
		return XPATH_LINEA_FILTRO;
	}
	
	@Override
	String getXPathImporteMinimo() {
		return XPATH_IMPORTE_MINIMO;
	}
	
	@Override
	String getXPathImporteMaximo() {
		return XPATH_IMPORTE_MAXIMO;
	}	
	
	@Override
	String getXPathRightCorner() {
		return XPATH_RIGHT_CORNER;
	}
	
	@Override
	String getXPathLeftCorner() {
		return XPATH_LEFT_CORNER;
	}	
	
	@Override
	String getXPathFiltroWrapper() {
		return XPATH_FILTRO_WRAPPER;
	}		
	
	@Override
	public void clickMinAndMax(int pixelsLeft, int pixelsRight) {
		var minSelector = getElement(getXPathLeftCorner());
		var maxSelector = getElement(getXPathRightCorner());
		drag(minSelector, pixelsLeft);
		drag(maxSelector, -pixelsRight);
	}
	
	private void drag(WebElement element, int pixels) {
        new Actions(driver).dragAndDropBy(element, pixels, 0).build().perform();		
	}

}
