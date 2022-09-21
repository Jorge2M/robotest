package com.mng.robotest.domains.compra.payments.multibanco.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.compra.payments.multibanco.pageobjects.PageMultibancoEnProgreso;
import com.mng.robotest.domains.transversal.PageBase;


public class PageMultibancoEnProgresoSteps extends PageBase {
	
	PageMultibancoEnProgreso pageMultibancoEnProgreso = new PageMultibancoEnProgreso();
	
	@Validation
	public ChecksTM validateIsPage() {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 3;
	   	checks.add(
			"Aparece la cabecera <b>Pagamento em progreso</b> (la esperamos hasta " + seconds + " segundos",
			pageMultibancoEnProgreso.isPageUntil(seconds), State.Warn);
	   	
	   	checks.add(
			"Figura un botón para ir al siguiente paso",
			pageMultibancoEnProgreso.isButonNextStep(), State.Defect);
	   	
	   	return checks;
	}
	
	@Step (
		description="Seleccionar el botón \"Continuar\"", 
		expected="El pago se ejecuta correctamente y aparece la correspondiente página de resultado de Mango")
	public void clickButtonNextStep() {
		pageMultibancoEnProgreso.clickButtonNextStep();
	}
}
