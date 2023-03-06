package com.mng.robotest.domains.compra.payments.dotpay.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.compra.payments.dotpay.pageobjects.PageDotpayAcceptSimulation;
import com.mng.robotest.domains.transversal.StepBase;


public class PageDotpayAcceptSimulationSteps extends StepBase {
	
	private final PageDotpayAcceptSimulation pageDotpayAcceptSimulation = new PageDotpayAcceptSimulation();
	
	@Validation
	public ChecksTM validateIsPage(int seconds) {
		var checks = ChecksTM.getNew();
	  	checks.add(
			"Aparece la página para la aceptación de la simulación (la esperamos hasta " + seconds + " segundos)",
			pageDotpayAcceptSimulation.isPage(seconds), State.Warn);
	  	
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
