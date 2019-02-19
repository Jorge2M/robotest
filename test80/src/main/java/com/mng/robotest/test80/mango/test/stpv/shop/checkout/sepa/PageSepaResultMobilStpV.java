package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sepa;

import com.mng.robotest.test80.arq.utils.State;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sepa.PageSepaResultMobil;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PageSepaResultMobilStpV {
    
	@Validation
    public static ListResultValidation validateIsPage(String importeTotal, String codPais, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
    	validations.add(
    		"Aparece la página de resultado de SEPA para móvil<br>",
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
