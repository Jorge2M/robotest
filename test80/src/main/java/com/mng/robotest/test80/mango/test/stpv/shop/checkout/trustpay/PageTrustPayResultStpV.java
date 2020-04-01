package com.mng.robotest.test80.mango.test.stpv.shop.checkout.trustpay;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay.PageTrustPayResult;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageTrustPayResultStpV {
    
	@Validation
    public static ChecksTM validateIsPage(String importeTotal, String codPais, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		String textHeader = "Payment In Progress";
	 	validations.add(
			"Figura el encabezamiento \"" + textHeader,
			PageTrustPayResult.headerContains(textHeader, driver), State.Defect); 
	 	validations.add(
			"Figura el importe total de la compra (" + importeTotal + ")",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn); 
	 	validations.add(
			"Figura el botón \"continue\"",
			PageTrustPayResult.isPresentButtonContinue(driver), State.Defect); 
	 	return validations;
    }
    
	@Step (
		description="Seleccionar el botón para continuar con el pago", 
		expected="El pago se completa correctamente")
	public static void clickButtonContinue(WebDriver driver) {
		PageTrustPayResult.clickButtonContinue(driver);
	}
}
