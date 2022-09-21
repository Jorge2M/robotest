package com.mng.robotest.test.steps.shop.checkout.d3d;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.d3d.PageD3DLogin;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageD3DLoginSteps extends StepBase {

	private final PageD3DLogin pageD3DLogin = new PageD3DLogin();
	
	@Validation (
		description="Aparece la página de identificación D3D (la esperamos hasta #{seconds} segundos)",
		level=State.Info,		
		store=StoreType.None)
	public boolean validateIsD3D(int seconds) {
		return pageD3DLogin.isPageUntil(seconds);
	}
	
	@Validation (
		description="Es visible el importe total de la operación #{importeTotal}",
		level=State.Warn)
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
