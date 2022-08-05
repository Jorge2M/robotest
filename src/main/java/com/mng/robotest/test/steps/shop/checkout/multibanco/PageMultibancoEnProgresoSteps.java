package com.mng.robotest.test.steps.shop.checkout.multibanco;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.multibanco.PageMultibancoEnProgreso;

public class PageMultibancoEnProgresoSteps {
	
	@Validation
	public static ChecksTM validateIsPage(WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSecondsToWait = 3;
	   	checks.add(
			"Aparece la cabecera <b>Pagamento em progreso</b> (la esperamos hasta " + maxSecondsToWait + " segundos",
			PageMultibancoEnProgreso.isPageUntil(maxSecondsToWait, driver), State.Warn);
	   	checks.add(
			"Figura un botón para ir al siguiente paso",
			PageMultibancoEnProgreso.isButonNextStep(driver), State.Defect);
	   	return checks;
	}
	
	@Step (
		description="Seleccionar el botón \"Continuar\"", 
		expected="El pago se ejecuta correctamente y aparece la correspondiente página de resultado de Mango")
	public static void clickButtonNextStep(WebDriver driver) {
		PageMultibancoEnProgreso.clickButtonNextStep(driver);
	}
}
