package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sepa;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.TestCaseData;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.domain.StepTestMaker;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sepa.PageSepa1rst;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PageSepa1rstStpV {
    
	@Validation
    public static ChecksResult validateIsPage(String nombrePago, String importeTotal, String codPais, Channel channel, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
    	validations.add(
    		"Figura el bloque correspondiente al pago <b>" + nombrePago + "</b>",
    		PageSepa1rst.isPresentIconoSepa(channel, driver), State.Warn);
    	
    	State stateVal = State.Warn;
    	boolean avoidEvidences = false;
        if (channel==Channel.movil_web) {
            stateVal = State.Info;
            avoidEvidences = true;
        }
    	validations.add(
    		"Aparece el importe de la compra: " + importeTotal,
    		ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal, avoidEvidences);		
    	validations.add(
    		"Aparece la cabecera indicando la 'etapa' del pago",
    		PageSepa1rst.isPresentCabeceraStep(driver), State.Warn);		
    	if (channel==Channel.desktop) {
        	validations.add(
        		"Figura el campo de introducción del titular",
        		PageSepa1rst.isPresentInputTitular(driver), State.Warn);
        	validations.add(
        		"Figura el campo de introducción del la cuenta",
        		PageSepa1rst.isPresentInputCuenta(driver), State.Warn);
        	validations.add(
        		"Figura un botón de pago",
    			PageSepa1rst.isPresentButtonPagoDesktop(driver), State.Defect);
    	}
    	
    	return validations;
    }
    
	@Step (
		description=
			"Introducimos los datos:<br>" +
	        "  - Titular: <b>#{titular}</b><br>" +
	        "  - Cuenta: <b>#{iban}</b></br>" +
	        "Y pulsamos el botón <b>Pay</b>",
	    expected="Aparece la página de resultado de pago OK de Mango")
    public static void inputDataAndclickPay(String iban, String titular, String importeTotal, String codPais, Channel channel, WebDriver driver) 
    throws Exception {
        if (channel==Channel.movil_web) {
        	StepTestMaker StepTestMaker = TestCaseData.getDatosCurrentStep();
        	StepTestMaker.setDescripcion("Seleccionamos el icono de SEPA. " + StepTestMaker.getDescripcion());
        	PageSepa1rst.clickIconoSepa(channel, driver);
        }
 
        PageSepa1rst.inputTitular(titular, driver);
        PageSepa1rst.inputCuenta(iban, driver);
        PageSepa1rst.clickAcepto(driver);
        PageSepa1rst.clickButtonContinuePago(channel, driver);
        
        //En el caso de móvil aparece una página de resultado específica de SEPA
        if (channel==Channel.movil_web) {
            PageSepaResultMobilStpV.validateIsPage(importeTotal, codPais, driver);
        }
    }
}
