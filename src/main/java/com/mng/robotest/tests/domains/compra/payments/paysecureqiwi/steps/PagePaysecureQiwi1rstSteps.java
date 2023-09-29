package com.mng.robotest.tests.domains.compra.payments.paysecureqiwi.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.paysecureqiwi.pageobjects.PagePaysecureQiwi1rst;
import com.mng.robotest.tests.domains.compra.payments.paysecureqiwi.pageobjects.PagePaysecureQiwi1rst.PaysecureGateway;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PagePaysecureQiwi1rstSteps extends StepBase {

	private final PagePaysecureQiwi1rst pagePaysecureQiwi = new PagePaysecureQiwi1rst();
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal) {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la página inicial de la pasarela PaySecure",
			pagePaysecureQiwi.isPage(), Warn);
	 	
	 	String codPais = dataTest.getCodigoPais();
	 	checks.add(
			"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), Warn);
	 	
	 	checks.add(
			"Aparece el icono de Qiwi",
			pagePaysecureQiwi.isPresentIcon(PaysecureGateway.QIWI), Warn);
	 	
	 	return checks;
	}

	@Step (
		description="Seleccionar la opción de Qiwi Kошелек", 
		expected="Aparece la página de introducción del número de teléfono")
	public void clickIconPasarelaQiwi() {
		pagePaysecureQiwi.clickIcon(PaysecureGateway.QIWI);
		new PageQiwiInputTlfnSteps().validateIsPage();
	}
}
