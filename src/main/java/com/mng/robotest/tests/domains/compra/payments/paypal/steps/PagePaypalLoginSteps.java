package com.mng.robotest.tests.domains.compra.payments.paypal.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.paypal.pageobjects.PagePaypalLogin;

public class PagePaypalLoginSteps extends StepBase {

	private final PagePaypalLogin pgPaypalLogin = new PagePaypalLogin();
	
	@Validation (
		description="Aparece la página de login " + SECONDS_WAIT)
	public boolean checkIsPage(int seconds) {
		return pgPaypalLogin.isPage(seconds);
	}
	
	@Step (
		description="Introducimos las credenciales (#{userMail} - #{password}) y pulsamos el botón \"Iniciar sesión\"", 
		expected="Aparece la página de inicio de sesión en Paypal")
	public void loginPaypal(String userMail, String password) {  
		String paginaPadre = driver.getWindowHandle();
		pgPaypalLogin.inputUserAndPassword(userMail, password);
		pgPaypalLogin.clickIniciarSesion();
		driver.switchTo().window(paginaPadre); 
		new PagePaypalSelectPagoSteps().checkIsPage(20);
	}
	
}
