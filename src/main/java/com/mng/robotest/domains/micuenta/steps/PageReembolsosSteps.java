package com.mng.robotest.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.reembolsos.pageobjects.PageReembolsos;
import com.mng.robotest.domains.transversal.StepBase;

public class PageReembolsosSteps extends StepBase {

	private final PageReembolsos pageReembolsos = new PageReembolsos();
	
	@Validation
	public ChecksTM validateIsPage () {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de Reembolsos",
			pageReembolsos.isPage(), State.Defect);
		
		checks.add(
			"Aparecen los inputs de BANCO, TITULAR e IBAN",
			(pageReembolsos.existsInputBanco() && pageReembolsos.existsInputTitular() && pageReembolsos.existsInputIBAN()), 
			State.Warn);
		
		return checks;
	}
}
