package com.mng.robotest.test.stpv.shop.checkout.tmango;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test.pageobject.shop.checkout.tmango.PageRedsysSim;

public class PageRedsysSimStpV {

	private final PageRedsysSim pageRedsysSim;
	
	public PageRedsysSimStpV(WebDriver driver) {
		pageRedsysSim = new PageRedsysSim(driver);
	}
	
	@Validation(
		description="Aparece la página de Simulador Pago RedSys",
		level=State.Defect)
	public boolean checkPage() {
		return pageRedsysSim.isPage();
	}
	
	@Step (
		description="Seleccionamos el botón \"Enviar\"", 
		expected="Aparece la página de resultado Ok de la pasarela Redsys")
	public void clickEnviar(String CIP, String importeTotal, String codigoPais) {
		pageRedsysSim.clickEnviar();
		new PageAmexResultSteps(pageRedsysSim.driver).validateIsPageOk(importeTotal, codigoPais);
	}
}
