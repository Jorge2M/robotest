package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


/**
 * Clase para operar con la página "Resultado de una búsqueda KO" a través de la API de driver
 * @author jorge.munoz
 *
 */
public class PageErrorBusqueda {

	/**
	 * @param textoBuscado
	 * @return el xpath correspondiente a la cabecera de resultado de una búsqueda concreta
	 */
	public static String getXPath_cabeceraBusquedaProd(String textoBuscado) {
		return ("//td/i/span[text()[contains(.,'" + textoBuscado + "')] or text()[contains(.,'" + textoBuscado.toLowerCase() + "')]]");
	}

	public static boolean isPage(WebDriver driver) {
		String xpath = "//*[text()[contains(.,'Tu búsqueda...')]]";
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static boolean isCabeceraResBusqueda(WebDriver driver, String textoBuscado) {
		String xpathCabe = getXPath_cabeceraBusquedaProd(textoBuscado);
		return (state(Present, By.xpath(xpathCabe), driver).wait(1).check());
	}
}
