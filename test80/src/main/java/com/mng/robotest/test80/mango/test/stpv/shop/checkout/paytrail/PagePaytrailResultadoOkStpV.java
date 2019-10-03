package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paytrail.PagePaytrailResultadoOk;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PagePaytrailResultadoOkStpV {
    
	@Validation
    public static ChecksResult validateIsPage(String importeTotal, String codPais, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	   	validations.add(
    		"Aparece la página de resultado Ok de Paytrail",
    		PagePaytrailResultadoOk.isPage(driver), State.Defect);
	   	validations.add(
    		"Aparece el importe de la compra: " + importeTotal,
    		ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
	   	return validations;
    }
    
	@Step (
		description="Click el botón para volver a Mango", 
        expected="Aparece la página de resultado Ok de Mango")
    public static void clickVolverAMangoButton(WebDriver driver) throws Exception {
		PagePaytrailResultadoOk.clickVolverAMangoButton(driver);
    }
}
