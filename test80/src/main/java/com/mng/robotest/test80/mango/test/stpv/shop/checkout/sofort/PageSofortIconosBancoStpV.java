package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sofort;

import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.otras.Channel;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort.PageSofort1rst;

/**
 * Page1: la página inicial de Sofort (la posterior a la selección del botón "Confirmar Pago")
 * @author jorge.munoz
 *
 */
public class PageSofortIconosBancoStpV {
	
	@Validation (
		description="Aparece la 1a página de Sofort (la esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Warn)
	public static boolean validateIsPageUntil(int maxSecondsWait, Channel channel, WebDriver driver) {
		return (PageSofort1rst.isPageVisibleUntil(maxSecondsWait, channel, driver));
	}
	
	@Step (
		description="Seleccionar el link hacia la siguiente página de Sofort", 
        expected="Aparece la página de selección del Banco")
    public static void clickIconoSofort(Channel channel, WebDriver driver) throws Exception { 
		PageSofort1rst.clickGoToSofort(driver, channel);

        //Validaciones
		int maxSecondsWait = 3;
        PageSofort2onStpV.validaIsPageUntil(maxSecondsWait, driver);
    }
}
