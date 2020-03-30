package com.mng.robotest.test80.mango.test.stpv.shop.checkout.giropay;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.giropay.PageGiropayInputBank;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;

public class PageGiropayInputBankStpV {

	@Validation(
		description="Estamos en la página de introducción del banco",
		level=State.Warn)
	public static boolean checkIsPage(WebDriver driver) {
		return PageGiropayInputBank.checkIsPage(driver);
	}
	
	@Step (
		description="Introducimos el banco \"#{bankToInput}\" y pulsamos el botón de confirmación", 
		expected="Aparece la página Mango de resultado del pago")
	public static void inputBankAndConfirm(String bankToInput, Channel channel, WebDriver driver) {
		PageGiropayInputBank.inputBank(bankToInput, driver);
		PageGiropayInputBank.confirm(driver);
	}
}
