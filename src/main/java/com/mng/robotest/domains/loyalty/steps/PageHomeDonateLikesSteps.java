package com.mng.robotest.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.loyalty.pageobjects.PageHomeDonateLikes;
import com.mng.robotest.domains.loyalty.pageobjects.PageHomeDonateLikes.ButtonLikes;


public class PageHomeDonateLikesSteps {
	
	private final PageHomeDonateLikes pageHomeDonateLikes = new PageHomeDonateLikes();
	
	@Validation
	public ChecksTM checkIsPage(int maxSeconds) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la pagina de <b>Donar Likes</b>",
			pageHomeDonateLikes.checkIsPage(0), State.Defect);
		
		checks.add(
			"Aparece el botón para donar " + ButtonLikes.BUTTON_100_LIKES.getNumLikes() + " Likes (esperamos hasta " + maxSeconds + " segundos)",
			pageHomeDonateLikes.isVisible(ButtonLikes.BUTTON_100_LIKES, maxSeconds), State.Defect);
		
		return checks;
	}
	
	@Step (
		description="Seleccionamos el botón para donar #{buttonLikes.getNumLikes()} likes",
		expected="Se donan correctamente los likes")
	public void selectDonateButton(ButtonLikes buttonLikes) {
		pageHomeDonateLikes.clickButton(buttonLikes);
		checkAfterDonateLikes(buttonLikes);
	}
	
	@Validation
	public ChecksTM checkAfterDonateLikes(ButtonLikes buttonSelected) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 5;
		checks.add(
			"Aparece el icono correspondiente a la operación realizada (lo esperamos hasta " + maxSeconds + " segundos)",
			pageHomeDonateLikes.isVisibleIconOperationDoneUntil(maxSeconds), State.Defect);

		return checks;
	}	
}
