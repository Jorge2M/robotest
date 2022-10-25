package com.mng.robotest.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.ficha.pageobjects.PageComoMedirme;
import com.mng.robotest.domains.transversal.StepBase;


public class PageComoMedirmeSteps extends StepBase {

	private final PageComoMedirme pageComoMedirme = new PageComoMedirme();
	
	@Validation (
		description="Aparece la página de <b>Cómo medirme</b> en una nueva pestaña",
		level=State.Warn)
	public boolean isPageInNewTab() {
		return (pageComoMedirme.goToPageInNewTabCheckAndClose());
	}
}
