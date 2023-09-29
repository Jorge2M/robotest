package com.mng.robotest.tests.domains.compra.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.pageobjects.ModalAvisoCambioPais;
import com.mng.robotest.tests.domains.compra.pageobjects.PageCheckoutWrapper;
import com.mng.robotest.testslegacy.beans.Pais;

public class ModalAvisoCambioPaisSteps extends StepBase {

	private final ModalAvisoCambioPais modalAvisoCambioPais = new ModalAvisoCambioPais(); 
	
	@Step (
		description="Seleccionar botón \"Confirmar cambio\"", 
		expected="Aparece el modal para la introducción de la dirección de facturación")
	public void clickConfirmar(Pais paisEnvio) {
		modalAvisoCambioPais.clickConfirmarCambio();
		checkConfirmacionCambio(paisEnvio);
	}
	
	@Validation
	private ChecksTM checkConfirmacionCambio(Pais paisEnvio) {
		var checks = ChecksTM.getNew();
		int seconds = 10;
	 	checks.add(
			"Desaparece el modal de aviso de cambio de país " + getLitSecondsWait(seconds),
			modalAvisoCambioPais.isInvisibleUntil(seconds));		
	 	
	 	checks.add(
			"En la dirección de envió aparece el país " + paisEnvio.getNombre_pais(),
			new PageCheckoutWrapper().direcEnvioContainsPais(paisEnvio.getNombre_pais()));
	 	
		return checks;
	}
}
