package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.epayment.PageEpaymentIdent;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paytrail.PagePaytrailEpayment;

public class PagePaytrailEpaymentStpV {
    
	@Validation
    public static ChecksResult validateIsPage(WebDriver driver) { 
		ChecksResult validations = ChecksResult.getNew();
	   	validations.add(
    		"Aparece la página inicial de E-Payment",
    		PageEpaymentIdent.isPage(driver), State.Warn);
	   	validations.add(
    		"Figuran el input correspondientes al \"User ID\"",
    		PageEpaymentIdent.isPresentInputUserTypePassword(driver), State.Warn);	   	
	   	return validations;
    }
    
	@Step (
		description="Click en el botón \"OK\" del apartado \"Code card\"", 
        expected="Aparece la página de introducción del <b>ID de confirmación</b>")
    public static void clickCodeCardOK(String importeTotal, String codPais, WebDriver driver) throws Exception {
        PagePaytrailEpayment.clickOkFromCodeCard(driver);
        
        //Validation
        PagePaytrailIdConfirmStpV.validateIsPage(importeTotal, codPais, driver);
    }
}
