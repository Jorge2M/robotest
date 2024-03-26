package com.mng.robotest.tests.domains.compra.payments.mercadopago.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.mercadopago.pageobjects.PageMercpagoConf;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;

public class PageMercpagoConfSteps extends StepBase {

	private final PageMercpagoConf pageMercpagoConf = new PageMercpagoConf();
	
	@Validation (
		description="Estamos en la página de confirmación del pago " + SECONDS_WAIT)
	public boolean validaisPage(int seconds) {  
		return pageMercpagoConf.isPage(seconds);
	}
	
	@Step (
		description="Seleccionar el botón \"Pagar\"", 
		expected="Aparece la página de resultado")
	public void clickPagar() {
		pageMercpagoConf.clickPagar();
		new PageResultPagoSteps().checkIsPage(30);
	}
}