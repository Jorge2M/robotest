package com.mng.robotest.test.pageobject.shop.checkout.paysecureqiwi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PagePaysecureConfirm {

	static String XPathButtonConfirmar = "//input[@name[contains(.,'Submit_Success')]]"; 
	
	/**
	 * @return si estamos en la página de confirmación de Qiwi (aparece a veces después de la introducción del teléfono + botón continuar)
	 */
	public static boolean isPage(WebDriver driver) {
		return (state(Present, By.xpath(XPathButtonConfirmar), driver).check());
	}

	public static void clickConfirmar(WebDriver driver) {
		click(By.xpath(XPathButtonConfirmar), driver).exec();
	}
}
