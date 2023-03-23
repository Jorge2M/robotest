package com.mng.robotest.domains.compra.payments.yandex.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.yandex.pageobjects.PageYandexMoneyResult;

public class PageYandexMoneyResultSteps extends StepBase {
	
	private final PageYandexMoneyResult pageYandexMoneyResult = new PageYandexMoneyResult();
	
	@Validation
	public ChecksTM validateIsResultOk() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la página de resultado de Yandex Money",
			pageYandexMoneyResult.isPage());
	 	
	 	checks.add(
			"Aparece un mensaje de transferencia con éxito",
			pageYandexMoneyResult.isVisibleMsgTransferOk());
	 	
		return checks;   
	}
}
