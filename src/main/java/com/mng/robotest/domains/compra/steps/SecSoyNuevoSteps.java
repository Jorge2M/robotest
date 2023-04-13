package com.mng.robotest.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.pageobjects.secsoynuevo.SecSoyNuevo;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;

import static com.mng.robotest.test.data.PaisShop.*;
import static com.mng.robotest.domains.compra.pageobjects.secsoynuevo.SecSoyNuevo.RadioState.*;

public class SecSoyNuevoSteps extends StepBase {

	private final SecSoyNuevo secSoyNuevo = SecSoyNuevo.make(channel);
	
	@Step (
		description=
			"1. Desmarcamos el check  NewsLetter<br>" + 
			"2. Introducimos el email <b>#{email}</b> (existente: <b>#{emailExistsYet}</b>)<br>" + 
			"3. Seleccionamos \"Continuar\"", 
		expected="Aparece la página de introducción de datos del usuario")
	public void inputEmailAndContinue(String email, boolean emailExistsYet, boolean userRegistered, Pais pais) {
		secSoyNuevo.setCheckPubliNewsletter(DEACTIVATE);
		if (COREA_DEL_SUR.isEquals(pais)) {
			secSoyNuevo.setCheckConsentimiento(ACTIVATE);
		}
		secSoyNuevo.inputEmail(email);
		secSoyNuevo.clickContinue();
		new Page2IdentCheckoutSteps().validateIsPage(emailExistsYet, 2);
		
		GenericChecks.checkDefault();
	}

}
