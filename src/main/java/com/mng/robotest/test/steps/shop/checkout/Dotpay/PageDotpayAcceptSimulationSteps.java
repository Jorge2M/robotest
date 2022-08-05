package com.mng.robotest.test.steps.shop.checkout.Dotpay;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.dotpay.PageDotpayAcceptSimulation;

public class PageDotpayAcceptSimulationSteps {
	
	@Validation
	public static ChecksTM validateIsPage(int maxSeconds, WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
	  	checks.add(
			"Aparece la página para la aceptación de la simulación (la esperamos hasta " + maxSeconds + " segundos)",
			PageDotpayAcceptSimulation.isPage(maxSeconds, driver), State.Warn);
	  	checks.add(
			"Figura un botón de aceptar rojo",
			PageDotpayAcceptSimulation.isPresentRedButtonAceptar(driver), State.Defect);
	  	return checks;
	}
	
	@Step (
		description="Seleccionar el botón rojo para aceptar", 
		expected="Aparece la página resultado")
	public static void clickRedButtonAceptar(WebDriver driver) {
		PageDotpayAcceptSimulation.clickRedButtonAceptar(driver);
	}
}
