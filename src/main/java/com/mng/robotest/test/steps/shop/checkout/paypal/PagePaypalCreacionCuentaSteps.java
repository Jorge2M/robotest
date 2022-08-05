package com.mng.robotest.test.steps.shop.checkout.paypal;

import org.openqa.selenium.WebDriver;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.test.pageobject.shop.checkout.paypal.PagePaypalCreacionCuenta;

public class PagePaypalCreacionCuentaSteps {
	
	@Step (
		description="Seleccionamos el botón <b>Iniciar Sesión</b>", 
		 expected="Aparece la página de login")
	public static void clickButtonIniciarSesion(WebDriver driver) {
		PagePaypalCreacionCuenta.clickButtonIniciarSesion(driver);
		
		//Validaciones
		int maxSeconds = 10;
		PagePaypalLoginSteps.validateIsPageUntil(maxSeconds, driver);
	}
}
