package com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay.PageDotpayAcceptSimulation;

public class PageDotpayAcceptSimulationStpV {
    
	@Validation
    public static ChecksResult validateIsPage(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
      	validations.add(
    		"Aparece la página de Dotpay para la introducción de los datos del pagador",
    		PageDotpayAcceptSimulation.isPage(driver), State.Warn);
      	validations.add(
    		"Figura un botón de aceptar rojo",
    		PageDotpayAcceptSimulation.isPresentRedButtonAceptar(driver), State.Defect);
      	return validations;
    }
    
	@Step (
		description="Seleccionar el botón rojo para aceptar", 
        expected="Aparece la página resultado")
    public static void clickRedButtonAceptar(WebDriver driver) throws Exception {
        PageDotpayAcceptSimulation.clickRedButtonAceptar(driver);
        
        //Validation
        PageDotpayResultadoStpV.validateIsPage(driver);
    }
}
