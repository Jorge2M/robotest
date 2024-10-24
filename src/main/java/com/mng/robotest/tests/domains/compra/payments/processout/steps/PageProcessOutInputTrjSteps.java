package com.mng.robotest.tests.domains.compra.payments.processout.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.processout.pageobjects.PageProcessOutInputTrj;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageProcessOutInputTrjSteps extends StepBase {

	private final PageProcessOutInputTrj pageObject = new PageProcessOutInputTrj();
	
	@Validation
	public ChecksTM checkIsPage(String importeTotal) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Estamos en la página con el formulario para la introducción de los datos de la tarjeta",
			pageObject.checkIsPage());
		
		String codPais = dataTest.getCodigoPais();
		checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), WARN);
		
		checks.add(
			"Figura un botón de pago",
			pageObject.isPresentButtonPago());
		
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
