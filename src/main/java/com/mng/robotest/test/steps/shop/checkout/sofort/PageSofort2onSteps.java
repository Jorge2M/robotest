package com.mng.robotest.test.steps.shop.checkout.sofort;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.sofort.PageSofort2on;

public class PageSofort2onSteps extends StepBase {
	
	private final PageSofort2on pageSofort2on = new PageSofort2on();
	
	@Validation (
		description="Aparece la página de selección del país/banco (la esperamos hasta #{seconds} segundos)",
		level=State.Defect)
	public boolean validaIsPageUntil(int seconds) {
		return pageSofort2on.isPageUntil(seconds);
	}
	
	@Step (
		description="Aceptar las cookies",
		expected="Desaparece el modal de aceptación de cookies")
	public void acceptCookies() {
		pageSofort2on.acceptCookies();
	}
	
	@Step (
		description="Seleccionamos el país con código <b>#{paisSofort}</b> y el código de banco <b>#{bankCode}</b> y pulsamos \"Weiter\"",
		expected="Aparece la página de indentificación en SOFORT")
	public void selectPaisYBanco(String paisSofort, String bankCode) {
		pageSofort2on.selectPais(paisSofort);
		pageSofort2on.inputBankcode(bankCode);
		SeleniumUtils.waitForPageLoaded(driver, 5);
		pageSofort2on.clickSubmitButtonPage3();
		new PageSofort4thSteps().validaIsPage();
	}
}
