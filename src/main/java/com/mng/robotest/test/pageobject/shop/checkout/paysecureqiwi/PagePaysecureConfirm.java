package com.mng.robotest.test.pageobject.shop.checkout.paysecureqiwi;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class PagePaysecureConfirm extends PageBase {

	private static final String XPATH_BUTTON_CONFIRMAR = "//input[@name[contains(.,'Submit_Success')]]"; 
	
	/**
	 * @return si estamos en la página de confirmación de Qiwi (aparece a veces después de la introducción del teléfono + botón continuar)
	 */
	public boolean isPage() {
		return state(Present, XPATH_BUTTON_CONFIRMAR).check();
	}

	public void clickConfirmar() {
		click(XPATH_BUTTON_CONFIRMAR).exec();
	}
}
