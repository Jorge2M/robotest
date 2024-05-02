package com.mng.robotest.tests.domains.compra.steps;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.compra.pageobjects.secsoynuevo.SecSoyNuevo;
import com.mng.robotest.tests.domains.compra.pageobjects.secsoynuevo.SecSoyNuevo.RadioState;

public class Page1IdentCheckout extends PageBase {
	
	private final SecSoyNuevo secSoyNuevo = SecSoyNuevo.make(channel);

	public void setCheckPubliNewsletterNewUser(RadioState action) {
		secSoyNuevo.setCheckPubliNewsletter(action);
	}
	
	public void setCheckConsentimientoNewUser(RadioState action) {
		secSoyNuevo.setCheckConsentimiento(action);
	}
	
	public boolean isInputEmailNewUser(int seconds) {
		return secSoyNuevo.isInputEmailUntil(seconds);
	}
	
	public void inputEmailNewUser(String email) {
		secSoyNuevo.inputEmail(email);
	}
	
	public void clickContinueNewUser() {
		secSoyNuevo.clickContinue();
	}
	
	public void login(String user, String password) {
		inputCredentials(user, password);
		clickIniciarSesion();
	}
	
	private static final String XP_LOGIN_CAPA = "//div[@id='loginCheckout']";
	private static final String XP_USER_INPUT = XP_LOGIN_CAPA + "//input[@id[contains(.,'userMail')]]";
	private static final String XP_PASSWORD_INPUT = XP_LOGIN_CAPA + "//input[@id[contains(.,'chkPwd')]]";
	private static final String XP_INICIAR_SESION_BUTTON = XP_LOGIN_CAPA + "//a[@onclick]";
	
	private void inputCredentials(String user, String password) {
		inputClearAndSendKeys(XP_USER_INPUT, user);
		inputClearAndSendKeys(XP_PASSWORD_INPUT, password);
	}

	private void clickIniciarSesion() {
		click(XP_INICIAR_SESION_BUTTON).exec();
	}
	
}
