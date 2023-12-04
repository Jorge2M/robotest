package com.mng.robotest.tests.domains.bolsa.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageIniciarSesionBolsaMobile extends PageBase {

	private static final String XP_USER_INPUT = "//*[@data-testid[contains(.,'login.emailInput')]]";
	private static final String XP_PASSWORD_INPUT = "//*[@data-testid[contains(.,'login.passInput')]]";
	private static final String XP_INICIAR_SESION_BUTTON = "//*[@data-testid[contains(.,'login.loginButton.login')]]";
	private static final String XP_HAS_OLVIDADO_TU_CONTRASENA = "//*[@data-testid[contains(.,'resetPassword')]]";
	
	public boolean isPage(int seconds) {
		return 
			state(PRESENT, XP_USER_INPUT).wait(seconds).check() &&
			state(PRESENT, XP_PASSWORD_INPUT).wait(seconds).check();
	}
	
	public void login(String user, String password) {
		inputCredentials(user, password);
		clickIniciarSesion();
	}
	
	public void clickHasOlvidadoContrasenya() {
		click(XP_HAS_OLVIDADO_TU_CONTRASENA).exec();
	}
	
	public void inputCredentials(String user, String password) {
		getElement(XP_USER_INPUT).sendKeys(user);
		getElement(XP_PASSWORD_INPUT).sendKeys(password);
	}

	public void clickIniciarSesion() {
		click(XP_INICIAR_SESION_BUTTON).exec();
	}

}
