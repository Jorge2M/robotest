package com.mng.robotest.test80.mango.test.stpv.shop.checkout.multibanco;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.multibanco.PageMultibancoEnProgreso;

public class PageMultibancoEnProgresoStpv {
    
	@Validation
    public static ChecksTM validateIsPage(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
        int maxSecondsToWait = 3;
	   	validations.add(
    		"Aparece la cabecera <b>Pagamento em progreso</b> (la esperamos hasta " + maxSecondsToWait + " segundos",
    		PageMultibancoEnProgreso.isPageUntil(maxSecondsToWait, driver), State.Warn);
	   	validations.add(
    		"Figura un botón para ir al siguiente paso",
    		PageMultibancoEnProgreso.isButonNextStep(driver), State.Defect);
	   	return validations;
    }
    
	@Step (
		description="Seleccionar el botón \"Continuar\"", 
		expected="El pago se ejecuta correctamente y aparece la correspondiente página de resultado de Mango")
	public static void clickButtonNextStep(WebDriver driver) {
		PageMultibancoEnProgreso.clickButtonNextStep(driver);
	}
}
