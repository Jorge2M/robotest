package com.mng.robotest.test.steps.shop.landing;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test.pageobject.shop.landing.PageLanding;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageLandingSteps {

	private final PageLanding pageLanding;
	
	public PageLandingSteps() {
		pageLanding = new PageLanding();
	}
	
	@Validation (
		description="Aparece la página de Landing (la esperamos hasta #{seconds} segundos)",
		level=Defect)
	public boolean checkIsPage(int seconds) {
		return (pageLanding.isPageUntil(seconds));
	}
}
