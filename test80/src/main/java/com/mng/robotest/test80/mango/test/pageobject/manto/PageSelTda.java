package com.mng.robotest.test80.mango.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.TiendaMantoEnum.TiendaManto;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


/**
 * Implementa la "API" de interacción con la página de "Selección de la tienda en Manto" (la posterior al login) 
 * @author jorge.munoz
 *
 */
public class PageSelTda {

	static String XPathCeldaTextSelectEntorno = "//td[text()[contains(.,'Seleccion de Entorno')]]";

	public static String getXpath_linkTienda(TiendaManto tienda) {
		return ("//a[text()[contains(.,'" + tienda.litPantManto + "')]]");
	}

	public static boolean isPage(WebDriver driver) {
		return isPage(0, driver);
	}
	public static boolean isPage(int maxSeconds, WebDriver driver) {
		return (state(Present, By.xpath(XPathCeldaTextSelectEntorno), driver).wait(maxSeconds).check());
	}

	public static void selectTienda(TiendaManto tienda, WebDriver driver) {
		String xpath = getXpath_linkTienda(tienda);
		click(By.xpath(xpath), driver).exec();
	}
}
