package com.mng.robotest.test.steps.shop.checkout.Dotpay;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.dotpay.PageDotpayAcceptSimulation;


public class PageDotpayAcceptSimulationSteps extends StepBase {
	
	PageDotpayAcceptSimulation pageDotpayAcceptSimulation = new PageDotpayAcceptSimulation();
	
	@Validation
	public ChecksTM validateIsPage(int maxSeconds) {
		ChecksTM checks = ChecksTM.getNew();
	  	checks.add(
			"Aparece la página para la aceptación de la simulación (la esperamos hasta " + maxSeconds + " segundos)",
			pageDotpayAcceptSimulation.isPage(maxSeconds), State.Warn);
	  	
	  	checks.add(
			"Figura un botón de aceptar rojo",
			pageDotpayAcceptSimulation.isPresentRedButtonAceptar(), State.Defect);
	  	
	  	return checks;
	}
	
	@Step (
		description="Seleccionar el botón rojo para aceptar", 
		expected="Aparece la página resultado")
	public void clickRedButtonAceptar() {
		pageDotpayAcceptSimulation.clickRedButtonAceptar();
	}
}
