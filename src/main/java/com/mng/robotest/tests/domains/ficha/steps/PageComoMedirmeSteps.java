package com.mng.robotest.tests.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.PageComoMedirme;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageComoMedirmeSteps extends StepBase {

	private final PageComoMedirme pgComoMedirme = new PageComoMedirme();
	
	@Validation (
		description="Aparece la página de <b>Cómo medirme</b> en una nueva pestaña",
		level=WARN)
	public boolean isPageInNewTab() {
		return (pgComoMedirme.goToPageInNewTabCheckAndClose());
	}
}
