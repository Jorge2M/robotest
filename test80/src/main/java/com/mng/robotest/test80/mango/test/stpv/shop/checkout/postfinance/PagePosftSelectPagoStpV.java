package com.mng.robotest.test80.mango.test.stpv.shop.checkout.postfinance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.conf.Log4jConfig;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.postfinance.PagePostfSelectPago;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PagePosftSelectPagoStpV {

	static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);
	
	@Validation
	public static ChecksResult validateIsPage(String nombrePago, String importeTotal, String codPais, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		int maxSecondsWait = 5;
	   	validations.add(
    		"Aparece la 1a pantalla para la selección del método <b>" + nombrePago + "</b> (la esperamos hasta " + maxSecondsWait + " segundos)",
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
