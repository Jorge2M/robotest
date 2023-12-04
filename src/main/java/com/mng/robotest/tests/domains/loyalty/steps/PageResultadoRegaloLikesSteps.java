package com.mng.robotest.tests.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageResultadoRegaloLikes;

public class PageResultadoRegaloLikesSteps extends StepBase {

	private final PageResultadoRegaloLikes pgResultado = new PageResultadoRegaloLikes();
	
	@Validation (
		description="Aparece la página de resultado Ok del envío de Likes " + SECONDS_WAIT)
	public boolean checkIsEnvioLikesOk(int seconds) {
		return pgResultado.isEnvioOk(seconds);
	}
	
}
