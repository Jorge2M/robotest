package com.mng.robotest.test80.mango.test.stpv.shop.checkout.ideal;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ideal.PageIdealSimulador;

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
    public static void clickContinueButton(WebDriver driver) throws Exception {
		PageIdealSimulador.clickButtonContinue(driver);
    }
}
