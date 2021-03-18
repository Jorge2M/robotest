package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ModalAvisoCambioPais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;

public class ModalAvisoCambioPaisStpV {

	private final ModalAvisoCambioPais modalAvisoCambioPais;
	private final WebDriver driver;
	
	public ModalAvisoCambioPaisStpV(WebDriver driver) {
		this.modalAvisoCambioPais = new ModalAvisoCambioPais(driver);
		this.driver = driver;
	}
	
	@Step (
		description="Seleccionar botón \"Confirmar cambio\"", 
        expected="Aparece el modal para la introducción de la dirección de facturación")
    public void clickConfirmar(Pais paisEnvio) throws Exception {
		modalAvisoCambioPais.clickConfirmarCambio();
        checkConfirmacionCambio(paisEnvio);
    }
	
	@Validation
	private ChecksTM checkConfirmacionCambio(Pais paisEnvio) throws Exception {
    	ChecksTM validations = ChecksTM.getNew();
	    int maxSeconds = 10;
	 	validations.add(
			"Desaparece el modal de aviso de cambio de país (lo esperamos hasta " + maxSeconds + " segundos)",
			modalAvisoCambioPais.isInvisibleUntil(maxSeconds), State.Defect);    	
	 	
	 	PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper(Channel.desktop, driver);
	 	validations.add(
			"En la dirección de envió aparece el país " + paisEnvio.getNombre_pais(),
			pageCheckoutWrapper.direcEnvioContainsPais(paisEnvio.getNombre_pais()), State.Defect);   
		return validations;
	}
}
