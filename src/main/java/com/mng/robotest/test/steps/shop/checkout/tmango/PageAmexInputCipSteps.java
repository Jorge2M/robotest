package com.mng.robotest.test.steps.shop.checkout.tmango;


import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.tmango.PageAmexInputCip;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageAmexInputCipSteps {
	
	private final PageAmexInputCip pageAmexInputCip;
	
	public PageAmexInputCipSteps(WebDriver driver) {
		pageAmexInputCip = new PageAmexInputCip(driver);
	}
	
	@Validation
	public ChecksTM validateIsPageOk(String importeTotal, String codigoPais) {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 5;
	 	checks.add(
			"Aparece la página de introducción del CIP (la esperamos hasta " + seconds + " segundos)",
			pageAmexInputCip.isPageUntil(seconds), State.Defect); 
	 	checks.add(
			"Aparece el importe de la operación " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codigoPais, pageAmexInputCip.driver), State.Warn);
	 	return checks;
	}
	
	@Step (
		description="Introducimos el CIP #{CIP} y pulsamos el botón \"Aceptar\"", 
		expected="Aparece una página de la pasarela de resultado OK")
	public void inputCipAndAcceptButton(String CIP, String importeTotal, String codigoPais) {
		pageAmexInputCip.inputCIP(CIP);
		pageAmexInputCip.clickAceptarButton();
		new PageAmexResultSteps(pageAmexInputCip.driver).validateIsPageOk(importeTotal, codigoPais);
	}
}
