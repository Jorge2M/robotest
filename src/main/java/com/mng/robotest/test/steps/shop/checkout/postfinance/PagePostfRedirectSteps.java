package com.mng.robotest.test.steps.shop.checkout.postfinance;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.postfinance.PagePostfRedirect;

public class PagePostfRedirectSteps {

	private PagePostfRedirect pagePostfRedirect = new PagePostfRedirect();
	
	@Validation
	public ChecksTM isPageAndFinallyDisappears() {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 10;
		checks.add(
			"Aparece una página de redirección con un botón OK",
			pagePostfRedirect.isPresentButtonOk(), State.Defect);
		
		checks.add(
			"La página de redirección acaba desapareciendo (esperamos hasta " + seconds + " segundos)",
			pagePostfRedirect.isInvisibleButtonOkUntil(seconds), State.Defect);
		
		return checks;
	}
}
