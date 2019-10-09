package com.mng.robotest.test80.mango.test.stpv.shop.checkout.paypal;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.paypal.ModalPreloaderSpinner;

public class ModalPreloaderSppinerStpV {
	public static void validateAppearsAndDisappears(WebDriver driver) {
		validateIsVisible(2, driver);
		validateIsVanished(10, driver);
	}
	
	@Validation (
		description="Aparece el icono del candado de \"Cargando\" (lo esperamos un máximo de #{maxSecondsWait})",
		level=State.Info)
    public static boolean validateIsVisible(int maxSecondsWait, WebDriver driver) {
        return (ModalPreloaderSpinner.isVisibleUntil(maxSecondsWait, driver));
    }
	
	@Validation (
		description="Desaparece el icono del candado de \"Cargando\" (lo esperamos un máximo de #{maxSecondsWait})",
		level=State.Info)
    public static boolean validateIsVanished(int maxSecondsWait, WebDriver driver) {
        return (ModalPreloaderSpinner.isNotVisibleUntil(maxSecondsWait, driver));
    }
}
