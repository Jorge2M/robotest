package com.mng.robotest.test.stpv.shop.ficha;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.ficha.SecFitFinder;

public class SecFitFinderStpV {
	
	@Validation
	public static ChecksTM validateIsOkAndClose(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 2;
	  	validations.add(
			"Es visible el Wrapper con la guía de tallas (lo esperamos hasta " + maxSeconds + " seconds)",
			SecFitFinder.isVisibleUntil(maxSeconds, driver), State.Defect);
	  	validations.add(
			"Es visible el input para la introducción de la altura",
			SecFitFinder.isVisibleInputAltura(driver), State.Warn);
	  	validations.add(
			"Es visible el input para la introducción del peso",
			SecFitFinder.isVisibleInputPeso(driver), State.Warn);

		SecFitFinder.clickAspaForCloseAndWait(driver);
		return validations;
	}
}
