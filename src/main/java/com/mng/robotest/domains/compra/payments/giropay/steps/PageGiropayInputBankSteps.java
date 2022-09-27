package com.mng.robotest.domains.compra.payments.giropay.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.compra.payments.giropay.pageobjects.PageGiropayInputBank;
import com.mng.robotest.domains.transversal.StepBase;

public class PageGiropayInputBankSteps extends StepBase {

	private final PageGiropayInputBank pageGiropayInputBank = new PageGiropayInputBank();
	
	@Validation(
		description="Estamos en la página de introducción del banco",
		level=State.Warn)
	public boolean checkIsPage() {
		return pageGiropayInputBank.checkIsPage();
	}
	
	@Step (
		description="Introducimos el banco \"#{bankToInput}\" y pulsamos el botón de confirmación", 
		expected="Aparece la página Mango de resultado del pago")
	public void inputBankAndConfirm(String bankToInput) {
		pageGiropayInputBank.inputBank(bankToInput);
		pageGiropayInputBank.confirm();
	}
}