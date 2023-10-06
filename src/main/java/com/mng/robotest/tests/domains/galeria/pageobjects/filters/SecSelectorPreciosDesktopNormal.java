package com.mng.robotest.tests.domains.galeria.pageobjects.filters;

import static com.mng.robotest.tests.domains.galeria.pageobjects.filters.SecSelectorPreciosDesktop.TypeClick.LEFT;
import static com.mng.robotest.tests.domains.galeria.pageobjects.filters.SecSelectorPreciosDesktop.TypeClick.RIGHT;

import org.openqa.selenium.interactions.Actions;

public class SecSelectorPreciosDesktopNormal extends SecSelectorPreciosDesktop {

	private static final String XPATH_LINEA_FILTRO = "//div[@class[contains(.,'input-range__track--background')]]"; //
	private static final String XPATH_IMPORTE_MINIMO = "(" + XPATH_LINEA_FILTRO + "//span[@class[contains(.,'label-container')]])[1]"; //
	private static final String XPATH_IMPORTE_MAXIMO = "(" + XPATH_LINEA_FILTRO + "//span[@class[contains(.,'label-container')]])[2]"; //
	private static final String XPATH_FILTRO_WRAPPER = "//div[@class='input-range']"; //
	private static final String XPATH_LEFT_CORNER = XPATH_IMPORTE_MINIMO + "/../..";
	private static final String XPATH_RIGHT_CORNER = XPATH_IMPORTE_MAXIMO + "/../..";

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
	public void clickMinAndMax(int margenPixelsIzquierda, int margenPixelsDerecha) {
		click(TypeClick.RIGHT, -margenPixelsDerecha);
		click(TypeClick.LEFT, margenPixelsIzquierda);
	}

	private void click(TypeClick typeClick, int pixelsFromCorner) {
		var builder = new Actions(driver);
		moveToCornerSelector(RIGHT);
		waitMillis(2000);
		moveToCornerSelector(typeClick);
		waitLoadPage();
		builder.moveByOffset(pixelsFromCorner, 0).click().build().perform();
		waitLoadPage();
	}

	private void moveToCornerSelector(TypeClick typeCorner) {
		waitLoadPage();
		moveToElement(getXPathFiltroWrapper());
		if (typeCorner==LEFT) { 
			moveToElement(getXPathLeftCorner());
		}
		else {
			moveToElement(getXPathRightCorner());
		}
	}	
	
}
