package com.mng.robotest.test.steps.shop.micuenta;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.PageDevoluciones;
import com.mng.robotest.test.pageobject.shop.PageDevoluciones.Devolucion;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;

import java.util.Arrays;


public class PageDevolucionesSteps extends StepBase {

	PageDevoluciones pageDevoluciones = new PageDevoluciones();
	
	@Validation
	public ChecksTM validaIsPage () {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de devoluciones",
			pageDevoluciones.isPage(), State.Defect);
		checks.add(
			"Aparece la opción de " + Devolucion.EN_TIENDA.getLiteral(),
			Devolucion.EN_TIENDA.isPresentLink(driver), State.Defect);
		checks.add(
			"Aparece la opcion de " + Devolucion.EN_DOMICILIO.getLiteral(),
			Devolucion.EN_DOMICILIO.isPresentLink(driver), State.Defect);
		checks.add(
			"Aparece la opción de " + Devolucion.PUNTO_CELERITAS.getLiteral(),
			Devolucion.EN_DOMICILIO.isPresentLink(driver), State.Defect);
		return checks;
	}

	@Step(
		description = "Pulsar \"Recogida gratuíta a domicilio\" + \"Solicitar Recogida\"",
		expected = "Aparece la tabla devoluciones sin ningún pedido")
	public void solicitarRegogidaGratuitaADomicilio() {
		boolean desplegada = true;
		Devolucion.EN_DOMICILIO.click(driver);
		Devolucion.EN_DOMICILIO.waitForInState(desplegada, 2, driver);
		pageDevoluciones.clickSolicitarRecogida();
		new PageRecogidaDomicSteps().vaidaIsPageSinDevoluciones();
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.SEO, 
				GenericCheck.JSerrors, 
				GenericCheck.TextsTraduced,
				GenericCheck.Analitica)).checks(driver);
	}
}
