package com.mng.robotest.test80.mango.test.stpv.shop.loyalty;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.shop.loyalty.PageHomePurchaseWithDiscount;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

public class PageHomePurchaseWithDiscountStpV {

	private final PageHomePurchaseWithDiscount pageHomePurchaseWithDiscount;
	
	private PageHomePurchaseWithDiscountStpV(WebDriver driver) {
		pageHomePurchaseWithDiscount = PageHomePurchaseWithDiscount.getNewInstance(driver);
	}
	public static PageHomePurchaseWithDiscountStpV getNew(WebDriver driver) {
		return new PageHomePurchaseWithDiscountStpV(driver);
	}
	
	@Validation
	public ChecksTM checkHomePurchaseWithDiscountPageOk() {
		ChecksTM validations = ChecksTM.getNew();
		
		validations.add(
			"Aparece la página de <b>Descuento Mango likes you</b>",
			pageHomePurchaseWithDiscount.checkIsPage(), State.Defect);
		validations.add(
			"Aparece el boton que permite <b>comprar ahora</b>",
			pageHomePurchaseWithDiscount.areVisibleButtonPurchaseNow(), State.Defect);
		return validations;
	}
}
