package com.mng.robotest.domains.compra.payments.sofort.steps;

import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.sofort.pageobjects.PageSofort1rst;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;

import static com.github.jorge2m.testmaker.conf.State.*;

/**
 * Page1: la página inicial de Sofort (la posterior a la selección del botón "Confirmar Pago")
 * @author jorge.munoz
 *
 */
public class PageSofortIconosBancoSteps extends StepBase {
	
	private final PageSofort1rst pageSofort1rst = new PageSofort1rst();
	
	@Validation (
		description="Aparece la 1a página de Sofort " + SECONDS_WAIT,
		level=Warn)
	public boolean validateIsPageUntil(int seconds) {
		return pageSofort1rst.isPageVisibleUntil(seconds);
	}
	
	@Step (
		description="Seleccionar el link hacia la siguiente página de Sofort", 
		expected="Aparece la página de selección del Banco")
	public void clickIconoSofort() { 
		pageSofort1rst.clickGoToSofort();
		new PageSofort2onSteps().validaIsPageUntil(3);
	}
}
