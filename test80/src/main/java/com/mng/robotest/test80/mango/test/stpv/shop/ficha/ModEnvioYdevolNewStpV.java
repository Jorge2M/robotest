package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.Validation;
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
