package com.mng.robotest.test.stpv.shop.checkout.paymaya;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test.pageobject.shop.checkout.paymaya.PageOtpPaymaya;

public class PageOtpPaymayaStpV {

	private final PageOtpPaymaya pageOtpPaymaya;
	private final WebDriver driver;
	
	public PageOtpPaymayaStpV(WebDriver driver) {
		this.pageOtpPaymaya = new PageOtpPaymaya(driver);
		this.driver = driver;
	}
	
	@Validation (
		description="Aparece la página de introducción del OTP de Paymaya",
		level=State.Warn)
	public boolean checkPage() {
		return pageOtpPaymaya.isPage();
	}
	
	@Step(
		description="Introducimos el otp (<b>#{otp}</b>) y pulsamos \"Proceed\"",
		expected="Aparece la página de resultado de paymaya")
	public PageResultPaymayaStpV proceed(String otp) throws Exception {
		pageOtpPaymaya.proceed(otp);
		PageResultPaymayaStpV pageResultPaymayaStpV = new PageResultPaymayaStpV(driver);
		pageResultPaymayaStpV.checkPage();
		return pageResultPaymayaStpV;
	}
}
