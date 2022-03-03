package com.mng.robotest.test.stpv.shop.loyalty;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.loyalty.PageHomeDonateLikes;
import com.mng.robotest.test.pageobject.shop.loyalty.PageHomeDonateLikes.ButtonLikes;

public class PageHomeDonateLikesStpV {
	
	final PageHomeDonateLikes pageHomeDonateLikes;
	
	private PageHomeDonateLikesStpV(WebDriver driver) {
		this.pageHomeDonateLikes = PageHomeDonateLikes.getNew(driver);
	}
	
	public static PageHomeDonateLikesStpV getNew(WebDriver driver) {
		PageHomeDonateLikesStpV pageHomeDonateLikesStpV = new PageHomeDonateLikesStpV(driver);
		return pageHomeDonateLikesStpV;
	}
	
	@Validation
	public ChecksTM checkIsPage(int maxSeconds) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la pagina de <b>Donar Likes</b> (esperamos hasta " + maxSeconds + " segundos)",
			pageHomeDonateLikes.checkIsPage(0), State.Defect);
		
		checks.add(
			"Aparece el botón para donar " + ButtonLikes.Button100likes.getNumLikes() + " Likes (esperamos hasta " + maxSeconds + " segundos)",
			pageHomeDonateLikes.isVisible(ButtonLikes.Button100likes, maxSeconds), State.Defect);
		
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
