package com.mng.robotest.test80.mango.test.stpv.shop.checkout.eps;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.eps.PageEpsSimulador;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.eps.PageEpsSimulador.TypeDelay;

public class PageEpsSimuladorStpV {
	
	@Validation (
		description="Aparece la página de simulación de EPS",
		level=State.Defect)
	public static boolean validateIsPage(WebDriver driver) { 
		return (PageEpsSimulador.isPage(driver));
	}
	
	@Step (
		description="Seleccionar la opción <b>#{typeDelay}</b> del apartado \"pending-authorised\"", 
		expected="La opción se selecciona correctamente")
	public static void selectDelay(TypeDelay typeDelay, WebDriver driver) {
		PageEpsSimulador.selectDelayAuthorised(typeDelay, driver);
	}

	@Step (
		description="Seleccionar el botón \"pending > autrhorised\"", 
		expected="El pago se realiza correctamente")
	public static void clickContinueButton(WebDriver driver) {
		PageEpsSimulador.clickButtonContinue(driver);
	}
}
