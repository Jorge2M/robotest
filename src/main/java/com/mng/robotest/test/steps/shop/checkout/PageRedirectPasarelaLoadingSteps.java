package com.mng.robotest.test.steps.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test.pageobject.shop.checkout.PageRedirectPasarelaLoading;

public class PageRedirectPasarelaLoadingSteps {
	
	@Validation (
		description="Acaba desapareciendo la página de \"Por favor espere. Este proceso puede tardar...\" (esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	public static boolean validateDisappeared(int maxSeconds, WebDriver driver) { 
		return new PageRedirectPasarelaLoading().isPageNotVisibleUntil(maxSeconds);
	}
}
