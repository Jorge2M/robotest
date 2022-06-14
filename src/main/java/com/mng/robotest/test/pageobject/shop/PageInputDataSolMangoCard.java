package com.mng.robotest.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


/**
 * Page1: página inicial de solicitud de la tarjeta (la que contiene el botón de "Solicitar Tarjeta Mango"
 * Modal: de aviso de trámite de la solicitud
 * Page2: página con los campos de introducción de datos
 * @author jorge.munoz
 *
 */
public class PageInputDataSolMangoCard {
	
	private static final String XPathbotonContinuarModal = "//div//button[text()[contains(.,'Continuar')]]";
	private static final String XPathIsPage2 = "//div/span[text()[contains(.,'Solicitud de tu MANGO Card')]]";
	private static final String XPathIframePage2 = "//div[@id='ifcentral']/iframe";
	private static final String XPathTextDatosPersonalesPage2 = "//a[@data-i18n[contains(.,'datospersonales')]]";
	private static final String XPathTextDatosBancariosPage2 = "//a[@data-i18n[contains(.,'datosbancarios')]]";
	private static final String XPathDatosContactoPage2 = "//a[@data-i18n[contains(.,'datoscontacto')]]";
	private static final String XPathDatosSocioeconomicosPage2 = "//a[@data-i18n[contains(.,'datossocioeconomicos')]]";
	private static final String XPathModalidadPagoPage2 = "//a[@data-i18n[contains(.,'modalidadpago')]]";
	private static final String XPathButtonContinuarPage2 = "//button[@id='in-continuar']";


	/*
	 * Funciones para la validación y interactuación con la segunda página de solicitud de tarjeta mango
	 */
	
	public static boolean isPresentBotonContinuarModalUntil(int maxSecondsToWait, WebDriver driver) {
		return (state(Present, By.xpath(XPathbotonContinuarModal), driver)
				.wait(maxSecondsToWait).check());
	}

	public static void clickBotonCerrarModal(WebDriver driver) {
		click(By.xpath(XPathbotonContinuarModal), driver).exec();
	}

	public static boolean isPage2(WebDriver driver) {
		return (state(Present, By.xpath(XPathIsPage2), driver).check());
	}

	/**
	 * Posicionarse en el iframe central de la página 2 con los datos para la solicitud de la tarjeta mango
	 * @param driver
	 */
	
	public static void gotoiFramePage2(WebDriver driver) {
		WebElement frameElement = driver.findElement(By.xpath(XPathIframePage2));
		driver.switchTo().frame(frameElement);
	}

	public static boolean isPresentDatosPersonalesPage2(WebDriver driver) {
		return (state(Present, By.xpath(XPathTextDatosPersonalesPage2), driver).check());
	}

	public static boolean isPresentDatosBancariosPage2(WebDriver driver) {
		return (state(Present, By.xpath(XPathTextDatosBancariosPage2), driver).check());
	}

	public static boolean isPresentDatosContactoPage2(WebDriver driver) {
		return (state(Present, By.xpath(XPathDatosContactoPage2), driver).check());
	}

	public static boolean isPresentDatosSocioeconomicosPage2(WebDriver driver) {
		return (state(Present, By.xpath(XPathDatosSocioeconomicosPage2), driver).check());
	}

	public static boolean isPresentModalidadpagoPage2(WebDriver driver) {
		return (state(Present, By.xpath(XPathModalidadPagoPage2), driver).check());
	}

	public static boolean isPresentButtonContinuarPage2(WebDriver driver) {
		return (state(Present, By.xpath(XPathButtonContinuarPage2), driver).check());
	}
	
}
