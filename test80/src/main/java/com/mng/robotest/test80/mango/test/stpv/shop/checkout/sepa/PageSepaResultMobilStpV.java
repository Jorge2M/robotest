package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sepa;

import com.mng.testmaker.utils.State;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sepa.PageSepaResultMobil;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PageSepaResultMobilStpV {
    
	@Validation
    public static ChecksResult validateIsPage(String importeTotal, String codPais, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
    	validations.add(
    		"Aparece la página de resultado de SEPA para móvil",
    		PageSepaResultMobil.isPage(driver), State.Warn);	
    	validations.add(
    		"Aparece el importe de la compra: " + importeTotal,
    		ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);	
    	return validations;
    }
    
	@Step (
		description="Seleccionamos el botón para Pagar", 
        expected="Aparece la página de resultado OK del pago en Mango")
    public static void clickButtonPagar(WebDriver driver) throws Exception {
		PageSepaResultMobil.clickButtonPay(driver);
    }
}
