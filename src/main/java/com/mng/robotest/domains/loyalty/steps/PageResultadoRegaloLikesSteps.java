package com.mng.robotest.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.loyalty.pageobjects.PageResultadoRegaloLikes;

public class PageResultadoRegaloLikesSteps extends StepBase {

	private final PageResultadoRegaloLikes pageResultado = new PageResultadoRegaloLikes();
	
	@Validation (
		description="Aparece la página de resultado Ok del envío de Likes (la esperamos hasta #{seconds} segundos)")
	public boolean checkIsEnvioLikesOk(int seconds) {
		return pageResultado.isEnvioOk(seconds);
	}
	
}
