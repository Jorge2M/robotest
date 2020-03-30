package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalConfirmacion;

public class PagePaypalConfirmacionStpV {

	@Validation (
		description="Aparece la página de Confirmación (la esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
	public static boolean validateIsPageUntil(int maxSecondsWait, WebDriver driver) {
		return (PagePaypalConfirmacion.isPageUntil(maxSecondsWait, driver));
	}

	@Step (
		description="Seleccionar el botón \"Continuar\"", 
		expected="Aparece la página de Mango de resultado OK del pago")
	public static void clickContinuarButton(WebDriver driver) { 
		PagePaypalConfirmacion.clickContinuarButton(driver);
	}
}
