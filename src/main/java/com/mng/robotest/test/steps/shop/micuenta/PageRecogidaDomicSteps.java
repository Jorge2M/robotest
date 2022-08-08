package com.mng.robotest.test.steps.shop.micuenta;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.PageRecADomic;


public class PageRecogidaDomicSteps {

	private final PageRecADomic pageRecADomic = new PageRecADomic();
	
	@Validation
	public ChecksTM vaidaIsPageSinDevoluciones () {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de Recogida a Domicilio",
			pageRecADomic.isPage(), State.Defect);
		checks.add(
			"Aparece la tabla de devoluciones",
			pageRecADomic.isTableDevoluciones(), State.Defect);
		checks.add(
			"No aparece ningún pedido",
			 !pageRecADomic.hayPedidos(), State.Info);

		return checks;
	}
}
