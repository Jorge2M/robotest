package com.mng.robotest.test.steps.shop.checkout.paytrail;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.paytrail.PagePaytrailIdConfirm;
import com.mng.robotest.test.utils.ImporteScreen;

public class PagePaytrailIdConfirmSteps {
	
	@Validation
	public static ChecksTM validateIsPage(String importeTotal, String codPais, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
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
	public static void inputIDAndClickConfirmar(String idConfirm, String importeTotal, String codPais, WebDriver driver) {
		PagePaytrailIdConfirm.inputIdConfirm(idConfirm, driver);
		PagePaytrailIdConfirm.clickConfirmar(driver);
		
		//Validations
		PagePaytrailResultadoOkSteps.validateIsPage(importeTotal, codPais, driver);
	}
}
