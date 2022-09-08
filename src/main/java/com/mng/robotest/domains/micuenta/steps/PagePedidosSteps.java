package com.mng.robotest.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.PageMispedidos;


public class PagePedidosSteps extends StepBase {

	PageMispedidos pageMispedidos = new PageMispedidos();
	
	@Validation
	public ChecksTM validaIsPageSinPedidos (String usrRegistrado) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de \"Mis Pedidos\"",
			pageMispedidos.isPage(), State.Defect);
		
		checks.add(
			"La página contiene " + usrRegistrado ,
			pageMispedidos.elementContainsText(usrRegistrado), State.Warn);
		
		checks.add(
			"La lista de pedidos está vacía",
			pageMispedidos.listaPedidosVacia(), State.Warn);
		
		return checks;
	}
}