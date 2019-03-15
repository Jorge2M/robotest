package com.mng.robotest.test80.mango.test.stpv.shop.checkout.trustpay;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay.PageTrustPayResult;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageTrustPayResultStpV {
    
	@Validation
    public static ChecksResult validateIsPage(String importeTotal, String codPais, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		String textHeader = "Payment In Progress";
	 	validations.add(
			"Figura el encabezamiento \"" + textHeader + "<br>",
			PageTrustPayResult.headerContains(textHeader, driver), State.Defect); 
	 	validations.add(
			"Figura el importe total de la compra (" + importeTotal + ")<br>",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn); 
	 	validations.add(
			"Figura el botón \"continue\"",
			PageTrustPayResult.isPresentButtonContinue(driver), State.Defect); 
	 	return validations;
    }
    
	@Step (
		description="Seleccionar el botón para continuar con el pago", 
        expected="El pago se completa correctamente")
    public static void clickButtonContinue(WebDriver driver) throws Exception {
		PageTrustPayResult.clickButtonContinue(driver);
    }
}
