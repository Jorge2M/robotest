package com.mng.robotest.test.steps.shop.checkout.paytrail;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.epayment.PageEpaymentIdent;
import com.mng.robotest.test.pageobject.shop.checkout.paytrail.PagePaytrailEpayment;

public class PagePaytrailEpaymentSteps {
	
	private final WebDriver driver;
	private final PageEpaymentIdent pageEpaymentIdent;
	private final PagePaytrailEpayment pagePaytrailEpayment;
	
	public PagePaytrailEpaymentSteps(WebDriver driver) {
		this.driver = driver;
		this.pageEpaymentIdent = new PageEpaymentIdent(driver);
		this.pagePaytrailEpayment = new PagePaytrailEpayment(driver);
	}
	
	@Validation
	public ChecksTM validateIsPage() { 
		ChecksTM checks = ChecksTM.getNew();
	   	checks.add(
			"Aparece la página inicial de E-Payment",
			pageEpaymentIdent.isPage(), State.Warn);
	   	checks.add(
			"Figuran el input correspondientes al \"User ID\"",
			pageEpaymentIdent.isPresentInputUserTypePassword(), State.Warn);	   	
	   	return checks;
	}
	
	@Step (
		description="Click en el botón \"OK\" del apartado \"Code card\"", 
		expected="Aparece la página de introducción del <b>ID de confirmación</b>")
	public void clickCodeCardOK(String importeTotal, String codPais) {
		pagePaytrailEpayment.clickOkFromCodeCard();
		PagePaytrailIdConfirmSteps.validateIsPage(importeTotal, codPais, driver);
	}
}
