package com.mng.robotest.test80.mango.test.stpv.shop.checkout.sofort;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort.PageSofort4th;

/**
 * Page4: la página de entrada usuario/password
 * @author jorge.munoz
 *
 */
public class PageSofort4thStpV {
    
	@Validation (
		description="Aparece la página de introducción del Usuario/Password de \"SOFORT\"",
		level=State.Warn)
    public static boolean validaIsPage(WebDriver driver) { 
		return (PageSofort4th.isPage(driver));
    }
    
	@Step (
		description="Introducir el usuario/password de DEMO: #{usrSofort} / #{passSofort}", 
        expected="Aparece la página de selección de cuenta")
    public static void inputCredencialesUsr(String usrSofort, String passSofort, WebDriver driver) throws Exception {
        PageSofort4th.inputUserPass(driver, usrSofort, passSofort);
        PageSofort4th.clickSubmitButton(driver);
        validateAppearsCtaForm(driver);
    }
	
	@Validation (
		description="Aparece un formulario para la selección de la cuenta",
		level=State.Warn)
	public static boolean validateAppearsCtaForm(WebDriver driver) {
		return (PageSofort4th.isVisibleFormSelCta(driver));
	}
    
	@Step (
		description="Seleccionamos la 1a cuenta y pulsamos aceptar", 
        expected="Aparece la página de confirmación de la transacción")
    public static void select1rstCtaAndAccept(WebDriver driver) throws Exception { 
        PageSofort4th.selectRadioCta(driver, 1);
        PageSofort4th.clickSubmitButton(driver);
        validateAppearsInputTAN(driver);
    }
	
	@Validation (
		description="Aparece un campo para la introducción del TAN",
		level=State.Warn)
	public static boolean validateAppearsInputTAN(WebDriver driver) {
		return (PageSofort4th.isVisibleInputTAN(driver));
	}
    
	@Step (
		description="Introducción del TAN: #{TANSofort} y pulsamos aceptar", 
        expected="El pago se realiza correctamente")
    public static void inputTANandAccept(String TANSofort, WebDriver driver) throws Exception {
		PageSofort4th.inputTAN(driver, TANSofort);
		PageSofort4th.clickSubmitButton(driver);
    }
}