package com.mng.robotest.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.registro.pageobjects.PagePoliticaPrivacidad;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PagePoliticaPrivacidadSteps extends StepBase {

	private final PagePoliticaPrivacidad pagePoliticaPrivacidad = new PagePoliticaPrivacidad();
	
	@Validation (
		description="Aparece la página con la política de privacidad (la esperamos hasta #{seconds} segundos)",
		level=Defect)
	public boolean checkIsPageUntil(int seconds) {
		return pagePoliticaPrivacidad.isPageUntil(seconds);
	}
	
}
