package com.mng.robotest.tests.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.testslegacy.pageobject.shop.PageDevoluciones;
import com.mng.robotest.testslegacy.pageobject.shop.PageDevoluciones.Devolucion;

public class PageDevolucionesSteps extends StepBase {

	private final PageDevoluciones pageDevoluciones = new PageDevoluciones();
	
	@Validation
	public ChecksTM validaIsPage () {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de devoluciones",
			pageDevoluciones.isPage());
		
		checks.add(
			"Aparece la opción de " + Devolucion.EN_TIENDA.getLiteral(),
			Devolucion.EN_TIENDA.isPresentLink(driver));
		
		checks.add(
			"Aparece la opcion de " + Devolucion.EN_DOMICILIO.getLiteral(),
			Devolucion.EN_DOMICILIO.isPresentLink(driver));
		
		checks.add(
			"Aparece la opción de " + Devolucion.PUNTO_CELERITAS.getLiteral(),
			Devolucion.EN_DOMICILIO.isPresentLink(driver));
		
		return checks;
	}

	@Step(
		description = "Pulsar \"Recogida gratuíta a domicilio\" + \"Solicitar Recogida\"",
		expected = "Aparece la tabla devoluciones sin ningún pedido")
	public void solicitarRegogidaGratuitaADomicilio() {
		Devolucion.EN_DOMICILIO.click(driver);
		Devolucion.EN_DOMICILIO.waitForInState(true, 2, driver);
		pageDevoluciones.clickSolicitarRecogida();
		new PageRecogidaDomicSteps().validaIsPageSinDevoluciones();
		checksDefault();
	}
}