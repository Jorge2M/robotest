package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.testmaker.service.webdriver.wrapper.ElementPageFunctions;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay3Mobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay3Mobil.BodyPageKoCardINIpay3;

public class PageKoCardINIpay3MobilStpV extends ElementPageFunctions {
	
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
		clickElementVisibleAndWaitLoad(BodyPageKoCardINIpay3.nextButton, 0, driver);
	    
	    //Validations
	    PageKoCardINIpay4MobilStpV.validateIsPage(driver);
	}
}
