package com.mng.robotest.domains.compra.payments.paypal.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.paypal.pageobjects.PagePaypalLogin;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PagePaypalLoginSteps extends StepBase {

	private final PagePaypalLogin pagePaypalLogin = new PagePaypalLogin();
	
	@Validation (
		description="Aparece la página de login (la esperamos hasta un máximo de #{seconds} segundos)",
		level=Defect)
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
