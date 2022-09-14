package com.mng.robotest.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.loyalty.pageobjects.PageHomeConseguirPorLikes;
import com.mng.robotest.domains.transversal.StepBase;


public class PageHomeConseguirPorLikesSteps extends StepBase {

	private final PageHomeConseguirPorLikes pageHomeConseguirPorLikes = new PageHomeConseguirPorLikes();
	
	@Validation (
		description="Aparece el botón de \"Conseguir por Likes\" (esperamos hasta #{seconds} segundos)",
		level=State.Defect)
	public boolean checkIsPage(int seconds) {
		return pageHomeConseguirPorLikes.isPage(seconds);
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
		int seconds = 5;
		checks.add(
			"Aparece el icono correspondiente a la operación realizada (lo esperamos hasta " + seconds + " segundos)",
			pageHomeConseguirPorLikes.isVisibleIconOperationDoneUntil(seconds), State.Defect);

		return checks;
	}	
}
