package com.mng.robotest.test80.mango.test.stpv.shop.loyalty;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.testmaker.utils.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.loyalty.PageHomeDonateLikes;
import com.mng.robotest.test80.mango.test.pageobject.shop.loyalty.PageHomeDonateLikes.ButtonLikes;

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
	public ChecksResult checkIsPage() {
		ChecksResult checks = ChecksResult.getNew();
		checks.add(
			"Aparece la pagina de <b>Donar Likes</b>",
			pageHomeDonateLikes.checkIsPage(), State.Defect);
		
		for (ButtonLikes buttonLikes : ButtonLikes.values()) {
			checks.add(
				"Aparece el botón para donar " + buttonLikes.getNumLikes() + " Likes",
				pageHomeDonateLikes.isVisible(buttonLikes), State.Defect);
		}
		
		return checks;
	}
	
	@Step (
		description="Seleccionamos el botón para donar #{buttonLikes.getNumLikes()} likes",
		expected="Se donan correctamente los likes")
	public void selectDonateButton(ButtonLikes buttonLikes) throws Exception {
		pageHomeDonateLikes.clickButton(buttonLikes);
		checkAfterDonateLikes(buttonLikes);
	}
	
	@Validation
	public ChecksResult checkAfterDonateLikes(ButtonLikes buttonSelected) {
		ChecksResult checks = ChecksResult.getNew();
		int maxSecondsWait = 5;
		checks.add(
			"Aparece el icono correspondiente a la operación realizada (lo esperamos hasta " + maxSecondsWait + " segundos)",
			pageHomeDonateLikes.isVisibleIconOperationDoneUntil(maxSecondsWait), State.Defect);

		return checks;
	}	
}
