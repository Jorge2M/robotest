package com.mng.robotest.tests.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.registro.pageobjects.PagePoliticaPrivacidad;

public class PagePoliticaPrivacidadSteps extends StepBase {

	@Validation (
		description="Aparece la página con la política de privacidad " + SECONDS_WAIT)
	public boolean checkisPage(int seconds) {
		return new PagePoliticaPrivacidad().isPage(seconds);
	}
	
}
