package com.mng.robotest.tests.domains.compra.payments.ideal.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.ideal.pageobjects.SecIdealCheckout;
import com.mng.robotest.tests.domains.compra.payments.ideal.pageobjects.SecIdealCheckout.BancoSeleccionado;

public class SecIdealCheckoutSteps extends StepBase {

	private final SecIdealCheckout secIdeal = new SecIdealCheckout();

	@Validation (
		description="Aparece el bloque de selección del banco")
	public boolean checkIsSectionOk() {
		return secIdeal.isVisibleSelectorOfBank(1);
	}
	
	@Step (
		description="Seleccionar el banco \"#{bancoSeleccionado}\"", 
		expected="El resultado es correcto")
	public void clickBanco(BancoSeleccionado bancoSeleccionado) {
		secIdeal.clickBancoByValue(bancoSeleccionado);
	}
	
}