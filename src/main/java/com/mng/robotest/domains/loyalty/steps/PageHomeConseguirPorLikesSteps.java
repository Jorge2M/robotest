package com.mng.robotest.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.loyalty.pageobjects.PageHomeConseguirPorLikes;


public class PageHomeConseguirPorLikesSteps {

	private final PageHomeConseguirPorLikes pageHomeConseguirPorLikes = new PageHomeConseguirPorLikes();
	
	@Validation (
		description="Aparece el botón de \"Conseguir por Likes\" (esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkIsPage(int maxSeconds) {
		return pageHomeConseguirPorLikes.isPage(maxSeconds);
	}
	
	@Step (
		description="Seleccionar el botón de \"Conseguir por Likes\"",
		expected="Aparece el icono de operación Ok")
	public void selectConseguirButton() {
		pageHomeConseguirPorLikes.selectConseguirButton();
		checkAfterConseguirButton();
	}
	
	@Validation
	public ChecksTM checkAfterConseguirButton() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 5;
		checks.add(
			"Aparece el icono correspondiente a la operación realizada (lo esperamos hasta " + maxSeconds + " segundos)",
			pageHomeConseguirPorLikes.isVisibleIconOperationDoneUntil(maxSeconds), State.Defect);

		return checks;
	}	
}
