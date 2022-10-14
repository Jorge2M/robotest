package com.mng.robotest.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.PageRecADomic;


public class PageRecogidaDomicSteps extends StepBase {

	private final PageRecADomic pageRecADomic = new PageRecADomic();
	
	@Validation
	public ChecksTM vaidaIsPageSinDevoluciones () {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de Recogida a Domicilio",
			pageRecADomic.isPage(), State.Defect);
		
		checks.add(
			"No aparece ningún pedido",
			 pageRecADomic.noHayPedidos(), State.Info);

		return checks;
	}
}
