package com.mng.robotest.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.pageobjects.PageRedirectPasarelaLoading;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageRedirectPasarelaLoadingSteps extends StepBase {
	
	@Validation (
		description="Acaba desapareciendo la página de \"Por favor espere. Este proceso puede tardar...\" (esperamos hasta #{seconds} segundos)",
		level=Warn)
	public boolean validateDisappeared(int seconds) { 
		return new PageRedirectPasarelaLoading().isPageNotVisibleUntil(seconds);
	}
}
