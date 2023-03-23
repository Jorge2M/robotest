package com.mng.robotest.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.loyalty.pageobjects.PageHomePurchaseWithDiscount;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageHomePurchaseWithDiscountSteps extends StepBase {

	private final PageHomePurchaseWithDiscount pageHomePurchaseWithDiscount = new PageHomePurchaseWithDiscount();
	
	@Validation
	public ChecksTM checkHomePurchaseWithDiscountPageOk() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de <b>Descuento Mango likes you</b>",
			pageHomePurchaseWithDiscount.checkIsPage(), Defect);
		
		checks.add(
			"Aparece el boton que permite <b>comprar ahora</b>",
			pageHomePurchaseWithDiscount.areVisibleButtonPurchaseNow(), Defect);
		
		return checks;
	}
}
