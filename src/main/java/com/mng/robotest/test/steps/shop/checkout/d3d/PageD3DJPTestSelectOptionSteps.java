package com.mng.robotest.test.steps.shop.checkout.d3d;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test.pageobject.shop.checkout.d3d.PageD3DJPTestSelectOption;
import com.mng.robotest.test.pageobject.shop.checkout.d3d.PageD3DJPTestSelectOption.OptionD3D;
import com.mng.robotest.test.utils.ImporteScreen;


public class PageD3DJPTestSelectOptionSteps {
	
	@Validation (
		description="Aparece la página de Test correspondiente al D3D de JPMorgan (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	public static boolean validateIsD3D(int maxSeconds, WebDriver driver) {
		return (PageD3DJPTestSelectOption.isPageUntil(maxSeconds, driver));
	}	
	
	@Validation (
		description="Aparece el importe #{importeTotal} de la operación",
		level=State.Warn)
	public static boolean isImporteVisible(String importeTotal, String codPais, WebDriver driver) {
		return (ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver));
	}
	
	@Step (
		description="Seleccionamos la opción \"Successful\" y clickamos en el botón \"Submit\"", 
		expected="Aparece la página de resultado OK")
	public static void clickSubmitButton(WebDriver driver) throws Exception {
		PageD3DJPTestSelectOption.selectOption(OptionD3D.Successful, driver);
		PageD3DJPTestSelectOption.clickSubmitButton(driver);
	}
}
