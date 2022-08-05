package com.mng.robotest.test.steps.shop.micuenta;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.PageReembolsos;

import org.openqa.selenium.WebDriver;


public class PageReembolsosSteps {

	@Validation
	public static ChecksTM validateIsPage (WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de Reembolsos",
			PageReembolsos.isPage(driver), State.Defect);
		checks.add(
			"Aparecen los inputs de BANCO, TITULAR e IBAN",
			(PageReembolsos.existsInputBanco(driver) && PageReembolsos.existsInputTitular(driver) && PageReembolsos.existsInputIBAN(driver)), 
			State.Warn);
		return checks;
	}
}
