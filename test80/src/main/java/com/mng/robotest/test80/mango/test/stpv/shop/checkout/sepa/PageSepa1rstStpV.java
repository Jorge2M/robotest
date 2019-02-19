package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sepa;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sepa.PageSepa1rst;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PageSepa1rstStpV {
    
	@Validation
    public static ListResultValidation validateIsPage(String nombrePago, String importeTotal, String codPais, Channel channel, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
    	validations.add(
    		"Figura el bloque correspondiente al pago <b>" + nombrePago + "</b><br>",
    		PageSepa1rst.isPresentIconoSepa(channel, driver), State.Warn);
    	
    	State stateVal = State.Warn;
        if (channel==Channel.movil_web) {
            stateVal = State.Info_NoHardcopy;
        }
    	validations.add(
    		"Aparece el importe de la compra: " + importeTotal + "<br>",
    		ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal);		
    	validations.add(
    		"Aparece la cabecera indicando la 'etapa' del pago<br>",
    		PageSepa1rst.isPresentCabeceraStep(driver), State.Warn);		
    	if (channel==Channel.desktop) {
        	validations.add(
        		"Figura el campo de introducción del titular<br>",
        		PageSepa1rst.isPresentInputTitular(driver), State.Warn);
        	validations.add(
        		"Figura el campo de introducción del la cuenta<br>",
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
        	DatosStep datosStep = TestCaseData.peekDatosStepForStep();
        	datosStep.setDescripcion("Seleccionamos el icono de SEPA. " + datosStep.getDescripcion());
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
