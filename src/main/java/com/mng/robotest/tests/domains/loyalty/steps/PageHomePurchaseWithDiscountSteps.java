package com.mng.robotest.tests.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageHomePurchaseWithDiscount;

public class PageHomePurchaseWithDiscountSteps extends StepBase {

	private final PageHomePurchaseWithDiscount pageHomePurchaseWithDiscount = new PageHomePurchaseWithDiscount();
	
	@Validation
	public ChecksTM checkHomePurchaseWithDiscountPageOk() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de <b>Descuento Mango likes you</b>",
			pageHomePurchaseWithDiscount.checkIsPage());
		
		checks.add(
			"Aparece el boton que permite <b>comprar ahora</b>",
			pageHomePurchaseWithDiscount.areVisibleButtonPurchaseNow());
		
		return checks;
	}
}
