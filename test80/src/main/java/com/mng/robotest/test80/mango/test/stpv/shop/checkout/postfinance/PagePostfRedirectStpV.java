package com.mng.robotest.test80.mango.test.stpv.shop.checkout.postfinance;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance.PagePostfRedirect;

public class PagePostfRedirectStpV {

	@Validation
    public static ListResultValidation isPageAndFinallyDisappears(WebDriver driver) throws Exception {
		ListResultValidation validations = ListResultValidation.getNew();
        int maxSecondsWait = 10;
	   	validations.add(
    		"Aparece una página de redirección con un botón OK<br>",
    		PagePostfRedirect.isPresentButtonOk(driver), State.Defect);    
	   	validations.add(
    		"La página de redirección acaba desapareciendo (esperamos hasta " + maxSecondsWait + " segundos)",
    		PagePostfRedirect.isInvisibleButtonOkUntil(driver, maxSecondsWait), State.Defect);  	   	
		return validations;
    }
}
