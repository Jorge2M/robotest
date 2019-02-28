package com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.PageAmexResult;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageAmexResultStpV {

	@Validation
    public static ListResultValidation validateIsPageOk(String importeTotal, String codigoPais, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
    	int maxSecondsWait = 2;
	 	validations.add(
			"Aparece una página con un mensaje de OK (lo esperamos hasta " + maxSecondsWait + " segundos)<br>",
			PageAmexResult.isResultOkUntil(maxSecondsWait, driver), State.Defect); 
	 	validations.add(
			"Aparece el importe de la operación " + importeTotal + "<br>",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codigoPais, driver), State.Warn); 
	 	validations.add(
			"Aparece un botón \"CONTINUAR\"",
			PageAmexResult.isPresentContinueButton(driver), State.Defect);
	 	return validations;
    }
    
    @Step (
    	description="Seleccionamos el botón \"Continuar\"", 
        expected="Aparece la página de Mango de resultado OK del pago")
    public static void clickContinuarButton(WebDriver driver) throws Exception {
    	PageAmexResult.clickContinuarButton(driver);
    }
}