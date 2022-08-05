package com.mng.robotest.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.ficha.pageobjects.PageComoMedirme;


public class PageComoMedirmeSteps {

	private final PageComoMedirme pageComoMedirme = new PageComoMedirme();
	
	@Validation (
		description="Aparece la página de <b>Cómo medirme</b> en una nueva pestaña",
		level=State.Warn)
	public boolean isPageInNewTab() throws Exception {
		return (pageComoMedirme.goToPageInNewTabCheckAndClose());
	}
}
