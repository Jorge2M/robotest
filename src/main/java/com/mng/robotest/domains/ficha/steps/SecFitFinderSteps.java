package com.mng.robotest.domains.ficha.steps;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.ficha.pageobjects.SecFitFinder;

public class SecFitFinderSteps {
	
	private final SecFitFinder secFitFinder;
	
	public SecFitFinderSteps(WebDriver driver) {
		secFitFinder = new SecFitFinder(driver);
	}
	
	@Validation
	public ChecksTM validateIsOkAndClose() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 2;
	  	validations.add(
			"Es visible el Wrapper con la guía de tallas (lo esperamos hasta " + maxSeconds + " seconds)",
			secFitFinder.isVisibleUntil(maxSeconds), State.Defect);
	  	validations.add(
			"Es visible el input para la introducción de la altura",
			secFitFinder.isVisibleInputAltura(), State.Warn);
	  	validations.add(
			"Es visible el input para la introducción del peso",
			secFitFinder.isVisibleInputPeso(), State.Warn);

		secFitFinder.clickAspaForCloseAndWait();
		return validations;
	}
}
