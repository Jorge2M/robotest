package com.mng.robotest.domains.ficha.steps;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.ficha.pageobjects.SecTotalLook;

public class SecTotalLookSteps {

	private final SecTotalLook secTotalLook;
	
	public SecTotalLookSteps(WebDriver driver) {
		secTotalLook = new SecTotalLook(driver);
	}
	
	@Validation (
		description="Es visible el bloque de artículos correspondiente al Total Look",
		level=State.Warn)
	public boolean checkIsVisible() {
		return (secTotalLook.isVisible());
	}
}
