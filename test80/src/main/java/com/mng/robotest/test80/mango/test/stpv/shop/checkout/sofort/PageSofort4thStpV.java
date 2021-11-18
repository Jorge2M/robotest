package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sofort;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort.PageSofort4th;


public class PageSofort4thStpV {
	
	private final PageSofort4th pageSofort4th;
	
	public PageSofort4thStpV(WebDriver driver) {
		this.pageSofort4th = new PageSofort4th(driver);
	}
	
	@Validation (
		description="Aparece la página de introducción del Usuario/Password de \"SOFORT\"",
		level=State.Warn)
	public boolean validaIsPage() { 
		return (pageSofort4th.isPage());
	}
	
	@Step (
		description="Introducir el usuario/password de DEMO: #{usrSofort} / #{passSofort}", 
		expected="Aparece la página de selección de cuenta")
	public void inputCredencialesUsr(String usrSofort, String passSofort) {
		pageSofort4th.inputUserPass(usrSofort, passSofort);
		pageSofort4th.clickSubmitButton();
		validateAppearsCtaForm();
	}
	
	@Validation (
		description="Aparece un formulario para la selección de la cuenta",
		level=State.Warn)
	public boolean validateAppearsCtaForm() {
		return pageSofort4th.isVisibleFormSelCta();
	}
	
	@Step (
		description="Seleccionamos la 1a cuenta y pulsamos aceptar", 
		expected="Aparece la página de confirmación de la transacción")
	public void select1rstCtaAndAccept() { 
		pageSofort4th.selectRadioCta(1);
		pageSofort4th.clickSubmitButton();
		validateAppearsInputTAN();
	}
	
	@Validation (
		description="Aparece un campo para la introducción del TAN",
		level=State.Warn)
	public boolean validateAppearsInputTAN() {
		return (pageSofort4th.isVisibleInputTAN());
	}

	@Step (
		description="Introducción del TAN: #{TANSofort} y pulsamos aceptar", 
		expected="El pago se realiza correctamente")
	public void inputTANandAccept(String TANSofort) {
		pageSofort4th.inputTAN(TANSofort);
		pageSofort4th.clickSubmitButton();
	}
}