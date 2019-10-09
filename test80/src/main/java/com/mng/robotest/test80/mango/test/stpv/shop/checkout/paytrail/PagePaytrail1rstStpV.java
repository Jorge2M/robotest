package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paytrail;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paytrail.PagePaytrail1rst;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PagePaytrail1rstStpV {
    
	@Validation
    public static ChecksResult validateIsPage(String importeTotal, String codPais, Channel channel, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		String nombrePagoCabecera = "Finnish E-Banking";
		int maxSecondsToWait = 2;
    	validations.add(
    		"Figura el bloque correspondiente al pago <b>" + nombrePagoCabecera + "</b>",
    		PagePaytrail1rst.isPresentEntradaPago(nombrePagoCabecera, driver), State.Warn);
    	
    	State stateVal = State.Warn;
        if (channel==Channel.movil_web) {
        	stateVal = State.Info;
        }
    	validations.add(
    		"Aparece el importe de la compra: \" + importeTotal",
    		ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal);  
    	
		if (channel==Channel.desktop) {
	    	validations.add(
        		"Es visible el desplegable de bancos (lo esperamos hasta " + maxSecondsToWait + " seconds)",
        		PagePaytrail1rst.isVisibleSelectBancosUntil(maxSecondsToWait, driver), State.Warn);
	    	validations.add(
        		"Figura un botón de pago",
        		PagePaytrail1rst.isPresentButtonPago(driver), State.Defect);
		}
		
		return validations;
    }
    
	@Step (
		description="Seleccionar el banco <b>Nordea</b> del desplegable y pulsar el botón \"Continue\"", 
        expected="Aparece la página de identificación de E-payment")
    public static void selectBancoAndContinue(Channel channel, WebDriver driver) throws Exception {
        PagePaytrail1rst.selectBanco("Nordea", channel, driver);
        PagePaytrail1rst.clickButtonContinue(channel, driver);
        
        //Validaciones
        PagePaytrailEpaymentStpV.validateIsPage(driver);
    }
}