package com.mng.robotest.tests.domains.loyalty.steps;

import java.util.Arrays;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageHomeDonateLikes;

public class PageHomeDonateLikesSteps extends StepBase {
	
	private final PageHomeDonateLikes pgHomeDonateLikes = PageHomeDonateLikes.make(dataTest.getPais());
	
	@Validation
	public ChecksTM checkIsPage(int seconds, Integer... listNumLikes) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la pagina de <b>Donar Likes</b>",
			pgHomeDonateLikes.isPage(0));

		checks.add(
			"Aparece alguno de los botones para donar " +  Arrays.toString(listNumLikes) + " Likes " + getLitSecondsWait(seconds),
			pgHomeDonateLikes.isVisibleAnyButton(seconds, listNumLikes));
		
		return checks;
	}
	
	public int selectDonateButton(Integer... numLikes) {
		for (int i=0; i<numLikes.length; i++) {
			var buttonLikes = numLikes[i];
			if (pgHomeDonateLikes.isVisibleButton(buttonLikes, 0)) {
				selectDonateButton(buttonLikes);
				return buttonLikes;
			}
		}
		return -1;
	}
	
	@Step (
		description="Seleccionamos un botón para donar #{numLikes} likes",
		expected="Se donan correctamente los likes")
	public void selectDonateButton(int numLikes) {
		pgHomeDonateLikes.clickButton(numLikes);
		checkAfterDonateLikes();
		checksDefault();
	}
	
	@Validation
	public ChecksTM checkAfterDonateLikes() {
		var checks = ChecksTM.getNew();
		int seconds = 5;
		checks.add(
			"Aparece el icono correspondiente a la operación realizada " + getLitSecondsWait(seconds),
			pgHomeDonateLikes.isVisibleIconOperationDoneUntil(seconds));

		return checks;
	}	
}
