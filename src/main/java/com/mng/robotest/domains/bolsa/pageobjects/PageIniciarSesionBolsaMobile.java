package com.mng.robotest.domains.bolsa.pageobjects;

import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageIniciarSesionBolsaMobile extends PageBase {

	private static final String XPATH_USER_INPUT = "//*[@data-testid='checkout.login.emailInput']";
	private static final String XPATH_PASSWORD_INPUT = "//*[@data-testid='checkout.login.passInput']";
	private static final String XPATH_MANTENER_SESION_INICIADA_CHECK = "//*[@data-testid='checkout.login.remindmeCheckbox']";
	private static final String XPATH_INICIAR_SESION_BUTTON = "//*[@data-testid='checkout.login.loginButton.login']";
	
	public boolean isPage(int seconds) {
		return 
			state(Present, XPATH_USER_INPUT).wait(seconds).check() &&
			state(Present, XPATH_PASSWORD_INPUT).wait(seconds).check();
	}
	
	public void login(String user, String password) {
		inputCredentials(user, password);
		clickIniciarSesion();
	}
	
	public boolean isMantenerSesionIniciadaChecked() {
		return getElement(XPATH_MANTENER_SESION_INICIADA_CHECK).isSelected();
	}
	
	private void inputCredentials(String user, String password) {
		getElement(XPATH_USER_INPUT).sendKeys(user);
		getElement(XPATH_PASSWORD_INPUT).sendKeys(password);
	}

	private void clickIniciarSesion() {
		click(XPATH_INICIAR_SESION_BUTTON).exec();
	}

}
