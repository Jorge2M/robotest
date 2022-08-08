package com.mng.robotest.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.ficha.pageobjects.SecFitFinder;
import com.mng.robotest.domains.transversal.StepBase;


public class SecFitFinderSteps extends StepBase {
	
	private final SecFitFinder secFitFinder = new SecFitFinder();
	
	@Validation
	public ChecksTM validateIsOkAndClose() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 2;
	  	checks.add(
			"Es visible el Wrapper con la guía de tallas (lo esperamos hasta " + maxSeconds + " seconds)",
			secFitFinder.isVisibleUntil(maxSeconds), State.Defect);
	  	checks.add(
			"Es visible el input para la introducción de la altura",
			secFitFinder.isVisibleInputAltura(), State.Warn);
	  	checks.add(
			"Es visible el input para la introducción del peso",
			secFitFinder.isVisibleInputPeso(), State.Warn);

		secFitFinder.clickAspaForCloseAndWait();
		return checks;
	}
}
