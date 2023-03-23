package com.mng.robotest.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.ficha.pageobjects.SecTotalLook;

import static com.github.jorge2m.testmaker.conf.State.*;

public class SecTotalLookSteps extends StepBase {

	private final SecTotalLook secTotalLook = new SecTotalLook();
	
	@Validation (
		description="Es visible el bloque de artículos correspondiente al Total Look",
		level=Warn)
	public boolean checkIsVisible() {
		return (secTotalLook.isVisible());
	}
}
