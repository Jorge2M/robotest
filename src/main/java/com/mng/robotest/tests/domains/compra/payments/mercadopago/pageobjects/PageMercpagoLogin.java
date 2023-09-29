package com.mng.robotest.tests.domains.compra.payments.mercadopago.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageMercpagoLogin extends PageBase {
	
	private static final String XPATH_INPUT_USER = "//input[@id='user_id']";
	private static final String XPATH_INPUT_PASSWORD = "//input[@id='password']";
	private static final String XPATH_BOTON_CONTINUAR = "//input[@id='init' and @type='submit']";
	
	public boolean isPage() {
		return driver.getTitle().contains("Mercado Pago");
	}
	
	public void sendInputUser(String input) {
		getElement(XPATH_INPUT_USER).clear();
		getElement(XPATH_INPUT_USER).sendKeys(input);
	}
	
	public void sendInputPassword(String input) {
		getElement(XPATH_INPUT_PASSWORD).clear();
		getElement(XPATH_INPUT_PASSWORD).sendKeys(input);
	}
	
	public boolean isInputUserVisible() {
		return state(Visible, XPATH_INPUT_USER).check();
	}
	
	public boolean isInputPasswordVisible() {
		return state(Visible, XPATH_INPUT_PASSWORD).check();
	}	
	
	public boolean isVisibleBotonContinuarPageId() {
		return state(Visible, XPATH_BOTON_CONTINUAR).check();
	}

	public void clickBotonContinuar() {
		click(XPATH_BOTON_CONTINUAR).exec();
	}
}