package com.mng.robotest.test.stpv.shop.micuenta;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.PageReembolsos;

import org.openqa.selenium.WebDriver;


public class PageReembolsosStpV {

	@Validation
	public static ChecksTM validateIsPage (WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Aparece la página de Reembolsos",
			PageReembolsos.isPage(driver), State.Defect);
		validations.add(
			"Aparecen los inputs de BANCO, TITULAR e IBAN",
			(PageReembolsos.existsInputBanco(driver) && PageReembolsos.existsInputTitular(driver) && PageReembolsos.existsInputIBAN(driver)), 
			State.Warn);
		return validations;
	}
}
