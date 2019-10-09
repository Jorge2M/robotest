package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paytrail.PagePaytrailIdConfirm;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PagePaytrailIdConfirmStpV {
    
	@Validation
    public static ChecksResult validateIsPage(String importeTotal, String codPais, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	   	validations.add(
    		"Aparece la página de introducción del ID de confirmación",
    		PagePaytrailIdConfirm.isPage(driver), State.Defect);
	   	validations.add(
    		"Aparece el importe de la compra: " + importeTotal,
    		ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);	   	
		return validations;
    }
    
	@Step (
		description="Introducir el ID <b>idConfirm</b> y seleccionar el botón \"Confirmar\"", 
        expected="Aparece la página de introducción del <b>ID de confirmación</b>")
    public static void inputIDAndClickConfirmar(String idConfirm, String importeTotal, String codPais, WebDriver driver) 
    throws Exception {
        PagePaytrailIdConfirm.inputIdConfirm(idConfirm, driver);
        PagePaytrailIdConfirm.clickConfirmar(driver);
        
        //Validations
        PagePaytrailResultadoOkStpV.validateIsPage(importeTotal, codPais, driver);
    }
}
