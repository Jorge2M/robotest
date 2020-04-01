package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
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
	private static ChecksTM checkConfirmacionCambio(Pais paisEnvio, WebDriver driver) throws Exception {
    	ChecksTM validations = ChecksTM.getNew();
	    int maxSeconds = 10;
	 	validations.add(
			"Desaparece el modal de aviso de cambio de país (lo esperamos hasta " + maxSeconds + " segundos)",
			ModalAvisoCambioPais.isInvisibleUntil(maxSeconds, driver), State.Defect);    	
	 	validations.add(
			"En la dirección de envió aparece el país " + paisEnvio.getNombre_pais(),
			PageCheckoutWrapper.direcEnvioContainsPais(Channel.desktop, paisEnvio.getNombre_pais(), driver), State.Defect);   
		return validations;
	}
}
