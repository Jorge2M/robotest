package com.mng.robotest.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.test.beans.Pais;

import static com.github.jorge2m.testmaker.conf.State.*;

public class Page1IdentCheckoutSteps extends StepBase {
	
	private final SecSoyNuevoSteps secSoyNuevoSteps = new SecSoyNuevoSteps();
	private final Page1IdentCheckout page1IdentCheckout = new Page1IdentCheckout();
	

	@Validation (
		description="Aparece el formulario correspondiente a la identificación (lo esperamos hasta #{seconds} segs)",
		level=Defect)
	public boolean validateIsPage(int seconds) {
		return (page1IdentCheckout.getSecSoyNuevo().isFormIdentUntil(seconds));
	}
	
	public void inputEmailAndContinue(String email, boolean emailExistsYet, boolean userRegistered, Pais pais) {
		secSoyNuevoSteps.inputEmailAndContinue(email, emailExistsYet, userRegistered, pais);
	}
	
	public void validaRGPDText() {
		secSoyNuevoSteps.validaRGPDText(); 
	}
}
