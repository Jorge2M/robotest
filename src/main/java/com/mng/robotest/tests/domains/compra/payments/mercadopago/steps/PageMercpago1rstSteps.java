package com.mng.robotest.tests.domains.compra.payments.mercadopago.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.mercadopago.pageobjects.PageMercpago1rst;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageMercpago1rstSteps extends StepBase {
	
	private final PageMercpago1rst pageMercpago1rst = new PageMercpago1rst();
	
	@Validation (
		description="Aparece la página inicial de Mercado para la introducción de datos " + SECONDS_WAIT,
		level=Warn)
	public boolean validateIsPageUntil(int seconds) {
	   return pageMercpago1rst.isPageUntil(seconds);
	}
	
	@Step (
		description="Accedemos a la página de identificación", 
		expected="Aparece la página de identificación")
	public void clickLinkRegistration() {
		pageMercpago1rst.clickLinkRegistro();
		new PageMercpagoLoginSteps().validateIsPage();
	}
}
