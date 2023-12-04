package com.mng.robotest.tests.domains.transversal.modales.pageobject;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalSuscripcion;

import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class ModalSuscripcionSteps extends StepBase {

	private final ModalSuscripcion modalSuscripcion = new ModalSuscripcion();
	
	@Step (
		description="Comprobar que se incluyen o no los textos legales de RGPD en el modal de suscripcion (el modal está en el HTML de la página no visible)",
		expected="Los textos existen en el código fuente dependiendo del pais",
		saveNettraffic=ALWAYS)
	public void validaRGPDModal() {
		String codPais = dataTest.getCodigoPais();
		if (dataTest.getPais().getRgpd().equals("S")) {
			checkExistsTextsRGPD(codPais);
		} else {
			checkNotExistsTextsRGPD(codPais);
		}
	}
	
	@Validation (
		description="El texto legal de RGPD <b>SI</b> existe en el modal de suscripción para el pais #{codigoPais}")
	private boolean checkExistsTextsRGPD(String codigoPais) {
	  	return modalSuscripcion.isTextoLegalRGPDPresent();	
	}
	
	@Validation (
		description="El texto legal de RGPD <b>NO</b> existe en el modal de suscripción para el pais #{codigoPais}")
	private boolean checkNotExistsTextsRGPD(String codigoPais) {
	  	return !modalSuscripcion.isTextoLegalRGPDPresent();	
	}
}
