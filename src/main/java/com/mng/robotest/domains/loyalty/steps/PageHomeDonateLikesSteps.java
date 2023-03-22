package com.mng.robotest.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.loyalty.pageobjects.PageHomeDonateLikes;
import com.mng.robotest.domains.loyalty.pageobjects.PageHomeDonateLikes.ButtonLikes;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;

import java.util.List;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageHomeDonateLikesSteps extends StepBase {
	
	private final PageHomeDonateLikes pageHomeDonateLikes = new PageHomeDonateLikes();
	
	@Validation
	public ChecksTM checkIsPage(int seconds, List<ButtonLikes> listButtons) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la pagina de <b>Donar Likes</b>",
			pageHomeDonateLikes.checkIsPage(0), Defect);

		checks.add(
			"Aparece alguno de los botones para donar " +  listButtons + " Likes (esperamos hasta " + seconds + " segundos)",
			pageHomeDonateLikes.isVisibleAny(listButtons, seconds), Defect);
		
		return checks;
	}
	
	public int selectDonateButton(List<ButtonLikes> buttonLikesList) {
		for (int i=0; i<buttonLikesList.size(); i++) {
			var buttonLikes = buttonLikesList.get(i);
			if (pageHomeDonateLikes.isVisible(buttonLikes, 0)) {
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
			pageHomeDonateLikes.isVisibleIconOperationDoneUntil(seconds), Defect);

		return checks;
	}	
}
