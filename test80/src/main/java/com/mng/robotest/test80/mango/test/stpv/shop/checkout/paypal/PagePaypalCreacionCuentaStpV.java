package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.PagePaypalCreacionCuenta;

public class PagePaypalCreacionCuentaStpV {
    
	@Step (
		description="Seleccionamos el botón <b>Iniciar Sesión</b>", 
         expected="Aparece la página de login")
    public static void clickButtonIniciarSesion(WebDriver driver) throws Exception {       
        PagePaypalCreacionCuenta.clickButtonIniciarSesion(driver);
        
        //Validaciones
        int maxSecondsWait = 10;
        PagePaypalLoginStpV.validateIsPageUntil(maxSecondsWait, driver);
    }
}
