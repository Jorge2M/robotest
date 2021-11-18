package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageInfoNewMisComprasMovil;
import org.openqa.selenium.WebDriver;

public class PageInfoNewMisComprasMovilStpV {

	@Validation(
		description="1) Aparece la página \"New!\" informativa a nivel de \"Mis Compras\"",
		level=State.Defect)
	public static boolean validateIsPage(WebDriver driver) {
		return (PageInfoNewMisComprasMovil.isPage(driver));
	}

	@Step(
		description = "Seleccionar el botón \"Ver mis compras\"",
		expected = "Aparece la página de \"Mis Compras\"")
	public static void clickButtonToMisComprasAndNoValidate(WebDriver driver) {
		PageInfoNewMisComprasMovil.clickButtonToMisCompras(driver);
	}
}
