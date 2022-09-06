package com.mng.robotest.test.steps.shop.checkout.mercadopago;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.mercadopago.PageMercpago1rst;

public class PageMercpago1rstSteps extends StepBase {
	
	private final PageMercpago1rst pageMercpago1rst = new PageMercpago1rst();
	
	@Validation (
		description="Aparece la página inicial de Mercado para la introducción de datos (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	public boolean validateIsPageUntil(int maxSeconds) {
	   return pageMercpago1rst.isPageUntil(maxSeconds);
	}
	
	@Step (
		description="Accedemos a la página de identificación", 
		expected="Aparece la página de identificación")
	public void clickLinkRegistration() {
		pageMercpago1rst.clickLinkRegistro();
		new PageMercpagoLoginSteps().validateIsPage();
	}
}
