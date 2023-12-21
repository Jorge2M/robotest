package com.mng.robotest.tests.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.testslegacy.pageobject.shop.PageDevoluciones;

import static com.mng.robotest.testslegacy.pageobject.shop.PageDevoluciones.Devolucion.*;

public class PageDevolucionesSteps extends StepBase {

	private final PageDevoluciones pgDevoluciones = new PageDevoluciones();
	
	@Validation
	public ChecksTM validaIsPage () {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de devoluciones",
			pgDevoluciones.isPage());
		
		checks.add(
			"Aparece la opción de " + EN_TIENDA.getLiteral(),
			EN_TIENDA.isPresentLink(driver));
		
		checks.add(
			"Aparece la opcion de " + EN_DOMICILIO.getLiteral(),
			EN_DOMICILIO.isPresentLink(driver));
		
		checks.add(
			"Aparece la opción de " + PUNTO_CELERITAS.getLiteral(),
			EN_DOMICILIO.isPresentLink(driver));
		
		return checks;
	}

	@Step(
		description = "Pulsar \"Recogida gratuíta a domicilio\" + \"Solicitar Recogida\"",
		expected = "Aparece la tabla devoluciones sin ningún pedido")
	public void solicitarRegogidaGratuitaADomicilio() {
		EN_DOMICILIO.click(driver);
		EN_DOMICILIO.waitForInState(true, 2, driver);
		pgDevoluciones.clickSolicitarRecogida();
		new PageRecogidaDomicSteps().checkIsPageWithoutReturns();
		checksDefault();
	}
	
}
