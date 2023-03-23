package com.mng.robotest.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.ficha.pageobjects.SecFitFinder;

import static com.github.jorge2m.testmaker.conf.State.*;

public class SecFitFinderSteps extends StepBase {
	
	private final SecFitFinder secFitFinder = new SecFitFinder();
	
	@Validation
	public ChecksTM validateIsOkAndClose() {
		var checks = ChecksTM.getNew();
		int seconds = 2;
	  	checks.add(
			"Es visible el Wrapper con la guía de tallas (lo esperamos hasta " + seconds + " seconds)",
			secFitFinder.isVisibleUntil(seconds), Defect);
	  	checks.add(
			"Es visible el input para la introducción de la altura",
			secFitFinder.isVisibleInputAltura(), Warn);
	  	checks.add(
			"Es visible el input para la introducción del peso",
			secFitFinder.isVisibleInputPeso(), Warn);

		secFitFinder.clickAspaForCloseAndWait();
		return checks;
	}
}
