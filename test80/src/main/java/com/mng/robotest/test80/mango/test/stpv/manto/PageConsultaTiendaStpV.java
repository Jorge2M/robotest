package com.mng.robotest.test80.mango.test.stpv.manto;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageConsultaTienda;

public class PageConsultaTiendaStpV {

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
	private static ChecksResult checkAfterInputTienda(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Aparece la información de la tienda",
			PageConsultaTienda.apareceInformacionTienda(driver), State.Defect);
	 	validations.add(
			"No aparece el mensaje de tienda no existe",
			!PageConsultaTienda.apareceMensajeTiendaNoExiste(driver), State.Defect);
	 	return validations;
	}
}
