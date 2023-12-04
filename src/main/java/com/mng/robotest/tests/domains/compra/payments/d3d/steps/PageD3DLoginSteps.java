package com.mng.robotest.tests.domains.compra.payments.d3d.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.d3d.pageobjects.PageD3DLogin;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.conf.StoreType.*;

public class PageD3DLoginSteps extends StepBase {

	private final PageD3DLogin pageD3DLogin = new PageD3DLogin();
	
	@Validation (
		description="Aparece la página de identificación D3D " + SECONDS_WAIT,
		level=INFO,	store=NONE)
	public boolean validateIsD3D(int seconds) {
		return pageD3DLogin.isPage(seconds);
	}
	
	@Validation (
		description="Es visible el importe total de la operación #{importeTotal}",
		level=WARN)
	public boolean isImporteVisible(String importeTotal) {
		String codPais = dataTest.getCodigoPais();
		return ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver);
	}
	
	@Step (
		description="Autenticarse en D3D con #{user} / #{password}", 
		expected="Aparece la página de resultado OK")
	public void loginAndClickSubmit(String user, String password) {
		pageD3DLogin.inputUserPassword(user, password);
		pageD3DLogin.clickButtonSubmit();
	}
	
}
