package com.mng.robotest.test80.mango.test.stpv.shop.checkout.multibanco;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.multibanco.PageMultibanco1rst;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageMultibanco1rstStpV {
	
	@Validation
    public static ListResultValidation validateIsPage(String nombrePago, String importeTotal, String emailUsr, String codPais, Channel channel, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
	   	validations.add(
    		"Figura el bloque correspondiente al pago <b>" + nombrePago + "</b><br>",
    		PageMultibanco1rst.isPresentEntradaPago(nombrePago, channel, driver), State.Warn);
	   	
	   	State stateVal = State.Warn;
        if (channel==Channel.movil_web) {
        	stateVal = State.Info;
        }
	   	validations.add(
    		"Aparece el importe de la compra: " + importeTotal + "<br>",
    		ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal);
	   	validations.add(
    		"Aparece la cabecera indicando la 'etapa' del pago<br>",
    		PageMultibanco1rst.isPresentCabeceraStep(driver), State.Warn);
	   	
        if (channel==Channel.desktop) {
    	   	validations.add(
	    		"Aparece un campo de introducción de email (informado con <b>" + emailUsr + "</b>)<br>",
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
        
        //Validaciones
        PageMultibancoEnProgresoStpv.validateIsPage(driver);
    }
}
