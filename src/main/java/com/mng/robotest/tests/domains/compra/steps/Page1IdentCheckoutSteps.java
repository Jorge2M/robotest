package com.mng.robotest.tests.domains.compra.steps;

import static com.mng.robotest.tests.domains.compra.pageobjects.secsoynuevo.SecSoyNuevo.RadioState.ACTIVATE;
import static com.mng.robotest.tests.domains.compra.pageobjects.secsoynuevo.SecSoyNuevo.RadioState.DEACTIVATE;
import static com.mng.robotest.testslegacy.data.PaisShop.COREA_DEL_SUR;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;

public class Page1IdentCheckoutSteps extends StepBase {
	
	private final Page1IdentCheckout pg1IdentCheckout = new Page1IdentCheckout();

	@Validation (
		description="Aparece el formulario correspondiente a la continuación como invitado " + SECONDS_WAIT)
	public boolean isPage(int seconds) {
		return pg1IdentCheckout.isInputEmailNewUser(seconds);
	}
	
	public boolean isUrlDesktopPage() {
		return driver.getCurrentUrl().contains("login/pc/logincheckout.faces");
	}
	
	public void inputEmailNewUserAndContinue(String email) {
		inputEmailNewUserAndContinue(email, true);
	}
	
	@Step (
		description=
			"1. Desmarcamos el check  NewsLetter<br>" + 
			"2. Introducimos el email <b>#{email}</b> (existente: <b>#{emailExistsYet}</b>)<br>" + 
			"3. Seleccionamos \"Continuar\"", 
		expected="Aparece la página de introducción de datos del usuario")
	public void inputEmailNewUserAndContinue(String email, boolean emailExistsYet) {
		pg1IdentCheckout.setCheckPubliNewsletterNewUser(DEACTIVATE);
		if (COREA_DEL_SUR.isEquals(dataTest.getPais())) {
			pg1IdentCheckout.setCheckConsentimientoNewUser(ACTIVATE);
		}
		pg1IdentCheckout.inputEmailNewUser(email);
		pg1IdentCheckout.clickContinueNewUser();
		new Page2IdentCheckoutSteps().checkIsPage(emailExistsYet, 2);
		
		checksDefault();
	}	
	
	public void login() {
		login(dataTest.getUserConnected(), dataTest.getPasswordUser());
	}
	
	@Step (
		description="Nos identificamos con el usuario <b>#{user}</b> y password <b>#{password}</b>",
		expected="Aparece la página de checkout")
	public void login(String user, String password) {
		pg1IdentCheckout.login(user, password);
		new CheckoutSteps().checkIsFirstPage(true);
	}
	
}
