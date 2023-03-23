package com.mng.robotest.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.test.pageobject.shop.PageMispedidos;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PagePedidosSteps extends StepBase {

	private final PageMispedidos pageMispedidos = new PageMispedidos();
	
	@Validation
	public ChecksTM validaIsPageSinPedidos (String usrRegistrado) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de \"Mis Pedidos\"",
			pageMispedidos.isPage(), Defect);
		
		checks.add(
			"La página contiene " + usrRegistrado ,
			pageMispedidos.elementContainsText(usrRegistrado), Warn);
		
		checks.add(
			"La lista de pedidos está vacía",
			pageMispedidos.listaPedidosVacia(), Warn);
		
		return checks;
	}
}
