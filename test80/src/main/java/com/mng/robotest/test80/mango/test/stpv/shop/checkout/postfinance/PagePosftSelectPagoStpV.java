package com.mng.robotest.test80.mango.test.stpv.shop.checkout.postfinance;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance.PagePostfSelectPago;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PagePosftSelectPagoStpV {
	
	@Validation
	public static ChecksTM validateIsPage(String nombrePago, String importeTotal, String codPais, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 5;
	   	validations.add(
    		"Aparece la 1a pantalla para la selección del método <b>" + nombrePago + "</b> (la esperamos hasta " + maxSeconds + " segundos)",
    		PagePostfSelectPago.isPageUntil(nombrePago, maxSeconds, driver), State.Defect);
	   	validations.add(
    		"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
    		ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
		return validations;
	}
	
	@Step(
		description="Seleccionar el icono del pago <b>#{nombrePago}</b>", 
		expected="Aparece una página de redirección")
	public static void clickIconoPago(String nombrePago, String importeTotal, String codigoPais, WebDriver driver) {
		PagePostfSelectPago.clickIconoPago(nombrePago, driver);
		PagePostfCodSegStpV.postfinanceValidate1rstPage(nombrePago, importeTotal, codigoPais, driver);
	}
}
