package com.mng.robotest.domains.compra.payments.sofort.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.domains.compra.payments.sofort.pageobjects.PageSofort2on;
import com.mng.robotest.domains.transversal.StepBase;

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
		PageObjTM.waitForPageLoaded(driver, 5);
		pageSofort2on.clickSubmitButtonPage3();
		new PageSofort4thSteps().validaIsPage();
	}
}
