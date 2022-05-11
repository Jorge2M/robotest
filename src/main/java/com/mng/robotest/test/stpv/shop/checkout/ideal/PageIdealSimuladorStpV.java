package com.mng.robotest.test.stpv.shop.checkout.ideal;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test.pageobject.shop.checkout.ideal.PageIdealSimulador;

public class PageIdealSimuladorStpV {

	@Validation (
		description="Aparece la página de simulación de Ideal",
		level=State.Defect)
	public static boolean validateIsPage(WebDriver driver) { 
		return (PageIdealSimulador.isPage(driver));
	}

	@Step (
		description="Seleccionar el botón \"Continuar\"", 
		expected="El pago se realiza correctamente")
	public static void clickContinueButton(WebDriver driver) {
		PageIdealSimulador.clickButtonContinue(driver);
	}
}
