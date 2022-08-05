package com.mng.robotest.test.steps.shop.checkout.sofort;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test.pageobject.shop.checkout.sofort.PageSofort1rst;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;

/**
 * Page1: la página inicial de Sofort (la posterior a la selección del botón "Confirmar Pago")
 * @author jorge.munoz
 *
 */
public class PageSofortIconosBancoSteps {
	
	private final PageSofort1rst pageSofort1rst;
	private final WebDriver driver;
	
	public PageSofortIconosBancoSteps(Channel channel, WebDriver driver) {
		this.pageSofort1rst = new PageSofort1rst(channel, driver);
		this.driver = driver;
	}
	
	@Validation (
		description="Aparece la 1a página de Sofort (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	public boolean validateIsPageUntil(int maxSeconds) {
		return (pageSofort1rst.isPageVisibleUntil(maxSeconds));
	}
	
	@Step (
		description="Seleccionar el link hacia la siguiente página de Sofort", 
		expected="Aparece la página de selección del Banco")
	public void clickIconoSofort() { 
		pageSofort1rst.clickGoToSofort();
		
		PageSofort2onSteps pageSofort2onSteps = new PageSofort2onSteps(driver); 
		pageSofort2onSteps.validaIsPageUntil(3);
	}
}
