package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalLogin;

public class PagePaypalLoginStpV {

	@Validation (
		description="Aparece la página de login (la esperamos hasta un máximo de #{maxSeconds} segundos)",
		level=State.Defect)
    public static boolean validateIsPageUntil(int maxSeconds, WebDriver driver) {
        return (PagePaypalLogin.isPageUntil(maxSeconds, driver));
    }
    
	@Step (
		description="Introducimos las credenciales (#{userMail} - #{password}) y pulsamos el botón \"Iniciar sesión\"", 
        expected="Aparece la página de inicio de sesión en Paypal")
    public static void loginPaypal(String userMail, String password, WebDriver driver) {  
        String paginaPadre = driver.getWindowHandle();
        PagePaypalLogin.inputUserAndPassword(userMail, password, driver);
        PagePaypalLogin.clickIniciarSesion(driver);
        driver.switchTo().window(paginaPadre); //Salimos del iframe
        PagePaypalSelectPagoStpV.validateIsPageUntil(20, driver);
    }
}
