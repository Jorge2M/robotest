package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalSelectPago;

public class PagePaypalSelectPagoStpV {
    
	@Validation (
		description="Aparece la página de Selección del Pago (la esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
    public static boolean validateIsPageUntil(int maxSecondsWait, WebDriver driver) {
        return (PagePaypalSelectPago.isPageUntil(maxSecondsWait, driver));
    }
    
    @Step (
    	description="Seleccionar el botón \"Continuar\"", 
        expected="Aparece la página de Mango de resultado OK del pago")
    public static void clickContinuarButton(WebDriver driver) throws Exception {     
        PagePaypalSelectPago.clickContinuarButton(driver);

        //Validations
        ModalPreloaderSppinerStpV.validateAppearsAndDisappears(driver);
    }
}
