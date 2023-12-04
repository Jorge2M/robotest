package com.mng.robotest.tests.domains.compra.payments.paymaya.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.compra.payments.paymaya.pageobjects.PageIdentPaymaya;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageIdentPaymayaSteps extends PageBase {

	private final PageIdentPaymaya pageIdentPaymaya = new PageIdentPaymaya();
	
	@Validation (
		description="Aparece la página de identificación en PayMaya",
		level=WARN)
	public boolean checkPage() {
		return pageIdentPaymaya.isPage();
	}
	
	@Step(
		description=
			"Introducimos las credenciales del usuario PayMaya (<b>#{user}/#{password}</b>) y pulsamos \"Entrar\"",
		expected=
			"Aparece la página de introducción del otp")
	public PageOtpPaymayaSteps login(String user, String password) {
		pageIdentPaymaya.login(user, password);
		var pageOtpPaymayaSteps = new PageOtpPaymayaSteps();
		pageOtpPaymayaSteps.checkPage();
		return pageOtpPaymayaSteps;
	}
}
