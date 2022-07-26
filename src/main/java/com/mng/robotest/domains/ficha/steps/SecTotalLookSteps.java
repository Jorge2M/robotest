package com.mng.robotest.domains.ficha.steps;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.ficha.pageobjects.SecTotalLook;

public class SecTotalLookSteps {

	@Validation (
		description="Es visible el bloque de artículos correspondiente al Total Look",
		level=State.Warn)
	public static boolean checkIsVisible(WebDriver driver) {
		return (SecTotalLook.isVisible(driver));
	}
}
