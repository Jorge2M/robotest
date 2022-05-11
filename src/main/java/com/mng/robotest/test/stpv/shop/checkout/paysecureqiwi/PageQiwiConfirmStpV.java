package com.mng.robotest.test.stpv.shop.checkout.paysecureqiwi;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.test.pageobject.shop.checkout.paysecureqiwi.PagePaysecureConfirm;

public class PageQiwiConfirmStpV {

	@Step (
		description="Seleccionar el botón \"Confirmar\" de la página de confirmación de Qiwi", 
		expected="Aparece la página de resultado del pago de Mango")
	public static void selectConfirmButton(WebDriver driver) throws Exception {
		PagePaysecureConfirm.clickConfirmar(driver);		 
	}
}
