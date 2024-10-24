package com.mng.robotest.tests.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageInfoNewMisComprasMovil;

public class PageInfoNewMisComprasMovilSteps extends StepBase {

	private final PageInfoNewMisComprasMovil pgInfoNewMisComprasMovil = new PageInfoNewMisComprasMovil();
	
	@Validation(description="Aparece la página \"New!\" informativa a nivel de \"Mis Compras\"")
	public boolean validateIsPage() {
		return pgInfoNewMisComprasMovil.isPage();
	}

	@Step(
		description = "Seleccionar el botón \"Ver mis compras\"",
		expected = "Aparece la página de \"Mis Compras\"")
	public void clickButtonToMisComprasAndNoValidate() {
		pgInfoNewMisComprasMovil.clickButtonToMisCompras();
	}
	
}
