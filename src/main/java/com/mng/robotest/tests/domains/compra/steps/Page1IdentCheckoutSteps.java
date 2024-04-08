package com.mng.robotest.tests.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;

public class Page1IdentCheckoutSteps extends StepBase {
	
	private final SecSoyNuevoSteps secSoyNuevoSteps = new SecSoyNuevoSteps();
	private final Page1IdentCheckout pg1IdentCheckout = new Page1IdentCheckout();
	

	@Validation (
		description="Aparece el formulario correspondiente a la continuación como invitado " + SECONDS_WAIT)
	public boolean checkIsPage(int seconds) {
		return pg1IdentCheckout.getSecSoyNuevo().isInputEmailUntil(seconds);
	}
	
	public boolean isUrlDesktopPage() {
		return driver.getCurrentUrl().contains("login/pc/logincheckout.faces");
	}
	
	public void inputEmailAndContinue(String email, boolean emailExistsYet) {
		secSoyNuevoSteps.inputEmailAndContinue(email, emailExistsYet);
	}
	
}
