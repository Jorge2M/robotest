package com.mng.robotest.tests.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageHomeDonateLikes;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageHomeDonateLikes.ButtonLikes;

public class PageHomeDonateLikesSteps extends StepBase {
	
	private final PageHomeDonateLikes pgHomeDonateLikes = new PageHomeDonateLikes();
	
	@Validation
	public ChecksTM checkIsPage(int seconds, ButtonLikes... listButtons) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la pagina de <b>Donar Likes</b>",
			pgHomeDonateLikes.checkIsPage(0));

		checks.add(
			"Aparece alguno de los botones para donar " +  listButtons + " Likes " + getLitSecondsWait(seconds),
			pgHomeDonateLikes.isVisibleAny(seconds, listButtons));
		
		return checks;
	}
	
	public int selectDonateButton(ButtonLikes... buttonLikesList) {
		for (int i=0; i<buttonLikesList.length; i++) {
			var buttonLikes = buttonLikesList[i];
			if (pgHomeDonateLikes.isVisible(buttonLikes, 0)) {
				selectDonateButton(buttonLikes);
				return buttonLikes.getNumLikes();
			}
		}
		return -1;
	}
	
	@Step (
		description="Seleccionamos un botón para donar #{buttonLikes.getNumLikes()} likes",
		expected="Se donan correctamente los likes")
	public void selectDonateButton(ButtonLikes buttonLikes) {
		pgHomeDonateLikes.clickButton(buttonLikes);
		checkAfterDonateLikes(buttonLikes);
		checksDefault();
	}
	
	@Validation
	public ChecksTM checkAfterDonateLikes(ButtonLikes buttonSelected) {
		var checks = ChecksTM.getNew();
		int seconds = 5;
		checks.add(
			"Aparece el icono correspondiente a la operación realizada " + getLitSecondsWait(seconds),
			pgHomeDonateLikes.isVisibleIconOperationDoneUntil(seconds));

		return checks;
	}	
}
