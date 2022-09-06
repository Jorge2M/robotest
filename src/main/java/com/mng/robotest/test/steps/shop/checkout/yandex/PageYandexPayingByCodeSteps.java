package com.mng.robotest.test.steps.shop.checkout.yandex;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.yandex.PageYandexPayingByCode;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageYandexPayingByCodeSteps extends StepBase {
	
	private final PageYandexPayingByCode pageYandexPayingByCode = new PageYandexPayingByCode();
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal, String codPais) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la página de <b>Paying by code</b>",
			pageYandexPayingByCode.isPage(), State.Warn);
	 	
	 	checks.add(
			"Aparece el importe de la compra por pantalla: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
	 	
	 	checks.add(
			"Aparece un <b>PaymentCode</b>",
			pageYandexPayingByCode.isVisiblePaymentCode(), State.Defect);
	 	
	 	return checks;
	}
	
	@Step (
		description="Seleccionamos el botón para volver a Mango", 
		expected="Aparece la página Mango de resultado OK del pago")
	public void clickBackToMango() throws Exception {
		new PageYandexPayingByCode().clickBackToMango();
	}
}
