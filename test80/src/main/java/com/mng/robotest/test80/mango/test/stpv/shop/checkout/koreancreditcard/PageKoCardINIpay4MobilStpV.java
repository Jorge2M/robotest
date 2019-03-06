package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay4Mobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay4Mobil.BodyPageKoCardINIpay4;

public class PageKoCardINIpay4MobilStpV extends ElementPageFunctions {
	
	@Validation (
		description="Está presente el texto de pago OK en Coreano <b>" + PageKoCardINIpay4Mobil.textoPagoConExitoKR + "</b>",
		level=State.Defect)
	public static boolean validateIsPage(WebDriver driver) {
	    return (PageKoCardINIpay4Mobil.isVisibleMessagePaymentOk(driver));
	}

	@Step (
		description="Seleccionar el botón para Confirmar", 
	    expected="Aparece la página de confirmación de KrediKarti")
	public static void clickConfirmarButton(WebDriver driver) throws Exception {     
		clickElementVisibleAndWaitLoad(BodyPageKoCardINIpay4.nextButton, 0, driver);
	}
}