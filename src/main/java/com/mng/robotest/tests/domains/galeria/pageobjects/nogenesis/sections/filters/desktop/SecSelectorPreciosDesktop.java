package com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.desktop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.VISIBLE;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

public class SecSelectorPreciosDesktop extends PageBase {

	private static final String XP_LEFT_CORNER = "//*[@data-testid='min-selector']";
	private static final String XP_RIGHT_CORNER = "//*[@data-testid='max-selector']";
	private static final String XP_LINEA_FILTRO = XP_LEFT_CORNER + "/..";
	private static final String XP_IMPORTE_MINIMO = XP_LINEA_FILTRO + "/div[3]";
	private static final String XP_IMPORTE_MAXIMO = XP_LINEA_FILTRO + "/div[4]";
	
	public boolean isVisible() {
		return state(VISIBLE, XP_LINEA_FILTRO).check();
	}

	public int getMinImport() {
		state(VISIBLE, XP_IMPORTE_MINIMO).wait(2).check();
		var impMinElement = getElement(XP_IMPORTE_MINIMO);
		return getImportFilter(impMinElement.getText());
	}
	
	public int getMaxImport() {
		var impMaxElement = getElement(XP_IMPORTE_MAXIMO);
		return getImportFilter(impMaxElement.getText());
	}
	private int getImportFilter(String importScreen) {
		return (int)(ImporteScreen.getFloatFromImporteMangoScreen(importScreen));		
	}	
	
	public void clickMinAndMax(int pixelsLeft, int pixelsRight) {
		var minSelector = getElement(XP_LEFT_CORNER);
		var maxSelector = getElement(XP_RIGHT_CORNER);
		drag(minSelector, pixelsLeft);
		drag(maxSelector, -pixelsRight);
	}
	
	private void drag(WebElement element, int pixels) {
        new Actions(driver).dragAndDropBy(element, pixels, 0).build().perform();		
	}

}
