package com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.desktop;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class SecSelectorPreciosDesktopNormal extends SecSelectorPreciosDesktop {

	private static final String XP_LEFT_CORNER = "//*[@data-testid='min-selector']";
	private static final String XP_RIGHT_CORNER = "//*[@data-testid='max-selector']";
	private static final String XP_LINEA_FILTRO = XP_LEFT_CORNER + "/..";
	private static final String XP_FILTRO_WRAPPER = XP_LINEA_FILTRO;	
	private static final String XP_IMPORTE_MINIMO = XP_LINEA_FILTRO + "/div[3]";
	private static final String XP_IMPORTE_MAXIMO = XP_LINEA_FILTRO + "/div[4]";
	
	@Override
	String getXPathLineaFiltro() {
		return XP_LINEA_FILTRO;
	}
	
	@Override
	String getXPathImporteMinimo() {
		return XP_IMPORTE_MINIMO;
	}
	
	@Override
	String getXPathImporteMaximo() {
		return XP_IMPORTE_MAXIMO;
	}	
	
	@Override
	String getXPathRightCorner() {
		return XP_RIGHT_CORNER;
	}
	
	@Override
	String getXPathLeftCorner() {
		return XP_LEFT_CORNER;
	}	
	
	@Override
	String getXPathFiltroWrapper() {
		return XP_FILTRO_WRAPPER;
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
