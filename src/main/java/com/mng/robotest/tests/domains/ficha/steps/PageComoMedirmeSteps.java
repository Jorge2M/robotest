package com.mng.robotest.tests.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.PageComoMedirme;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageComoMedirmeSteps extends StepBase {

	private final PageComoMedirme pageComoMedirme = new PageComoMedirme();
	
	@Validation (
		description="Aparece la página de <b>Cómo medirme</b> en una nueva pestaña",
		level=Warn)
	public boolean isPageInNewTab() {
		return (pageComoMedirme.goToPageInNewTabCheckAndClose());
	}
}
