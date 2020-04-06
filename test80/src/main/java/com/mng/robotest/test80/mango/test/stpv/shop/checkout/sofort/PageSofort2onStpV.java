package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sofort;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort.PageSofort2on;

/**
 * Page2: la página de selección del país
 * @author jorge.munoz
 *
 */
public class PageSofort2onStpV {
    
	@Validation (
		description="Aparece la página de selección del país/banco (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
    public static boolean validaIsPageUntil(int maxSeconds, WebDriver driver) {
		return (PageSofort2on.isPageUntil(maxSeconds, driver));
    }
    
	@Step (
		description="Seleccionamos el país con código <b>#{paisSofort}</b> y el código de banco <b>#{bankCode}</b> y pulsamos \"Weiter\"",
		expected="Aparece la página de indentificación en SOFORT")
    public static void selectPaisYBanco(String paisSofort, String bankCode, WebDriver driver) {
        PageSofort2on.selectPais(driver, paisSofort);
        PageSofort2on.inputBankcode(bankCode, driver);
        SeleniumUtils.waitForPageLoaded(driver, 5);
        PageSofort2on.clickSubmitButtonPage3(driver);
    
        //Validaciones
        PageSofort4thStpV.validaIsPage(driver);
    }
}
