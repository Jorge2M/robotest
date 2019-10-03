package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageRedirectPasarelaLoading;

public class PageRedirectPasarelaLoadingStpV {
    
	@Validation (
		description="Acaba desapareciendo la página de \"Por favor espere. Este proceso puede tardar...\" (esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Warn)
    public static boolean validateDisappeared(int maxSecondsWait, WebDriver driver) { 
		return (PageRedirectPasarelaLoading.isPageNotVisibleUntil(maxSecondsWait, driver));
    }
}
