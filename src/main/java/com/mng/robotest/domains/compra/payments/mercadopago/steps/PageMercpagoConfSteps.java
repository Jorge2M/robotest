package com.mng.robotest.domains.compra.payments.mercadopago.steps;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.compra.payments.mercadopago.pageobjects.PageMercpagoConf;
import com.mng.robotest.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.domains.transversal.StepBase;

public class PageMercpagoConfSteps extends StepBase {

	private final PageMercpagoConf pageMercpagoConf = new PageMercpagoConf();
	
	@Validation (
		description="Estamos en la página de confirmación del pago (la esperamos hasta #{seconds} segundos)",
		level=State.Defect)
	public boolean validaIsPageUntil(int seconds) {  
		return pageMercpagoConf.isPageUntil(seconds);
	}
	
	@Step (
		description="Seleccionar el botón \"Pagar\"", 
		expected="Aparece la página de resultado")
	public void clickPagar() {
		pageMercpagoConf.clickPagar();
		new PageResultPagoSteps().validaIsPageUntil(30);
	}
}