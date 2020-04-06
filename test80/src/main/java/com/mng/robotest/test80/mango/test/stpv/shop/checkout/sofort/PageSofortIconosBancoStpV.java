package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sofort;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort.PageSofort1rst;

/**
 * Page1: la página inicial de Sofort (la posterior a la selección del botón "Confirmar Pago")
 * @author jorge.munoz
 *
 */
public class PageSofortIconosBancoStpV {
	
	@Validation (
		description="Aparece la 1a página de Sofort (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	public static boolean validateIsPageUntil(int maxSeconds, Channel channel, WebDriver driver) {
		return (PageSofort1rst.isPageVisibleUntil(maxSeconds, channel, driver));
	}
	
	@Step (
		description="Seleccionar el link hacia la siguiente página de Sofort", 
		expected="Aparece la página de selección del Banco")
	public static void clickIconoSofort(Channel channel, WebDriver driver) { 
		PageSofort1rst.clickGoToSofort(driver, channel);
		int maxSeconds = 3;
		PageSofort2onStpV.validaIsPageUntil(maxSeconds, driver);
	}
}
