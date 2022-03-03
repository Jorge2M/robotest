package com.mng.robotest.test.stpv.shop.checkout.paypal;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test.pageobject.shop.checkout.paypal.ModalPreloaderSpinner;

public class ModalPreloaderSppinerStpV {
	public static void validateAppearsAndDisappears(WebDriver driver) {
		validateIsVisible(2, driver);
		validateIsVanished(10, driver);
	}
	
	@Validation (
		description="Aparece el icono del candado de \"Cargando\" (lo esperamos un máximo de #{maxSeconds})",
		level=State.Info,
		avoidEvidences=true)
	public static boolean validateIsVisible(int maxSeconds, WebDriver driver) {
		return (ModalPreloaderSpinner.isVisibleUntil(maxSeconds, driver));
	}
	
	@Validation (
		description="Desaparece el icono del candado de \"Cargando\" (lo esperamos un máximo de #{maxSeconds})",
		level=State.Info,
		avoidEvidences=true)
	public static boolean validateIsVanished(int maxSeconds, WebDriver driver) {
		return (ModalPreloaderSpinner.isNotVisibleUntil(maxSeconds, driver));
	}
}
