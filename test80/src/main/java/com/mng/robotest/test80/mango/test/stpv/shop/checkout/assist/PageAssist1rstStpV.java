package com.mng.robotest.test80.mango.test.stpv.shop.checkout.assist;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assist.PageAssist1rst;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.assist.PageAssistLast;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageAssist1rstStpV {
    
	@Validation
    public static ChecksTM validateIsPage(String importeTotal, Pais pais, Channel channel, WebDriver driver) {
    	ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Está presente el logo de Assist",
			PageAssist1rst.isPresentLogoAssist(channel, driver), State.Warn);
	 	validations.add(
			"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
			ImporteScreen.isPresentImporteInScreen(importeTotal, pais.getCodigo_pais(), driver), State.Warn);
	 	validations.add(
			"No se trata de la página de precompra (no aparece los logos de formas de pago)",
			!PageCheckoutWrapper.isPresentMetodosPago(channel, driver), State.Defect);
	 	
	 	boolean inputsTrjOk = PageAssist1rst.isPresentInputsForTrjData(channel, driver);
        if (channel==Channel.movil_web) {
    	 	validations.add(
				"Figuran 5 campos de input para los datos de la tarjeta: 1 para el número de tarjeta, 2 para la fecha de caducidad, 1 para el titular y 1 para el CVC",
				inputsTrjOk, State.Warn);
        } else {
    	 	validations.add(
				"Figuran 5 campos de input para los datos de la tarjeta: 4 para el número de tarjeta, 2 para la fecha de caducidad, 1 para el titular y 1 para el CVC",
				inputsTrjOk, State.Warn);
        }
        
        return validations;
    }
    
	@Step (
		description="Introducimos los datos de la tarjeta y pulsamos el botón de pago", 
        expected="Aparece la página de resultado de Mango")
    public static void inputDataTarjAndPay(Pago pago, Channel channel, WebDriver driver) throws Exception {
        PageAssist1rst.inputDataPagoAndWaitSubmitAvailable(pago, channel, driver);
        PageAssist1rst.clickBotonPago(channel, driver);
        checkAfterClickPayButton(channel, driver);
    }
	
	@Validation
	private static ChecksTM checkAfterClickPayButton(Channel channel, WebDriver driver) {
    	ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 10;
	 	validations.add(
			"Desaparece la página con el botón de pago (lo esperamos hasta " + maxSeconds + " segundos)",
			PageAssist1rst.invisibilityBotonPagoUntil(maxSeconds, channel, driver), State.Warn);
	 	validations.add(
			"Aparece una página intermedia con un botón de submit",
			PageAssistLast.isPage(driver), State.Warn);
	 	return validations;
	}
}