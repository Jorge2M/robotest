package com.mng.robotest.tests.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.reembolsos.pageobjects.PageReembolsos;

public class PageReembolsosSteps extends StepBase {

	private final PageReembolsos pgReembolsos = new PageReembolsos();
	
	@Validation
	public ChecksTM checkIsPage () {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de Reembolsos",
			pgReembolsos.isPage());
		
		int seconds = 5;
		checks.add(
			"Aparecen los inputs de BANCO, TITULAR e IBAN " + getLitSecondsWait(seconds),
			(pgReembolsos.existsInputBanco(seconds) && pgReembolsos.existsInputTitular() && pgReembolsos.existsInputIBAN()));
		
		return checks;
	}
}
