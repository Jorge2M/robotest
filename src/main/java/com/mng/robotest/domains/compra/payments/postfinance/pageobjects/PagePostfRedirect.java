package com.mng.robotest.domains.compra.payments.postfinance.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PagePostfRedirect extends PageBase {

	private static final String XPATH_BUTTON_OK = "//form/input[@type='button' and @value[contains(.,'OK')]]";
	
	/**
	 * @return si estamos en la página de redirección con el botón OK que aparece después de introducir el código de seguridad y pulsar "Continuar"
	 */
	public boolean isPresentButtonOk() {
		return state(Present, XPATH_BUTTON_OK).check();
	}
	
	/**
	 * @return si es invisible (ha desaparecido) la capa de redirección con el botón OK que aparece después de introducir el código de seguridad y pulsar "Continuar"
	 */
	public boolean isInvisibleButtonOkUntil(int seconds) {
		boolean invisibility = false;
		try { 
			new WebDriverWait(driver, seconds).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(XPATH_BUTTON_OK)));
			invisibility = true;
		}
		catch (Exception e) {
			/*
			 * Continuamos pues existe la posibilidad de que el botón esté en el estado deseado
			 */
		}
		
		return invisibility;
	}
}
