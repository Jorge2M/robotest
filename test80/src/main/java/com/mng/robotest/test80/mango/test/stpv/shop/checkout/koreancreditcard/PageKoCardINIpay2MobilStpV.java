package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.service.webdriver.wrapper.ElementPageFunctions;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay2Mobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay2Mobil.BodyPageKoCardINIpay2;

public class PageKoCardINIpay2MobilStpV extends ElementPageFunctions {
	
	@Validation (
		description="Aparece la 2a página de INIpay (con el input del email)",
		level=State.Defect)
	public static boolean validateIsPage(WebDriver driver) {
	    return (PageKoCardINIpay2Mobil.isPage(driver));
	}

	@Step (
		description="Seleccionamos el botón para Continuar",
	    expected="Aparece la 3a y última página de INIpay")
	public static void confirmMainPaymentCorea(WebDriver driver) throws Exception {
	    clickAndWait(BodyPageKoCardINIpay2.nextButton, 2, driver);
	
	    //Validation
	    PageKoCardINIpay3MobilStpV.validateIsPage(driver);
	}
}
