package com.mng.robotest.test80.mango.test.stpv.shop.modales;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalSuscripcion;

public class ModalSuscripcionStpV {

	@Step (
		description="Comprobar que se incluyen o no los textos legales de RGPD en el modal de suscripcion (el modal está en el HTML de la página no visible)",
		expected="Los textos existen en el código fuente dependiendo del pais",
		saveNettraffic=SaveWhen.Always)
	public static void validaRGPDModal(DataCtxShop dCtxSh, WebDriver driver) {
		String codPais = dCtxSh.pais.getCodigo_pais();
		if (dCtxSh.pais.getRgpd().equals("S")) {
			checkExistsTextsRGPD(codPais, driver);
		} else {
			checkNotExistsTextsRGPD(codPais, driver);
		}
	}
	
	@Validation (
		description="El texto legal de RGPD <b>SI</b> existe en el modal de suscripción para el pais #{codigoPais}",
		level=State.Defect)
	private static boolean checkExistsTextsRGPD(String codigoPais, WebDriver driver) {
	  	return ModalSuscripcion.isTextoLegalRGPDPresent(driver);	
	}
	
	@Validation (
		description="El texto legal de RGPD <b>NO</b> existe en el modal de suscripción para el pais #{codigoPais}",
		level=State.Defect)
	private static boolean checkNotExistsTextsRGPD(String codigoPais, WebDriver driver) {
	  	return !ModalSuscripcion.isTextoLegalRGPDPresent(driver);	
	}
}
