package com.mng.robotest.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.test.pageobject.shop.PageRecADomic;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageRecogidaDomicSteps extends StepBase {

	private final PageRecADomic pageRecADomic = new PageRecADomic();
	
	@Validation
	public ChecksTM validaIsPageSinDevoluciones () {
		var checks = ChecksTM.getNew();
		int seconds = 5;
		checks.add(
			"Aparece la página de Recogida a Domicilio " + getLitSecondsWait(seconds),
			pageRecADomic.isPage(seconds));
		
		checks.add(
			"No aparece ningún pedido",
			 pageRecADomic.noHayPedidos(), Info);

		return checks;
	}
	
}
