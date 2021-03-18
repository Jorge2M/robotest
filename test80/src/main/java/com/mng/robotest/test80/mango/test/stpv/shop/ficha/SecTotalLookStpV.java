package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecTotalLook;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;

public class SecTotalLookStpV {

	@Validation (
		description="Es visible el bloque de artículos correspondiente al Total Look",
		level=State.Warn)
	public static boolean checkIsVisible(WebDriver driver) {
		return (SecTotalLook.isVisible(driver));
	}
}
