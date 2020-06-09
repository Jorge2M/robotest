package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay2Mobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay2Mobil.BodyPageKoCardINIpay2;

public class PageKoCardINIpay2MobilStpV {
	
	@Validation (
		description="Aparece la 2a página de INIpay (con el input del email)",
		level=State.Defect)
	public static boolean validateIsPage(WebDriver driver) {
	    return (PageKoCardINIpay2Mobil.isPage(driver));
	}

	@Step (
		description="Seleccionamos el botón para Continuar",
		expected="Aparece la 3a y última página de INIpay")
	public static void confirmMainPaymentCorea(WebDriver driver) {
		click(BodyPageKoCardINIpay2.nextButton.getBy(), driver)
			.waitLoadPage(2).exec();
		PageKoCardINIpay3MobilStpV.validateIsPage(driver);
	}
}
