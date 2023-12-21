package com.mng.robotest.tests.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.testslegacy.pageobject.shop.PageRecADomic;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageRecogidaDomicSteps extends StepBase {

	private final PageRecADomic pgRecADomic = new PageRecADomic();
	
	@Validation
	public ChecksTM checkIsPageWithoutReturns() {
		var checks = ChecksTM.getNew();
		int seconds = 5;
		checks.add(
			"Aparece la página de Recogida a Domicilio " + getLitSecondsWait(seconds),
			pgRecADomic.isPage(seconds));
		
		checks.add(
			"No aparece ningún pedido",
			 pgRecADomic.noHayPedidos(), INFO);

		return checks;
	}
	
}
