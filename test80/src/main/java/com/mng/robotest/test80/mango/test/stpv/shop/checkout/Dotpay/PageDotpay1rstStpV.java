package com.mng.robotest.test80.mango.test.stpv.shop.checkout.Dotpay;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.dotpay.PageDotpay1rst;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageDotpay1rstStpV {
    
	@Validation
    public static ChecksResult validateIsPage(String nombrePago, String importeTotal, String codPais, Channel channel, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
      	validations.add(
    		"Figura el bloque correspondiente al pago <b>" + nombrePago + "</b>",
    		PageDotpay1rst.isPresentEntradaPago(nombrePago, channel, driver), State.Warn);
      	
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
    		PageDotpay1rst.isPresentCabeceraStep(nombrePago, channel, driver), State.Warn);
      	if (channel==Channel.desktop) {
          	validations.add(
        		"Figura un botón de pago",
        		PageDotpay1rst.isPresentButtonPago(driver), State.Defect);
      	}
      	
      	return validations;
    }
    
	@Step (
		description="Seleccionar el link hacia el Pago", 
        expected="Aparece la página de selección del canal de pago")
    public static void clickToPay(String importeTotal, String codPais, Channel channel, WebDriver driver) throws Exception {
        PageDotpay1rst.clickToPay(channel, driver);
        PageDotpayPaymentChannelStpV.validateIsPage(importeTotal, codPais, driver);
    }
}
