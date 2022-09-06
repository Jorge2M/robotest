package com.mng.robotest.test.steps.shop.checkout.paysecureqiwi;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.paysecureqiwi.PagePaysecureQiwi1rst;
import com.mng.robotest.test.pageobject.shop.checkout.paysecureqiwi.PagePaysecureQiwi1rst.PaysecureGateway;
import com.mng.robotest.test.utils.ImporteScreen;

public class PagePaysecureQiwi1rstSteps extends StepBase {

	private final PagePaysecureQiwi1rst pagePaysecureQiwi = new PagePaysecureQiwi1rst();
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal, String codPais, Channel channel) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la página inicial de la pasarela PaySecure",
			pagePaysecureQiwi.isPage(), State.Warn);
	 	
	 	checks.add(
			"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
	 	
	 	checks.add(
			"Aparece el icono de Qiwi",
			pagePaysecureQiwi.isPresentIcon(PaysecureGateway.Qiwi), State.Warn);
	 	
	 	return checks;
	}

	@Step (
		description="Seleccionar la opción de Qiwi Kошелек", 
		expected="Aparece la página de introducción del número de teléfono")
	public void clickIconPasarelaQiwi(Channel channel) throws Exception {
		pagePaysecureQiwi.clickIcon(PaysecureGateway.Qiwi);
		new PageQiwiInputTlfnSteps().validateIsPage();
	}
}
