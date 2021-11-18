package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sofort;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort.PageSofort2on;


public class PageSofort2onStpV {
	
	private final PageSofort2on pageSofort2on;
	private final WebDriver driver;
	
	public PageSofort2onStpV(WebDriver driver) {
		this.pageSofort2on = new PageSofort2on(driver);
		this.driver = driver;
	}
	
	@Validation (
		description="Aparece la página de selección del país/banco (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean validaIsPageUntil(int maxSeconds) {
		return (pageSofort2on.isPageUntil(maxSeconds));
	}
	
	@Step (
		description="Aceptar las cookies",
		expected="Desaparece el modal de aceptación de cookies")
	public void acceptCookies() {
		pageSofort2on.acceptCookies();
	}
	
	@Step (
		description="Seleccionamos el país con código <b>#{paisSofort}</b> y el código de banco <b>#{bankCode}</b> y pulsamos \"Weiter\"",
		expected="Aparece la página de indentificación en SOFORT")
	public void selectPaisYBanco(String paisSofort, String bankCode) {
		pageSofort2on.selectPais(paisSofort);
		pageSofort2on.inputBankcode(bankCode);
		SeleniumUtils.waitForPageLoaded(driver, 5);
		pageSofort2on.clickSubmitButtonPage3();
		
		PageSofort4thStpV pageSofort4thStpV = new PageSofort4thStpV(driver);
		pageSofort4thStpV.validaIsPage();
	}
}
