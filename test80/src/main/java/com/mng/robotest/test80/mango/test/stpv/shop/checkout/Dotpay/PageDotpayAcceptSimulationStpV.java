package com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay.PageDotpayAcceptSimulation;

public class PageDotpayAcceptSimulationStpV {
    
	@Validation
    public static ListResultValidation validateIsPage(WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
      	validations.add(
    		"Aparece la página de Dotpay para la introducción de los datos del pagador<br>",
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
