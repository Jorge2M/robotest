package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ModalAvisoCambioPais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;

public class ModalAvisoCambioPaisStpV {

	@Step (
		description="Seleccionar botón \"Confirmar cambio\"", 
        expected="Aparece el modal para la introducción de la dirección de facturación")
    public static void clickConfirmar(Pais paisEnvio, WebDriver driver) throws Exception {
        ModalAvisoCambioPais.clickConfirmarCambio(driver);

        //Validaciones
        checkConfirmacionCambio(paisEnvio, driver);
    }
	
	@Validation
	private static ChecksResult checkConfirmacionCambio(Pais paisEnvio, WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
	    int maxSecondsWait = 10;
	 	validations.add(
			"Desaparece el modal de aviso de cambio de país (lo esperamos hasta " + maxSecondsWait + " segundos)",
			ModalAvisoCambioPais.isInvisibleUntil(maxSecondsWait, driver), State.Defect);    	
	 	validations.add(
			"En la dirección de envió aparece el país " + paisEnvio.getNombre_pais(),
			PageCheckoutWrapper.direcEnvioContainsPais(Channel.desktop, paisEnvio.getNombre_pais(), driver), State.Defect);   
		return validations;
	}
}
