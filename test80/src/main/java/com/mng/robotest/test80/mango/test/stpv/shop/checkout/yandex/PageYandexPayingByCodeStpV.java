package com.mng.robotest.test80.mango.test.stpv.shop.checkout.yandex;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex.PageYandexPayingByCode;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageYandexPayingByCodeStpV {
    
	@Validation
    public static ChecksResult validateIsPage(String importeTotal, String codPais, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Aparece la página de <b>Paying by code</b>",
			PageYandexPayingByCode.isPage(driver), State.Warn);
	 	validations.add(
			"Aparece el importe de la compra por pantalla: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
	 	validations.add(
			"Aparece un <b>PaymentCode</b>",
			PageYandexPayingByCode.isVisiblePaymentCode(driver), State.Defect);
	 	return validations;
    }
    
	@Step (
		description="Seleccionamos el botón para volver a Mango", 
        expected="Aparece la página Mango de resultado OK del pago")
    public static void clickBackToMango(Channel channel, WebDriver driver) throws Exception {
		PageYandexPayingByCode.clickBackToMango(channel, driver);
    }
}
