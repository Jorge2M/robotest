package com.mng.robotest.domains.loyalty.steps;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.loyalty.pageobjects.PageHomePurchaseWithDiscount;

public class PageHomePurchaseWithDiscountSteps {

	private final PageHomePurchaseWithDiscount pageHomePurchaseWithDiscount;
	
	public PageHomePurchaseWithDiscountSteps(WebDriver driver) {
		pageHomePurchaseWithDiscount = new PageHomePurchaseWithDiscount(driver);
	}
	
	@Validation
	public ChecksTM checkHomePurchaseWithDiscountPageOk() {
		ChecksTM checks = ChecksTM.getNew();
		
		checks.add(
			"Aparece la página de <b>Descuento Mango likes you</b>",
			pageHomePurchaseWithDiscount.checkIsPage(), State.Defect);
		checks.add(
			"Aparece el boton que permite <b>comprar ahora</b>",
			pageHomePurchaseWithDiscount.areVisibleButtonPurchaseNow(), State.Defect);
		return checks;
	}
}
