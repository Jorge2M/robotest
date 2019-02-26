package com.mng.robotest.test80.mango.test.stpv.shop.checkout.postfinance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance.PagePostfSelectPago;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PagePosftSelectPagoStpV {

	static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
	
	@Validation
	public static ListResultValidation validateIsPage(String nombrePago, String importeTotal, String codPais, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
		int maxSecondsWait = 5;
	   	validations.add(
    		"Aparece la 1a pantalla para la selección del método <b>" + nombrePago + "</b> (la esperamos hasta " + maxSecondsWait + " segundos)<br>",
    		PagePostfSelectPago.isPageUntil(nombrePago, maxSecondsWait, driver), State.Defect);
	   	validations.add(
    		"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
    		ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
		return validations;
	}
	
	@Step(
		description="Seleccionar el icono del pago <b>#{nombrePago}</b>", 
        expected="Aparece una página de redirección")
	public static void clickIconoPago(String nombrePago, String importeTotal, String codigoPais, WebDriver driver) 
	throws Exception {
		PagePostfSelectPago.clickIconoPago(nombrePago, driver);
		
        //Validation
		PagePostfCodSegStpV.postfinanceValidate1rstPage(nombrePago, importeTotal, codigoPais, driver);
	}
}
