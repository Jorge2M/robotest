package com.mng.robotest.test.pageobject.shop;

import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

/**
 * Page1: página inicial de solicitud de la tarjeta (la que contiene el botón de "Solicitar Tarjeta Mango"
 * Modal: de aviso de trámite de la solicitud
 * Page2: página con los campos de introducción de datos
 * @author jorge.munoz
 *
 */
public class PageInputDataSolMangoCard extends PageBase {
	
	private static final String XPATH_BOTON_CONTINUAR_MODAL = "//div//button[text()[contains(.,'Continuar')]]";
	private static final String XPATH_IS_PAGE2 = "//div/span[text()[contains(.,'Solicitud de tu MANGO Card')]]";
	private static final String XPATH_IFRAME_PAGE2 = "//div[@id='ifcentral']/iframe";
	private static final String XPATH_TEXT_DATOS_PERSONALES_PAGE2 = "//a[@data-i18n[contains(.,'datospersonales')]]";
	private static final String XPATH_TEXT_DATOS_BANCARIOS_PAGE2 = "//a[@data-i18n[contains(.,'datosbancarios')]]";
	private static final String XPATH_DATOS_CONTACTO_PAGE2 = "//a[@data-i18n[contains(.,'datoscontacto')]]";
	private static final String XPATH_DATOS_SOCIOECONOMICOS_PAGE2 = "//a[@data-i18n[contains(.,'datossocioeconomicos')]]";
	private static final String XPATH_MODALIDAD_PAGO_PAGE2 = "//a[@data-i18n[contains(.,'modalidadpago')]]";
	private static final String XPATH_BUTTON_CONTINUAR_PAGE2 = "//button[@id='in-continuar']";

	public boolean isPresentBotonContinuarModalUntil(int secondsToWait) {
		return (state(Present, XPATH_BOTON_CONTINUAR_MODAL).wait(secondsToWait).check());
	}

	public void clickBotonCerrarModal() {
		click(XPATH_BOTON_CONTINUAR_MODAL).exec();
	}

	public boolean isPage2() {
		return state(Present, XPATH_IS_PAGE2).check();
	}

	/**
	 * Posicionarse en el iframe central de la página 2 con los datos para la solicitud de la tarjeta mango
	 * @param driver
	 */
	public void gotoiFramePage2() {
		WebElement frameElement = getElement(XPATH_IFRAME_PAGE2);
		driver.switchTo().frame(frameElement);
	}

	public boolean isPresentDatosPersonalesPage2() {
		return state(Present, XPATH_TEXT_DATOS_PERSONALES_PAGE2).check();
	}

	public boolean isPresentDatosBancariosPage2() {
		return state(Present, XPATH_TEXT_DATOS_BANCARIOS_PAGE2).check();
	}

	public boolean isPresentDatosContactoPage2() {
		return state(Present, XPATH_DATOS_CONTACTO_PAGE2).check();
	}

	public boolean isPresentDatosSocioeconomicosPage2() {
		return state(Present, XPATH_DATOS_SOCIOECONOMICOS_PAGE2).check();
	}

	public boolean isPresentModalidadpagoPage2() {
		return state(Present, XPATH_MODALIDAD_PAGO_PAGE2).check();
	}

	public boolean isPresentButtonContinuarPage2() {
		return state(Present, XPATH_BUTTON_CONTINUAR_PAGE2).check();
	}
	
}
