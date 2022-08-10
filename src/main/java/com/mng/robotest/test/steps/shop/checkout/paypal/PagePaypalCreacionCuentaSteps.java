package com.mng.robotest.test.steps.shop.checkout.paypal;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.paypal.PagePaypalCreacionCuenta;


public class PagePaypalCreacionCuentaSteps extends StepBase {
	
	@Step (
		description="Seleccionamos el botón <b>Iniciar Sesión</b>", 
		 expected="Aparece la página de login")
	public void clickButtonIniciarSesion() {
		new PagePaypalCreacionCuenta().clickButtonIniciarSesion();
		new PagePaypalLoginSteps().validateIsPageUntil(10);
	}
}
