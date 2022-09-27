package com.mng.robotest.domains.compra.payments.postfinance.pageobjects;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
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
		return state(State.Invisible, XPATH_BUTTON_OK).wait(seconds).check();
	}
}
