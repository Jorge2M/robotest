package com.mng.robotest.test.steps.shop.checkout;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataTest;


public class Page1IdentCheckoutSteps extends StepBase {
	
	private final SecSoyNuevoSteps secSoyNuevoSteps = new SecSoyNuevoSteps();
	private final Page1IdentCheckout page1IdentCheckout = new Page1IdentCheckout();
	
	@SuppressWarnings("static-access")
	@Validation (
		description="Aparece el formulario correspondiente a la identificación (lo esperamos hasta #{maxSeconds} segs)",
		level=State.Defect)
	public boolean validateIsPage(int maxSeconds) {
		return (page1IdentCheckout.getSecSoyNuevo().isFormIdentUntil(maxSeconds));
	}
	
	public void inputEmailAndContinue(
			String email, boolean emailExistsYet, boolean userRegistered, Pais pais) throws Exception {
		secSoyNuevoSteps.inputEmailAndContinue(email, emailExistsYet, userRegistered, pais);
	}
	
	public void validaRGPDText() {
		secSoyNuevoSteps.validaRGPDText(); 
	}
}
