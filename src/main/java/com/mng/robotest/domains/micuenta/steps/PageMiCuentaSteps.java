package com.mng.robotest.domains.micuenta.steps;

import java.util.List;
import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.micuenta.pageobjects.PageInfoNewMisComprasMovil;
import com.mng.robotest.domains.micuenta.pageobjects.PageMiCuenta;
import com.mng.robotest.domains.registro.beans.DataNewRegister;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;

public class PageMiCuentaSteps extends StepBase {
	
	private final PageMiCuenta pageMiCuenta = new PageMiCuenta();
	private final SecMenusUserSteps userMenusSteps = new SecMenusUserSteps();
	
	@Validation(
		description="1) Aparece la página de \"Mi cuenta\" (la esperamos hasta #{seconds} segundos)",
		level=State.Defect)
	public boolean validateIsPage (int seconds) {
		return pageMiCuenta.isPageUntil(seconds);
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
		new PageMisDatosSteps().validaIsPage(usuarioReg);
		GenericChecks.checkDefault();
	}

	public void goToMisComprasFromMenu() {
		userMenusSteps.clickMenuMiCuenta();
		goToMisComprasFromMenuAndValidate();
	}

	@Step(
		description = "Seleccionar el link \"Mis Compras\"",
		expected = "Aparece la página de \"Mis Compras\"")
	private void goToMisComprasFromMenuAndValidate() {
		pageMiCuenta.clickMisCompras();
		if (channel.isDevice() &&
			new PageInfoNewMisComprasMovil().isPage()) {
			new PageInfoNewMisComprasMovilSteps().validateIsPage();
			new PageInfoNewMisComprasMovilSteps().clickButtonToMisComprasAndNoValidate();
		}

		new PageMisComprasSteps().validateIsPage();
		GenericChecks.checkDefault();
	}
 
	public void goToMisDatosAndValidateData(Map<String,String> dataRegistro, String codPais) {
		goToMisDatos(dataRegistro.get("cfEmail"));
		new PageMisDatosSteps().validaIsDataAssociatedToRegister(dataRegistro, codPais);
		GenericChecks.checkDefault();
	}
	public void goToMisDatosAndValidateData(DataNewRegister dataNewRegister) {
		goToMisDatos(dataNewRegister.getEmail());
		new PageMisDatosSteps().validaIsDataAssociatedToRegister(dataNewRegister);
		GenericChecks.checkDefault();
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
		new PageSuscripcionesSteps().validaIsPage();
		GenericChecks.checkDefault();
	}
	
	public void goToSuscripcionesAndValidateData(Map<String,String> datosRegOk) {
		goToSuscripciones();
		new PageSuscripcionesSteps().validaIsDataAssociatedToRegister(datosRegOk);
	}
	public void goToSuscripcionesAndValidateData(List<LineaType> linesMarked) {
		goToSuscripciones();
		new PageSuscripcionesSteps().validaIsDataAssociatedToRegister(linesMarked);
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
		new PageReembolsosSteps().validateIsPage();
	}
}
