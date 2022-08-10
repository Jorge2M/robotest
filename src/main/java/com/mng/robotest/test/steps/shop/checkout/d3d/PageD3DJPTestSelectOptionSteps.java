package com.mng.robotest.test.steps.shop.checkout.d3d;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.d3d.PageD3DJPTestSelectOption;
import com.mng.robotest.test.pageobject.shop.checkout.d3d.PageD3DJPTestSelectOption.OptionD3D;
import com.mng.robotest.test.utils.ImporteScreen;


public class PageD3DJPTestSelectOptionSteps extends StepBase {
	
	private final PageD3DJPTestSelectOption pageD3DJPTestSelectOption = new PageD3DJPTestSelectOption();
	
	@Validation (
		description="Aparece la página de Test correspondiente al D3D de JPMorgan (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	public boolean validateIsD3D(int maxSeconds) {
		return pageD3DJPTestSelectOption.isPageUntil(maxSeconds);
	}	
	
	@Validation (
		description="Aparece el importe #{importeTotal} de la operación",
		level=State.Warn)
	public boolean isImporteVisible(String importeTotal, String codPais) {
		return (ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver));
	}
	
	@Step (
		description="Seleccionamos la opción \"Successful\" y clickamos en el botón \"Submit\"", 
		expected="Aparece la página de resultado OK")
	public void clickSubmitButton() throws Exception {
		pageD3DJPTestSelectOption.selectOption(OptionD3D.SUCCESSFUL);
		pageD3DJPTestSelectOption.clickSubmitButton();
	}
}
