package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.ModEnvioYdevolNew;

public class ModEnvioYdevolNewStpV {

	@Validation (
		description="Aparece el modal con los datos a nivel de envío y devolución",
		level=State.Defect)
    public static boolean validateIsVisible(WebDriver driver) {
        int maxSecondsToWait = 1;
        return (ModEnvioYdevolNew.isVisibleUntil(maxSecondsToWait, driver));
    }
    
	@Step (
		description="Seleccionar el aspa para cerrar el modal de \"Envío y devolución\"",
        expected="Desaparece el modal")
    public static void clickAspaForClose(WebDriver driver) throws Exception {
        ModEnvioYdevolNew.clickAspaForClose(driver);      
        checkIsVisibleModalDatosEnvio(driver);
    }
	
	@Validation (
		description="No es visible el modal con los datos a nivel de envío y devolución",
		level=State.Warn)
	private static boolean checkIsVisibleModalDatosEnvio(WebDriver driver) {
       int maxSecondsToWait = 1;
       return (!ModEnvioYdevolNew.isVisibleUntil(maxSecondsToWait, driver));
	}
}
