package com.mng.robotest.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.loyalty.pageobjects.PageHomeDonateLikes;
import com.mng.robotest.domains.loyalty.pageobjects.PageHomeDonateLikes.ButtonLikes;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;


public class PageHomeDonateLikesSteps extends StepBase {
	
	private final PageHomeDonateLikes pageHomeDonateLikes = new PageHomeDonateLikes();
	
	@Validation
	public ChecksTM checkIsPage(int seconds) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la pagina de <b>Donar Likes</b>",
			pageHomeDonateLikes.checkIsPage(0), State.Defect);
		
		checks.add(
			"Aparece el botón para donar " + ButtonLikes.BUTTON_100_LIKES.getNumLikes() + " Likes (esperamos hasta " + seconds + " segundos)",
			pageHomeDonateLikes.isVisible(ButtonLikes.BUTTON_100_LIKES, seconds), State.Defect);
		
		return checks;
	}
	
	@Step (
		description="Seleccionamos el botón para donar #{buttonLikes.getNumLikes()} likes",
		expected="Se donan correctamente los likes")
	public void selectDonateButton(ButtonLikes buttonLikes) {
		pageHomeDonateLikes.clickButton(buttonLikes);
		checkAfterDonateLikes(buttonLikes);
		GenericChecks.checkDefault();
	}
	
	@Validation
	public ChecksTM checkAfterDonateLikes(ButtonLikes buttonSelected) {
		var checks = ChecksTM.getNew();
		int seconds = 5;
		checks.add(
			"Aparece el icono correspondiente a la operación realizada (lo esperamos hasta " + seconds + " segundos)",
			pageHomeDonateLikes.isVisibleIconOperationDoneUntil(seconds), State.Defect);

		return checks;
	}	
}
