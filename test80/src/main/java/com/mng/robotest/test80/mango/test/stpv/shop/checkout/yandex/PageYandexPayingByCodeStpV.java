package com.mng.robotest.test80.mango.test.stpv.shop.checkout.yandex;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex.PageYandexPayingByCode;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageYandexPayingByCodeStpV {
    
	@Validation
    public static ListResultValidation validateIsPage(String importeTotal, String codPais, WebDriver driver) {
    	ListResultValidation validations = ListResultValidation.getNew();
	 	validations.add(
			"Aparece la página de <b>Paying by code</b><br>",
			PageYandexPayingByCode.isPage(driver), State.Warn);
	 	validations.add(
			"Aparece el importe de la compra por pantalla: " + importeTotal + "<br>",
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
