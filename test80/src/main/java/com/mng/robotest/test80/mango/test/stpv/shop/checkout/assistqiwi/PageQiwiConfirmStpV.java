package com.mng.robotest.test80.mango.test.stpv.shop.checkout.assistqiwi;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.annotations.step.Step;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assistqiwi.PageQiwiConfirm;

public class PageQiwiConfirmStpV {

	@Step (
		description="Seleccionar el botón \"Confirmar\" de la página de confirmación de Qiwi", 
        expected="Aparece la página de resultado del pago de Mango")
	public static void selectConfirmButton(WebDriver driver) throws Exception {
		PageQiwiConfirm.clickConfirmar(driver);         
    }
}
