package com.mng.robotest.test.steps.shop.checkout.yandex;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.yandex.PageYandexMoneyResult;

public class PageYandexMoneyResultSteps {
	
	@Validation
	public static ChecksTM validateIsResultOk(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Aparece la página de resultado de Yandex Money",
			PageYandexMoneyResult.isPage(driver), State.Defect);
	 	validations.add(
			"Aparece un mensaje de transferencia con éxito",
			PageYandexMoneyResult.isVisibleMsgTransferOk(driver), State.Defect);
		return validations;   
	}
}
