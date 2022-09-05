package com.mng.robotest.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.micuenta.pageobjects.PageInfoNewMisComprasMovil;
import com.mng.robotest.domains.transversal.StepBase;

public class PageInfoNewMisComprasMovilSteps extends StepBase {

	private final PageInfoNewMisComprasMovil pageInfoNewMisComprasMovil = new PageInfoNewMisComprasMovil();
	
	@Validation(
		description="1) Aparece la página \"New!\" informativa a nivel de \"Mis Compras\"",
		level=State.Defect)
	public boolean validateIsPage() {
		return pageInfoNewMisComprasMovil.isPage();
	}

	@Step(
		description = "Seleccionar el botón \"Ver mis compras\"",
		expected = "Aparece la página de \"Mis Compras\"")
	public void clickButtonToMisComprasAndNoValidate() {
		pageInfoNewMisComprasMovil.clickButtonToMisCompras();
	}
}
