package com.mng.robotest.test80.mango.test.stpv.shop.landing;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.landing.PageLanding;

public class PageLandingStpV {

	private final PageLanding pageLanding;
	
	public PageLandingStpV(WebDriver driver) {
		pageLanding = new PageLanding(driver);
	}
	
	@Validation (
		description="Aparece la página de Landing (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkIsPage(int maxSeconds) {
		return (pageLanding.isPageUntil(maxSeconds));
	}
}
