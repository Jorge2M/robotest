package com.mng.robotest.test80.mango.test.stpv.shop.loyalty;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.testmaker.utils.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.loyalty.PageHomeConseguirPor1200Likes;

public class PageHomeConseguirPor1200LikesStpV {

	
	final PageHomeConseguirPor1200Likes pageHomeConseguirPor1200Likes;
	
	private PageHomeConseguirPor1200LikesStpV(WebDriver driver) {
		this.pageHomeConseguirPor1200Likes = PageHomeConseguirPor1200Likes.getNew(driver);
	}
	
	public static PageHomeConseguirPor1200LikesStpV getNew(WebDriver driver) {
		PageHomeConseguirPor1200LikesStpV pageHomeConseguirPor1200LikesStpV = new PageHomeConseguirPor1200LikesStpV(driver);
		return pageHomeConseguirPor1200LikesStpV;
	}
	
	@Validation (
		description="Aparece el botón de \"Conseguir por 1200 Likes\"",
		level=State.Defect)
	public boolean checkIsPage() {
		return pageHomeConseguirPor1200Likes.isPage();
	}
	
	@Step (
		description="Seleccionar el botón de \"Conseguir por 1200 Likes\"",
		expected="Aparece el icono de operación Ok")
	public void selectConseguirButton() throws Exception {
		pageHomeConseguirPor1200Likes.selectConseguirButton();
		checkAfterConseguirButton();
	}
	
	@Validation
	public ChecksResult checkAfterConseguirButton() {
		ChecksResult checks = ChecksResult.getNew();
		int maxSecondsWait = 5;
		checks.add(
			"Aparece el icono correspondiente a la operación realizada (lo esperamos hasta " + maxSecondsWait + " segundos)",
			pageHomeConseguirPor1200Likes.isVisibleIconOperationDoneUntil(maxSecondsWait), State.Defect);

		return checks;
	}	
}
