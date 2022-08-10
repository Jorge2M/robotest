package com.mng.robotest.test.steps.shop.checkout.paymaya;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.checkout.paymaya.PageIdentPaymaya;


public class PageIdentPaymayaSteps extends PageBase {

	private final PageIdentPaymaya pageIdentPaymaya = new PageIdentPaymaya();
	
	@Validation (
		description="Aparece la página de identificación en PayMaya",
		level=State.Warn)
	public boolean checkPage() {
		return pageIdentPaymaya.isPage();
	}
	
	@Step(
		description=
			"Introducimos las credenciales del usuario PayMaya (<b>#{user}/#{password}</b>) y pulsamos \"Entrar\"",
		expected=
			"Aparece la página de introducción del otp")
	public PageOtpPaymayaSteps login(String user, String password) throws Exception {
		pageIdentPaymaya.login(user, password);
		PageOtpPaymayaSteps pageOtpPaymayaSteps = new PageOtpPaymayaSteps();
		pageOtpPaymayaSteps.checkPage();
		return pageOtpPaymayaSteps;
	}
}
