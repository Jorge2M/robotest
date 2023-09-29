package com.mng.robotest.tests.domains.compra.payments.paypal.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.paypal.pageobjects.PagePaypalCreacionCuenta;


public class PagePaypalCreacionCuentaSteps extends StepBase {
	
	@Step (
		description="Seleccionamos el botón <b>Iniciar Sesión</b>", 
		 expected="Aparece la página de login")
	public void clickButtonIniciarSesion() {
		new PagePaypalCreacionCuenta().clickButtonIniciarSesion();
		new PagePaypalLoginSteps().validateIsPageUntil(10);
	}
}
