package com.mng.robotest.test.steps.shop.micuenta;

import java.util.Arrays;
import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.micuenta.PageInfoNewMisComprasMovil;
import com.mng.robotest.test.pageobject.shop.micuenta.PageMiCuenta;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;
import com.mng.robotest.test.steps.shop.menus.SecMenusUserSteps;
import com.mng.robotest.test.steps.shop.miscompras.PageMisComprasSteps;


public class PageMiCuentaSteps extends StepBase {
	
	private final PageMiCuenta pageMiCuenta = new PageMiCuenta();
	private final SecMenusUserSteps userMenusSteps = new SecMenusUserSteps();
	
	@Validation(
		description="1) Aparece la página de \"Mi cuenta\" (la esperamos hasta #{maxSecondsToWait} segundos)",
		level=State.Defect)
	public boolean validateIsPage (int maxSecondsToWait) {
		return (pageMiCuenta.isPageUntil(maxSecondsToWait));
	}

	public void goToMisDatos(String usuarioReg) {
		userMenusSteps.clickMenuMiCuenta();
		clickLinkMisDatos(usuarioReg);
	}

	@Step(
		description = "Seleccionar el link \"Mis datos\"",
		expected = "Aparece la página de \"Mis datos\"")
	private void clickLinkMisDatos (String usuarioReg) {
		pageMiCuenta.clickMisDatos();
		(new PageMisDatosSteps(driver)).validaIsPage(usuarioReg);
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.SEO, 
				GenericCheck.JSerrors, 
				GenericCheck.TextsTraduced,
				GenericCheck.Analitica)).checks(driver);
	}

	public void goToMisComprasFromMenu(Pais pais) {
		userMenusSteps.clickMenuMiCuenta();
		goToMisComprasFromMenuAndValidate(pais);
	}

	@Step(
		description = "Seleccionar el link \"Mis Compras\"",
		expected = "Aparece la página de \"Mis Compras\"")
	private void goToMisComprasFromMenuAndValidate(Pais pais) {
		pageMiCuenta.clickMisCompras();
		if (channel.isDevice() &&
			PageInfoNewMisComprasMovil.isPage(driver)) {
			PageInfoNewMisComprasMovilSteps.validateIsPage(driver);
			PageInfoNewMisComprasMovilSteps.clickButtonToMisComprasAndNoValidate(driver);
		}

		PageMisComprasSteps pageMisComprasSteps = new PageMisComprasSteps(channel, app);
		pageMisComprasSteps.validateIsPage(pais);
		
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.Analitica,
				GenericCheck.TextsTraduced)).checks(driver);
	}
 
	public void goToMisDatosAndValidateData(Map<String,String> dataRegistro, String codPais) {
		goToMisDatos(dataRegistro.get("cfEmail"));
		(new PageMisDatosSteps(driver)).validaIsDataAssociatedToRegister(dataRegistro, codPais);
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.SEO, 
				GenericCheck.JSerrors, 
				GenericCheck.TextsTraduced,
				GenericCheck.Analitica)).checks(driver);
	}

	public void goToSuscripciones() {
		userMenusSteps.clickMenuMiCuenta();
		clickLinkSuscripciones();
	}

	@Step(
		description = "Seleccionar el link \"Suscripciones\"",
		expected = "Aparece la página de \"Suscripciones\"")
	private void clickLinkSuscripciones() {
		pageMiCuenta.clickSuscripciones();
		PageSuscripcionesSteps.create(driver).validaIsPage();
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.SEO, 
				GenericCheck.JSerrors, 
				GenericCheck.TextsTraduced,
				GenericCheck.Analitica)).checks(driver);
	}
	
	public void goToSuscripcionesAndValidateData(Map<String,String> datosRegOk) {
		goToSuscripciones();
		PageSuscripcionesSteps.create(driver).validaIsDataAssociatedToRegister(datosRegOk);
	}

	public void goToDevoluciones() {
		userMenusSteps.clickMenuMiCuenta();
		clickLinkDevoluciones();
	}

	@Step(
		description = "Seleccionar el link \"Devoluciones\"",
		expected = "Aparece la página de \"Devoluciones\"")
	private void clickLinkDevoluciones() {
		pageMiCuenta.clickDevoluciones();
		new PageDevolucionesSteps().validaIsPage();
	}

	public void goToReembolsos() {
		userMenusSteps.clickMenuMiCuenta();
		clickLinkReembolsos();
	}

	@Step(
		description = "Seleccionar el link \"Reembolsos\"",
		expected = "Aparece la página de \"Reembolsos\"")
	private void clickLinkReembolsos() {
		pageMiCuenta.clickReembolsos();
		PageReembolsosSteps.validateIsPage(driver);
	}
}
