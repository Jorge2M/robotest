package com.mng.robotest.test80.mango.test.stpv.shop.checkout.multibanco;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.multibanco.PageMultibanco1rst;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageMultibanco1rstStpV {
	
	@Validation
    public static ChecksResult validateIsPage(String nombrePago, String importeTotal, String emailUsr, String codPais, Channel channel, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	   	validations.add(
    		"Figura el bloque correspondiente al pago <b>" + nombrePago + "</b>",
    		PageMultibanco1rst.isPresentEntradaPago(nombrePago, channel, driver), State.Warn);
	   	
	   	State stateVal = State.Warn;
        if (channel==Channel.movil_web) {
        	stateVal = State.Info;
        }
	   	validations.add(
    		"Aparece el importe de la compra: " + importeTotal,
    		ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal);
	   	validations.add(
    		"Aparece la cabecera indicando la 'etapa' del pago",
    		PageMultibanco1rst.isPresentCabeceraStep(driver), State.Warn);
	   	
        if (channel==Channel.desktop) {
    	   	validations.add(
	    		"Aparece un campo de introducción de email (informado con <b>" + emailUsr + "</b>)",
	    		PageMultibanco1rst.isPresentEmailUsr(emailUsr, driver), State.Warn);
    	   	validations.add(
	    		"Figura un botón de pago",
	    		PageMultibanco1rst.isPresentButtonPagoDesktop(driver), State.Defect);    	   	
        }
        
        return validations;
    }
    
	@Step (
		description="Seleccionar el botón \"Pagar\"", 
        expected="Aparece la página de \"En progreso\"")
    public static void continueToNextPage(Channel channel, WebDriver driver) throws Exception {
        PageMultibanco1rst.continueToNextPage(channel, driver);
        PageMultibancoEnProgresoStpv.validateIsPage(driver);
    }
}
