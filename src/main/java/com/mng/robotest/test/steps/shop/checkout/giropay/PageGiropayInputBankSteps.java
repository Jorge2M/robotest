package com.mng.robotest.test.steps.shop.checkout.giropay;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test.pageobject.shop.checkout.giropay.PageGiropayInputBank;


public class PageGiropayInputBankSteps {

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
	public void inputBankAndConfirm(String bankToInput, Channel channel) {
		pageGiropayInputBank.inputBank(bankToInput);
		pageGiropayInputBank.confirm();
	}
}
