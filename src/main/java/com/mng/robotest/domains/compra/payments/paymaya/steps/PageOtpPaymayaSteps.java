package com.mng.robotest.domains.compra.payments.paymaya.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.compra.payments.paymaya.pageobjects.PageOtpPaymaya;


public class PageOtpPaymayaSteps {

	private final PageOtpPaymaya pageOtpPaymaya = new PageOtpPaymaya();
	
	@Validation (
		description="Aparece la página de introducción del OTP de Paymaya",
		level=State.Warn)
	public boolean checkPage() {
		return pageOtpPaymaya.isPage();
	}
	
	@Step(
		description="Introducimos el otp (<b>#{otp}</b>) y pulsamos \"Proceed\"",
		expected="Aparece la página de resultado de paymaya")
	public PageResultPaymayaSteps proceed(String otp) {
		pageOtpPaymaya.proceed(otp);
		var pageResultPaymayaSteps = new PageResultPaymayaSteps();
		pageResultPaymayaSteps.checkPage();
		return pageResultPaymayaSteps;
	}
}
