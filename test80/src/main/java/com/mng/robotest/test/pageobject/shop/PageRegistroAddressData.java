package com.mng.robotest.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


/**
 * Clase para operar con la página de registro en la que se introducen los datos de la dirección del usuario
 * @author jorge.munoz
 *
 */
public class PageRegistroAddressData {

	static String XPathTitleAddressSteps = "//div[@class[contains(.,'addressData')]]";
	static String XPathDesplegablePaises = "//select[@id[contains(.,'pais')]]";
	static String XPathButtonFinalizar = "//div[@class[contains(.,'registerStepsModal')]]//div[@class='submitContent']//input[@type='submit']";
	static String XPathErrorInputDireccion = "//div[@id[contains(.,'cfDir1ErrorLabel')]]";	

	public static String getXPath_optionPaisSelected(String codigoPais) {
		return ("//select[@id[contains(.,'pais')]]/option[@selected='selected' and @value='" + codigoPais + "']");
	}

	public static boolean isPageUntil(WebDriver driver, int secondsWait) {
		return (state(Present, By.xpath(XPathTitleAddressSteps), driver)
				.wait(secondsWait).check());
	}

	public static boolean existsDesplegablePaises(WebDriver driver) {
		return (state(Present, By.xpath(XPathDesplegablePaises), driver).check());
	}

	public static boolean isOptionPaisSelected(WebDriver driver, String codigoPais) {
		String xpathOptionPaisSelected = getXPath_optionPaisSelected(codigoPais);
		return (state(Present, By.xpath(xpathOptionPaisSelected), driver).check());
	}

	public static void clickButtonFinalizar(WebDriver driver) {
		click(By.xpath(XPathButtonFinalizar), driver).exec();
	}

	public static boolean isVisibleErrorInputDireccion(WebDriver driver) {
		return (state(Visible, By.xpath(XPathErrorInputDireccion), driver).check());
	}
}
