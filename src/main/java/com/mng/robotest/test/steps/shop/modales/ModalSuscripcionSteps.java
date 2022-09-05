package com.mng.robotest.test.steps.shop.modales;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.modales.ModalSuscripcion;

public class ModalSuscripcionSteps extends StepBase {

	private final ModalSuscripcion modalSuscripcion = new ModalSuscripcion();
	
	@Step (
		description="Comprobar que se incluyen o no los textos legales de RGPD en el modal de suscripcion (el modal está en el HTML de la página no visible)",
		expected="Los textos existen en el código fuente dependiendo del pais",
		saveNettraffic=SaveWhen.Always)
	public void validaRGPDModal() {
		String codPais = dataTest.pais.getCodigo_pais();
		if (dataTest.pais.getRgpd().equals("S")) {
			checkExistsTextsRGPD(codPais);
		} else {
			checkNotExistsTextsRGPD(codPais);
		}
	}
	
	@Validation (
		description="El texto legal de RGPD <b>SI</b> existe en el modal de suscripción para el pais #{codigoPais}",
		level=State.Defect)
	private boolean checkExistsTextsRGPD(String codigoPais) {
	  	return modalSuscripcion.isTextoLegalRGPDPresent();	
	}
	
	@Validation (
		description="El texto legal de RGPD <b>NO</b> existe en el modal de suscripción para el pais #{codigoPais}",
		level=State.Defect)
	private boolean checkNotExistsTextsRGPD(String codigoPais) {
	  	return !modalSuscripcion.isTextoLegalRGPDPresent();	
	}
}
