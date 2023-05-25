package com.mng.robotest.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.registro.pageobjects.PagePoliticaPrivacidad;

public class PagePoliticaPrivacidadSteps extends StepBase {

	@Validation (
		description="Aparece la página con la política de privacidad (la esperamos hasta #{seconds} segundos)")
	public boolean checkIsPageUntil(int seconds) {
		return new PagePoliticaPrivacidad().isPageUntil(seconds);
	}
	
}
