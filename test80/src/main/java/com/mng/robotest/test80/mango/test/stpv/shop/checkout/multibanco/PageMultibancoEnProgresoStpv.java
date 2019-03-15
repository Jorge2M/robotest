package com.mng.robotest.test80.mango.test.stpv.shop.checkout.multibanco;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.multibanco.PageMultibancoEnProgreso;

public class PageMultibancoEnProgresoStpv {
    
	@Validation
    public static ChecksResult validateIsPage(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
        int maxSecondsToWait = 3;
	   	validations.add(
    		"Aparece la cabecera <b>Pagamento em progreso</b> (la esperamos hasta " + maxSecondsToWait + " segundos<br>",
    		PageMultibancoEnProgreso.isPageUntil(maxSecondsToWait, driver), State.Warn);
	   	validations.add(
    		"Figura un botón para ir al siguiente paso",
    		PageMultibancoEnProgreso.isButonNextStep(driver), State.Defect);
	   	return validations;
    }
    
	@Step (
		description="Seleccionar el botón \"Continuar\"", 
        expected="El pago se ejecuta correctamente y aparece la correspondiente página de resultado de Mango")
    public static void clickButtonNextStep(WebDriver driver) throws Exception {
		PageMultibancoEnProgreso.clickButtonNextStep(driver);
    }
}
