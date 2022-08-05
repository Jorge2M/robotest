package com.mng.robotest.test.steps.shop.checkout.yandex;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test.pageobject.shop.checkout.yandex.PageYandexPayingByCode;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageYandexPayingByCodeSteps {
	
	@Validation
	public static ChecksTM validateIsPage(String importeTotal, String codPais, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
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
