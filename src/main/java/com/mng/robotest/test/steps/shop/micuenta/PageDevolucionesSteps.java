package com.mng.robotest.test.steps.shop.micuenta;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.PageDevoluciones;
import com.mng.robotest.test.pageobject.shop.PageDevoluciones.Devolucion;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;

public class PageDevolucionesSteps {

	@Validation
	public static ChecksTM validaIsPage (WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de devoluciones",
			PageDevoluciones.isPage(driver), State.Defect);
		checks.add(
			"Aparece la opción de " + Devolucion.EnTienda.getLiteral(),
			Devolucion.EnTienda.isPresentLink(driver), State.Defect);
		checks.add(
			"Aparece la opcion de " + Devolucion.EnDomicilio.getLiteral(),
			Devolucion.EnDomicilio.isPresentLink(driver), State.Defect);
		checks.add(
			"Aparece la opción de " + Devolucion.PuntoCeleritas.getLiteral(),
			Devolucion.EnDomicilio.isPresentLink(driver), State.Defect);
		return checks;
	}

	@Step(
		description = "Pulsar \"Recogida gratuíta a domicilio\" + \"Solicitar Recogida\"",
		expected = "Aparece la tabla devoluciones sin ningún pedido")
	public static void solicitarRegogidaGratuitaADomicilio(WebDriver driver) {
		boolean desplegada = true;
		Devolucion.EnDomicilio.click(driver);
		Devolucion.EnDomicilio.waitForInState(desplegada, 2, driver);
		PageDevoluciones.clickSolicitarRecogida(driver);
		PageRecogidaDomicSteps.vaidaIsPageSinDevoluciones(driver);
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.SEO, 
				GenericCheck.JSerrors, 
				GenericCheck.TextsTraduced,
				GenericCheck.Analitica)).checks(driver);
	}
}
