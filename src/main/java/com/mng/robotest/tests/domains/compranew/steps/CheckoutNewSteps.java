package com.mng.robotest.tests.domains.compranew.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compranew.pageobjects.PageCheckoutIdent;
import com.mng.robotest.tests.domains.compranew.pageobjects.PageCheckoutPayment;

public class CheckoutNewSteps extends StepBase {

	PageCheckoutIdent pIdentCheckout = new PageCheckoutIdent();
	PageCheckoutPayment pCheckout = new PageCheckoutPayment();
	
	@Validation (description="Aparece página de identificación-checkout " + SECONDS_WAIT)
	public boolean isPageIdentCheckout(int seconds) {
		return pIdentCheckout.isPage(seconds);
	}	
	
	@Step (
		description="Loginarse utilizando <b>#{mail} / #{password}</b>",
		expected="Aparece la página de Checkout")
	public void login(String mail, String password) {
		pIdentCheckout.login(mail, password);
		isPageCheckout(5); 
	}	
	
	@Validation (description="Aparece página de checkout " + SECONDS_WAIT)
	public boolean isPageCheckout(int seconds) {
		return pCheckout.isPage(seconds);
	}	
	
	
	
//	createAccount


		
	
	
}
