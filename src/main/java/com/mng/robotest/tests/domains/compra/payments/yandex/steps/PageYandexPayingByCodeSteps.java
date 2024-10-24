package com.mng.robotest.tests.domains.compra.payments.yandex.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.yandex.pageobjects.PageYandexPayingByCode;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageYandexPayingByCodeSteps extends StepBase {
	
	private final PageYandexPayingByCode pageYandexPayingByCode = new PageYandexPayingByCode();
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal, String codPais) {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la página de <b>Paying by code</b>",
			pageYandexPayingByCode.isPage(), WARN);
	 	
	 	checks.add(
			"Aparece el importe de la compra por pantalla: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), WARN);
	 	
	 	checks.add(
			"Aparece un <b>PaymentCode</b>",
			pageYandexPayingByCode.isVisiblePaymentCode());
	 	
	 	return checks;
	}
	
	@Step (
		description="Seleccionamos el botón para volver a Mango", 
		expected="Aparece la página Mango de resultado OK del pago")
	public void clickBackToMango() {
		new PageYandexPayingByCode().clickBackToMango();
	}
}
