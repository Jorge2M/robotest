package com.mng.robotest.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.registro.pageobjects.PageCondicionesVenta;

public class PageCondicionesVentaSteps extends StepBase {

	@Validation (
		description="Aparece la página con las condiciones de venta " + SECONDS_WAIT)
	public boolean checkIsPageUntil(int seconds) {
		return new PageCondicionesVenta().isPageUntil(seconds);
	}
	
}
