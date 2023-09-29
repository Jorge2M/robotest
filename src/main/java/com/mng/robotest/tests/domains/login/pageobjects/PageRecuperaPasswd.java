package com.mng.robotest.tests.domains.login.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageRecuperaPasswd extends PageBase {
	
	private static final String XPATH_INPUT_CORREO = "//input[@type='text' and @id[contains(.,'RPemail')]]";
	private static final String XPATH_BUTTON_ENVIAR = "//input[@type='submit' and @id[contains(.,'ResetPassword')]]";
	private static final String XPATH_MSG_REVISA_TU_EMAIL = "//div[text()[contains(.,'REVISA TU EMAIL')]]";
	private static final String XPATH_BUTTON_IR_DE_SHOPPING = "//div[@id[contains(.,'IrShopping')]]/a";
	
	public boolean isPageUntil(int seconds) {
		return isPresentElementWithText("RECUPERA TU CONTRASEÑA", seconds);
	}
	
	public boolean isPresentInputCorreo() {
		return state(Present, XPATH_INPUT_CORREO).check();
	}
	
	public void inputEmail(String email) {
		getElement(XPATH_INPUT_CORREO).clear();
		getElement(XPATH_INPUT_CORREO).sendKeys(email);
	}

	public void clickEnviar() {
		click(XPATH_BUTTON_ENVIAR).exec();
	}

	public boolean isVisibleRevisaTuEmailUntil(int seconds) {
		return state(Visible, XPATH_MSG_REVISA_TU_EMAIL).wait(seconds).check();
	}

	public boolean isVisibleButtonIrDeShopping() {
		return state(Visible, XPATH_BUTTON_IR_DE_SHOPPING).check();
	}
		
}
