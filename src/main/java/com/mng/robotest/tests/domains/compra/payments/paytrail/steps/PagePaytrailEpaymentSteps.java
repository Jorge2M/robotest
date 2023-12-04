package com.mng.robotest.tests.domains.compra.payments.paytrail.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.paypal.pageobjects.PageEpaymentIdent;
import com.mng.robotest.tests.domains.compra.payments.paytrail.pageobjects.PagePaytrailEpayment;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PagePaytrailEpaymentSteps extends StepBase {
	
	private final PageEpaymentIdent pageEpaymentIdent = new PageEpaymentIdent();
	private final PagePaytrailEpayment pagePaytrailEpayment = new PagePaytrailEpayment();
	
	@Validation
	public ChecksTM validateIsPage() { 
		var checks = ChecksTM.getNew();
	   	checks.add(
			"Aparece la página inicial de E-Payment",
			pageEpaymentIdent.isPage(), WARN);
	   	
	   	checks.add(
			"Figuran el input correspondientes al \"User ID\"",
			pageEpaymentIdent.isPresentInputUserTypePassword(), WARN);	
	   	
	   	return checks;
	}
	
	@Step (
		description="Click en el botón \"OK\" del apartado \"Code card\"", 
		expected="Aparece la página de introducción del <b>ID de confirmación</b>")
	public void clickCodeCardOK(String importeTotal, String codPais) {
		pagePaytrailEpayment.clickOkFromCodeCard();
		new PagePaytrailIdConfirmSteps().validateIsPage(importeTotal, codPais);
	}
}
