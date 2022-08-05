package com.mng.robotest.test.steps.manto;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.manto.PageConsultaTienda;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;

public class PageConsultaTiendaSteps {

	@Validation (
		description="Es visible el input para la introducción de la tienda",
		level=State.Defect)
	public static boolean validateIsPage(WebDriver driver) {
		return (PageConsultaTienda.isVisibleInputTienda(driver));
	}

	@Step (
		description="Introducimos tienda #{tiendaNoExistente}</br>",
		expected="No debe ser válida",
		saveErrorData=SaveWhen.Never)
	public static void consultaTiendaInexistente(String tiendaNoExistente, WebDriver driver) {
		PageConsultaTienda.introducirTienda(tiendaNoExistente, driver);
		checkIsVisibleMessageTiendaNotExits(driver);
	}
	
	@Validation (
		description="Aparece el mensaje La tienda no existe",
		level=State.Defect)
	private static boolean checkIsVisibleMessageTiendaNotExits(WebDriver driver) {
		return (PageConsultaTienda.apareceMensajeTiendaNoExiste(driver));
	}

	@Step (
		description="Introducimos tienda <b>#{tiendaExistente}</b>",
		expected="No debe ser válida",
		saveErrorData=SaveWhen.Never)
	public static void consultaTiendaExistente(String tiendaExistente, WebDriver driver) {
		PageConsultaTienda.introducirTienda(tiendaExistente, driver);
		checkAfterInputTienda(driver);
	}
	
	@Validation
	private static ChecksTM checkAfterInputTienda(WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la información de la tienda",
			PageConsultaTienda.apareceInformacionTienda(driver), State.Defect);
	 	checks.add(
			"No aparece el mensaje de tienda no existe",
			!PageConsultaTienda.apareceMensajeTiendaNoExiste(driver), State.Defect);
	 	return checks;
	}
}
