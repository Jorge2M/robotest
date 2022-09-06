package com.mng.robotest.test.steps.shop.checkout.mercadopago;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.mercadopago.PageMercpagoConf;
import com.mng.robotest.test.steps.shop.checkout.PageResultPagoSteps;

public class PageMercpagoConfSteps extends StepBase {

	private final PageMercpagoConf pageMercpagoConf = new PageMercpagoConf();
	
	@Validation (
		description="Estamos en la página de confirmación del pago (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean validaIsPageUntil(int maxSeconds) {  
		return pageMercpagoConf.isPageUntil(maxSeconds);
	}
	
	@Step (
		description="Seleccionar el botón \"Pagar\"", 
		expected="Aparece la página de resultado")
	public void clickPagar() {
		pageMercpagoConf.clickPagar();
		new PageResultPagoSteps().validaIsPageUntil(30);
	}
}