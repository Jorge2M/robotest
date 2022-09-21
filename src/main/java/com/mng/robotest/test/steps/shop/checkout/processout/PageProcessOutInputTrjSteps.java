package com.mng.robotest.test.steps.shop.checkout.processout;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.pageobject.shop.checkout.processout.PageProcessOutInputTrj;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageProcessOutInputTrjSteps extends StepBase {

	private final PageProcessOutInputTrj pageObject = new PageProcessOutInputTrj();
	
	@Validation
	public ChecksTM checkIsPage(String importeTotal) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Estamos en la página con el formulario para la introducción de los datos de la tarjeta",
			pageObject.checkIsPage(), State.Defect);
		
		String codPais = dataTest.getCodigoPais();
		checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
		
		checks.add(
			"Figura un botón de pago",
			pageObject.isPresentButtonPago(), State.Defect);
		
		return checks;
	}

	@Step(
		description="Introducir los datos de la tarjeta y pulsar el botón \"Pay Now\"",
		expected="Aparece la página de resultado Ok del pago")	
	public void inputTrjAndClickPay(Pago pago) {
		pageObject.inputDataTrj(pago);
		pageObject.clickButtonPay();
	}
}
