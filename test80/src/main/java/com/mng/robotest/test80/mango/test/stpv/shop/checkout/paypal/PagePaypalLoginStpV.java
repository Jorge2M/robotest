package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalLogin;

public class PagePaypalLoginStpV {

	@Validation (
		description="Aparece la página de login (la esperamos hasta un máximo de #{maxSecondsWait} segundos)",
		level=State.Defect)
    public static boolean validateIsPageUntil(int maxSecondsWait, WebDriver driver) {
        return (PagePaypalLogin.isPageUntil(maxSecondsWait, driver));
    }
    
	@Step (
		description="Introducimos las credenciales (#{userMail} - #{password}) y pulsamos el botón \"Iniciar sesión\"", 
        expected="Aparece la página de inicio de sesión en Paypal")
    public static void loginPaypal(String userMail, String password, WebDriver driver) throws Exception {  
        String paginaPadre = driver.getWindowHandle();                                                  
        PagePaypalLogin.inputUserAndPassword(userMail, password, driver);
        PagePaypalLogin.clickIniciarSesion(driver);
        driver.switchTo().window(paginaPadre); //Salimos del iframe
        
        //Validaciones
        int maxSecondsWait = 20;
        PagePaypalSelectPagoStpV.validateIsPageUntil(maxSecondsWait, driver);
    }
}
