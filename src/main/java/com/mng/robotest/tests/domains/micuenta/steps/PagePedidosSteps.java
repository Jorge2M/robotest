package com.mng.robotest.tests.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.testslegacy.pageobject.shop.PageMispedidos;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PagePedidosSteps extends StepBase {

	private final PageMispedidos pageMispedidos = new PageMispedidos();
	
	@Validation
	public ChecksTM validaIsPageSinPedidos (String usrRegistrado) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de \"Mis Pedidos\"",
			pageMispedidos.isPage());
		
		checks.add(
			"La página contiene " + usrRegistrado ,
			pageMispedidos.elementContainsText(usrRegistrado), WARN);
		
		checks.add(
			"La lista de pedidos está vacía",
			pageMispedidos.listaPedidosVacia(), WARN);
		
		return checks;
	}
}
