package com.mng.robotest.test.stpv.shop.checkout.paymaya;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test.pageobject.shop.checkout.paymaya.PageIdentPaymaya;

public class PageIdentPaymayaStpV {

	private final PageIdentPaymaya pageIdentPaymaya;
	private final WebDriver driver;
	
	public PageIdentPaymayaStpV(WebDriver driver) {
		pageIdentPaymaya = new PageIdentPaymaya(driver);
		this.driver = driver;
	}
	
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
	public PageOtpPaymayaStpV login(String user, String password) throws Exception {
		pageIdentPaymaya.login(user, password);
		PageOtpPaymayaStpV pageOtpPaymayaStpV = new PageOtpPaymayaStpV(driver);
		pageOtpPaymayaStpV.checkPage();
		return pageOtpPaymayaStpV;
	}
}
