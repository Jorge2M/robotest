package com.mng.robotest.tests.domains.micuenta.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.testslegacy.pageobject.shop.PageDevoluciones;
import com.mng.robotest.testslegacy.pageobject.shop.PageDevoluciones.Devolucion;

import static com.mng.robotest.testslegacy.pageobject.shop.PageDevoluciones.Devolucion.*;

public class PageDevolucionesSteps extends StepBase {

	private final PageDevoluciones pgDevoluciones = new PageDevoluciones();
	
	@Validation
	public ChecksTM checkIsPage () {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de devoluciones",
			pgDevoluciones.isPage(1));
		
		for (Devolucion devolucion : Devolucion.values()) {
			checks.add(
				"Aparece la opción de " + devolucion.getLiteral(),
				pgDevoluciones.isVisible(devolucion, 1));
		}
		return checks;
	}

	@Step(
		description = "Pulsar <b>Recogida gratuíta a domicilio + Solicitar Recogida</b>",
		expected = "Aparece la tabla devoluciones sin ningún pedido")
	public void solicitarRegogidaGratuitaADomicilio() {
		pgDevoluciones.click(RECOGIDA_GRATUITA_A_DOMICILIO);
		pgDevoluciones.clickSolicitarRecogida();
		new PageRecogidaDomicSteps().checkIsPageWithoutReturns();
		checksDefault();
	}
	
}
