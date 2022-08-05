package com.mng.robotest.test.steps.shop.checkout.processout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.pageobject.shop.checkout.processout.PageProcessOutInputTrj;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageProcessOutInputTrjSteps {

	private final PageProcessOutInputTrj pageObject;
	private final WebDriver driver;
	
	public PageProcessOutInputTrjSteps(WebDriver driver) {
		this.pageObject = new PageProcessOutInputTrj(driver);
		this.driver = driver;
	}
	
	@Validation
	public ChecksTM checkIsPage(String importeTotal, String codPais) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Estamos en la página con el formulario para la introducción de los datos de la tarjeta",
			pageObject.checkIsPage(), State.Defect);
		checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
		checks.add(
			"Figura un botón de pago",
			pageObject.isPresentButtonPago(), State.Defect);
		
		return checks;
	}

	@Step(
		description="Introducir los datos de la tarjeta y pulsar el botón \"Pay Now\"",
		expected="Aparece la página de resultado Ok del pago")	
	public void inputTrjAndClickPay(Pago pago) {
		pageObject.inputDataTrj(pago);
		pageObject.clickButtonPay();
	}
}
