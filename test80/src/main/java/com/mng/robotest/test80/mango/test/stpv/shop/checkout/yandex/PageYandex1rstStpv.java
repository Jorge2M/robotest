package com.mng.robotest.test80.mango.test.stpv.shop.checkout.yandex;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex.PageYandex1rst;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex.PageYandexPayingByCode;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageYandex1rstStpv {
    
	@Validation
    public static ChecksTM validateIsPage(String emailUsr, String importeTotal, String codPais, WebDriver driver) {
        //Esta validación debería hacerse en un punto posterior, una vez se ha intentado enviar el input que es cuando se genera el botón retry.
    	ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Aparece la página inicial de Yandex",
			PageYandex1rst.isPage(driver), State.Warn);
	 	validations.add(
			"Figura preinformado el email del usuario: " + emailUsr,
			PageYandex1rst.isValueEmail(emailUsr, driver), State.Warn);
	 	validations.add(
			"Aparece el importe de la compra por pantalla: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
		return validations;
    }
    
	@Step (
		description="Introducimos el teléfono <b>#{telefonoRuso}</b> y seleccionamos el botón <b>\"Continuar\"</b>", 
        expected="Aparece la página de confirmación del pago")
    public static String inputTlfnAndclickContinuar(String telefonoRuso, String importeTotal, String codPais, WebDriver driver) {
        PageYandex1rst.inputTelefono(telefonoRuso, driver);
        PageYandex1rst.clickContinue(driver);
        if (!PageYandex1rst.retryButtonExists(driver)) {
            PageYandexPayingByCodeStpV.validateIsPage(importeTotal, codPais, driver);
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
        PageYandexPayingByCodeStpV.validateIsPage(importeTotal, codPais, driver);
        String paymentCode = PageYandexPayingByCode.getPaymentCode(driver);
        return paymentCode;
    }
}
