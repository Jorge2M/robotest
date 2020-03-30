package com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango;


import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.PageAmexInputCip;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageAmexInputCipStpV {
    
	@Validation
    public static ChecksTM validateIsPageOk(String importeTotal, String codigoPais, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
        int maxSecondsWait = 5;
	 	validations.add(
			"Aparece la página de introducción del CIP (la esperamos hasta " + maxSecondsWait + " segundos)",
			PageAmexInputCip.isPageUntil(maxSecondsWait, driver), State.Defect); 
	 	validations.add(
			"Aparece el importe de la operación " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codigoPais, driver), State.Warn);
	 	return validations;
    }
    
	@Step (
		description="Introducimos el CIP #{CIP} y pulsamos el botón \"Aceptar\"", 
        expected="Aparece una página de la pasarela de resultado OK")
    public static void inputCipAndAcceptButton(String CIP, String importeTotal, String codigoPais, WebDriver driver) {
        PageAmexInputCip.inputCIP(CIP, driver);
        PageAmexInputCip.clickAceptarButton(driver);
                    
        //Validaciones
        PageAmexResultStpV.validateIsPageOk(importeTotal, codigoPais, driver);
    }
}
