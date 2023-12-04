package com.mng.robotest.tests.domains.compra.pageobjects;

import java.util.ListIterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.testslegacy.utils.ImporteScreen;

public class UtilsCheckout {

	public static float getImporteScreenFromIntegerAndDecimal(WebElement importeWeb) {
		String precioArticulo = "";
		
		ListIterator<WebElement> itArticuloEntero = null;
		ListIterator<WebElement> itArticuloDecimal = null;
		itArticuloEntero = importeWeb.findElements(By.className("entero")).listIterator();
		while (itArticuloEntero != null && itArticuloEntero.hasNext()) {
			var articuloEntero = itArticuloEntero.next();
			precioArticulo += articuloEntero.getText();
		}

		itArticuloDecimal = importeWeb.findElements(By.className("decimal")).listIterator();
		while (itArticuloDecimal != null && itArticuloDecimal.hasNext()) {
			var articuloDecimal = itArticuloDecimal.next();
			precioArticulo += articuloDecimal.getText();
		}

		return ImporteScreen.getFloatFromImporteMangoScreen(precioArticulo);
	}

}
