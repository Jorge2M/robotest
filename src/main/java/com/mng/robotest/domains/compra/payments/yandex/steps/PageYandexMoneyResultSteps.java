package com.mng.robotest.domains.compra.payments.yandex.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.compra.payments.yandex.pageobjects.PageYandexMoneyResult;
import com.mng.robotest.domains.transversal.StepBase;

public class PageYandexMoneyResultSteps extends StepBase {
	
	private final PageYandexMoneyResult pageYandexMoneyResult = new PageYandexMoneyResult();
	
	@Validation
	public ChecksTM validateIsResultOk() {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la página de resultado de Yandex Money",
			pageYandexMoneyResult.isPage(), State.Defect);
	 	
	 	checks.add(
			"Aparece un mensaje de transferencia con éxito",
			pageYandexMoneyResult.isVisibleMsgTransferOk(), State.Defect);
	 	
		return checks;   
	}
}
