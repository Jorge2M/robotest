package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay3Mobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay3Mobil.BodyPageKoCardINIpay3;

public class PageKoCardINIpay3MobilStpV {
	
	@Validation (
		description="Aparece la 3a página de INIpay",
		level=State.Defect)
	public static boolean validateIsPage(WebDriver driver) {
		return (PageKoCardINIpay3Mobil.isPage(driver));
	}
	
	@Step (
		description="Seleccionamos el <b>Next Button</b>",
		expected="Aparece la 4a y última página de INIpay con resultado OK")
	public static void clickNextButton(WebDriver driver) throws Exception {
		click(BodyPageKoCardINIpay3.nextButton.getBy(), driver).waitLoadPage(0).exec();
		PageKoCardINIpay4MobilStpV.validateIsPage(driver);
	}
}
