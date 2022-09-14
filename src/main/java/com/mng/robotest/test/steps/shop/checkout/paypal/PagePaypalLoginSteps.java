package com.mng.robotest.test.steps.shop.checkout.paypal;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.paypal.PagePaypalLogin;


public class PagePaypalLoginSteps extends StepBase {

	PagePaypalLogin pagePaypalLogin = new PagePaypalLogin();
	
	@Validation (
		description="Aparece la página de login (la esperamos hasta un máximo de #{seconds} segundos)",
		level=State.Defect)
	public boolean validateIsPageUntil(int seconds) {
		return pagePaypalLogin.isPageUntil(seconds);
	}
	
	@Step (
		description="Introducimos las credenciales (#{userMail} - #{password}) y pulsamos el botón \"Iniciar sesión\"", 
		expected="Aparece la página de inicio de sesión en Paypal")
	public void loginPaypal(String userMail, String password) {  
		String paginaPadre = driver.getWindowHandle();
		pagePaypalLogin.inputUserAndPassword(userMail, password);
		pagePaypalLogin.clickIniciarSesion();
		driver.switchTo().window(paginaPadre); //Salimos del iframe
		new PagePaypalSelectPagoSteps().validateIsPageUntil(20);
	}
}
