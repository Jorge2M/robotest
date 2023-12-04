package com.mng.robotest.tests.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.pageobjects.PageRedirectPasarelaLoading;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageRedirectPasarelaLoadingSteps extends StepBase {
	
	@Validation (
		description="Acaba desapareciendo la página de \"Por favor espere. Este proceso puede tardar...\" " + SECONDS_WAIT,
		level=WARN)
	public boolean validateDisappeared(int seconds) { 
		return new PageRedirectPasarelaLoading().isPageNotVisibleUntil(seconds);
	}
}
