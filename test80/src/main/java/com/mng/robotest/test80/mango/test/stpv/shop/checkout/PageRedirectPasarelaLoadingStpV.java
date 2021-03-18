package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageRedirectPasarelaLoading;

public class PageRedirectPasarelaLoadingStpV {
    
	@Validation (
		description="Acaba desapareciendo la página de \"Por favor espere. Este proceso puede tardar...\" (esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
    public static boolean validateDisappeared(int maxSeconds, WebDriver driver) { 
		return (PageRedirectPasarelaLoading.isPageNotVisibleUntil(maxSeconds, driver));
    }
}
