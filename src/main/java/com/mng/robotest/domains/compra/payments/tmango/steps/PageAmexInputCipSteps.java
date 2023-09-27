package com.mng.robotest.domains.compra.payments.tmango.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.tmango.pageobjects.PageAmexInputCip;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageAmexInputCipSteps extends StepBase {
	
	private final PageAmexInputCip pageAmexInputCip = new PageAmexInputCip();
	
	@Validation
	public ChecksTM validateIsPageOk(String importeTotal, String codigoPais) {
		var checks = ChecksTM.getNew();
		int seconds = 5;
	 	checks.add(
			"Aparece la página de introducción del CIP " + getLitSecondsWait(seconds),
			pageAmexInputCip.isPageUntil(seconds));
	 	
	 	checks.add(
			"Aparece el importe de la operación " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codigoPais, pageAmexInputCip.driver), Warn);
	 	
	 	return checks;
	}
	
	@Step (
		description="Introducimos el CIP #{CIP} y pulsamos el botón \"Aceptar\"", 
		expected="Aparece una página de la pasarela de resultado OK")
	public void inputCipAndAcceptButton(String cip, String importeTotal) {
		pageAmexInputCip.inputCIP(cip);
		pageAmexInputCip.clickAceptarButton();
		new PageAmexResultSteps().validateIsPageOk(importeTotal);
	}
}
