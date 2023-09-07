package com.mng.robotest.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;

public class Page1IdentCheckoutSteps extends StepBase {
	
	private final SecSoyNuevoSteps secSoyNuevoSteps = new SecSoyNuevoSteps();
	private final Page1IdentCheckout page1IdentCheckout = new Page1IdentCheckout();
	

	@Validation (
		description="Aparece el formulario correspondiente a la identificación (lo esperamos hasta #{seconds} segs)")
	public boolean checkIsPage(int seconds) {
		return page1IdentCheckout.getSecSoyNuevo().isInputEmailUntil(seconds);
	}
	
	public void inputEmailAndContinue(String email, boolean emailExistsYet) {
		secSoyNuevoSteps.inputEmailAndContinue(email, emailExistsYet);
	}
	
}
