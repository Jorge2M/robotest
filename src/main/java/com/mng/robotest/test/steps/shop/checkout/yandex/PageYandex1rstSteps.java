package com.mng.robotest.test.steps.shop.checkout.yandex;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.yandex.PageYandex1rst;
import com.mng.robotest.test.pageobject.shop.checkout.yandex.PageYandexPayingByCode;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageYandex1rstSteps {
	
	@Validation
	public static ChecksTM validateIsPage(String emailUsr, String importeTotal, String codPais, WebDriver driver) {
		//Esta validación debería hacerse en un punto posterior, una vez se ha intentado enviar el input que es cuando se genera el botón retry.
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la página inicial de Yandex",
			PageYandex1rst.isPage(driver), State.Warn);
	 	checks.add(
			"Figura preinformado el email del usuario: " + emailUsr,
			PageYandex1rst.isValueEmail(emailUsr, driver), State.Warn);
	 	checks.add(
			"Aparece el importe de la compra por pantalla: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
		return checks;
	}
	
	@Step (
		description="Introducimos el teléfono <b>#{telefonoRuso}</b> y seleccionamos el botón <b>\"Continuar\"</b>", 
		expected="Aparece la página de confirmación del pago")
	public static String inputTlfnAndclickContinuar(String telefonoRuso, String importeTotal, String codPais, WebDriver driver) {
		PageYandex1rst.inputTelefono(telefonoRuso, driver);
		PageYandex1rst.clickContinue(driver);
		if (!PageYandex1rst.retryButtonExists(driver)) {
			PageYandexPayingByCodeSteps.validateIsPage(importeTotal, codPais, driver);
			return (PageYandexPayingByCode.getPaymentCode(driver));
		} else {
			return (retry(importeTotal, codPais, driver));
		}
	}

	public static boolean hasFailed(WebDriver driver) {
		return PageYandex1rst.retryButtonExists(driver);
	}

	public static String retry(String importeTotal, String codPais, WebDriver driver) {
		PageYandex1rst.clickOnRetry(driver);
		PageYandex1rst.clickContinue(driver);
		PageYandexPayingByCodeSteps.validateIsPage(importeTotal, codPais, driver);
		String paymentCode = PageYandexPayingByCode.getPaymentCode(driver);
		return paymentCode;
	}
}
