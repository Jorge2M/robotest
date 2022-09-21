package com.mng.robotest.domains.compra.payments.paypal.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.domains.compra.payments.paypal.pageobjects.PagePaypalCreacionCuenta;
import com.mng.robotest.domains.transversal.StepBase;


public class PagePaypalCreacionCuentaSteps extends StepBase {
	
	@Step (
		description="Seleccionamos el botón <b>Iniciar Sesión</b>", 
		 expected="Aparece la página de login")
	public void clickButtonIniciarSesion() {
		new PagePaypalCreacionCuenta().clickButtonIniciarSesion();
		new PagePaypalLoginSteps().validateIsPageUntil(10);
	}
}
