package com.mng.robotest.test.steps.shop.checkout;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.checkout.ModalAvisoCambioPais;
import com.mng.robotest.test.pageobject.shop.checkout.PageCheckoutWrapper;


public class ModalAvisoCambioPaisSteps extends StepBase {

	private final ModalAvisoCambioPais modalAvisoCambioPais;
	private final AppEcom app;
	
	public ModalAvisoCambioPaisSteps(AppEcom app) {
		this.modalAvisoCambioPais = new ModalAvisoCambioPais();
		this.app = app;
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
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 10;
	 	checks.add(
			"Desaparece el modal de aviso de cambio de país (lo esperamos hasta " + maxSeconds + " segundos)",
			modalAvisoCambioPais.isInvisibleUntil(maxSeconds), State.Defect);		
	 	
	 	PageCheckoutWrapper pageCheckoutWrapper = new PageCheckoutWrapper(channel, app);
	 	checks.add(
			"En la dirección de envió aparece el país " + paisEnvio.getNombre_pais(),
			pageCheckoutWrapper.direcEnvioContainsPais(paisEnvio.getNombre_pais()), State.Defect);   
		return checks;
	}
}
