package com.mng.robotest.test80.mango.test.stpv.shop.checkout.postfinance;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance.PagePostfRedirect;

public class PagePostfRedirectStpV {

	@Validation
    public static ChecksResult isPageAndFinallyDisappears(WebDriver driver) throws Exception {
		ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 10;
	   	validations.add(
    		"Aparece una página de redirección con un botón OK",
    		PagePostfRedirect.isPresentButtonOk(driver), State.Defect);    
	   	validations.add(
    		"La página de redirección acaba desapareciendo (esperamos hasta " + maxSecondsWait + " segundos)",
    		PagePostfRedirect.isInvisibleButtonOkUntil(driver, maxSecondsWait), State.Defect);  	   	
		return validations;
    }
}
