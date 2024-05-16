package com.mng.robotest.tests.domains.compra.payments.visa.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.visa.pageobjects.PageD3DLogin;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.conf.StoreType.*;

public class PageD3DLoginSteps extends StepBase {

	private final PageD3DLogin pageD3DLogin = PageD3DLogin.make();
	
	@Validation (
		description="Aparece la página de identificación D3D " + SECONDS_WAIT,
		level=INFO,	store=NONE)
	public boolean checkIsD3D(int seconds) {
		return pageD3DLogin.isPage(seconds);
	}
	
	@Validation (
		description="Es visible el importe total de la operación #{importeTotal}",
		level=WARN)
	public boolean isImporteVisible(String importeTotal) {
		return pageD3DLogin.isImporteVisible(importeTotal);
	}
	
	@Step (
		description="Autenticarse en D3D con #{user} / #{password}", 
		expected="Aparece la página de resultado OK")
	public void loginAndClickSubmit(String user, String password) {
		pageD3DLogin.inputCredentials(user, password);
		pageD3DLogin.clickButtonSubmit();
	}
	
}
