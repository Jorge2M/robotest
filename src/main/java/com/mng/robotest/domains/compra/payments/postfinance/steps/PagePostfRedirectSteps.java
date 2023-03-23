package com.mng.robotest.domains.compra.payments.postfinance.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.compra.payments.postfinance.pageobjects.PagePostfRedirect;

public class PagePostfRedirectSteps {

	private PagePostfRedirect pagePostfRedirect = new PagePostfRedirect();
	
	@Validation
	public ChecksTM isPageAndFinallyDisappears() {
		var checks = ChecksTM.getNew();
		int seconds = 10;
		checks.add(
			"Aparece una página de redirección con un botón OK",
			pagePostfRedirect.isPresentButtonOk());
		
		checks.add(
			"La página de redirección acaba desapareciendo (esperamos hasta " + seconds + " segundos)",
			pagePostfRedirect.isInvisibleButtonOkUntil(seconds));
		
		return checks;
	}
}
