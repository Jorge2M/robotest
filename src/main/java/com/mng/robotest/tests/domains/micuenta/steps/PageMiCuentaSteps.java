package com.mng.robotest.tests.domains.micuenta.steps;

import static com.mng.robotest.tests.domains.micuenta.pageobjects.PageMiCuenta.Link.*;

import java.util.List;
import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.tests.domains.menus.steps.SecMenusUserSteps;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageInfoNewMisComprasMovil;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageMiCuenta;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;

public class PageMiCuentaSteps extends StepBase {
	
	private final PageMiCuenta pgMiCuenta = new PageMiCuenta();
	private final SecMenusUserSteps userMenusSteps = new SecMenusUserSteps();
	
	@Validation(description="Aparece la página de \"Mi cuenta\" " + SECONDS_WAIT)
	public boolean validateIsPage (int seconds) {
		return pgMiCuenta.isPage(seconds);
	}

	public void goToMisDatos(String usuarioReg) {
		userMenusSteps.clickMenuMiCuenta();
		clickLinkMisDatos(usuarioReg);
	}
	
	private void goToMisDirecciones() {
		userMenusSteps.clickMenuMiCuenta();
	    clickLinkMisDirecciones();
	}

	public void clickLinkMisDatos() {
		clickLinkMisDatos(dataTest.getUserConnected());
	}
	
	@Step(
		description = "Seleccionar el link <b>Mis datos</b>",
		expected = "Aparece la página de Mis datos")
	private void clickLinkMisDatos(String usuarioReg) {
		pgMiCuenta.click(MIS_DATOS);
		new PageMisDatosSteps().validaIsPage(usuarioReg);
		checksDefault();
	}
	
	@Step(
		description = "Seleccionar el link <b>Mis direcciones</b>",
		expected = "Aparece la página de Mis direcciones")
	private void clickLinkMisDirecciones() {
		pgMiCuenta.click(MIS_DIRECCIONES);
		new PageMisDireccionesSteps().checkIsPage(3);
		checksDefault();
	}	

	public void goToMisComprasFromMenu() {
		userMenusSteps.clickMenuMiCuenta();
		goToMisComprasFromMenuAndValidate();
	}

	@Step(
		description = "Seleccionar el link \"Mis Compras\"",
		expected = "Aparece la página de \"Mis Compras\"")
	private void goToMisComprasFromMenuAndValidate() {
		pgMiCuenta.click(MIS_COMPRAS);
		if (channel.isDevice() &&
			new PageInfoNewMisComprasMovil().isPage()) {
			new PageInfoNewMisComprasMovilSteps().validateIsPage();
			new PageInfoNewMisComprasMovilSteps().clickButtonToMisComprasAndNoValidate();
		}

		new PageMisComprasSteps().validateIsPage();
		checksDefault();
	}
 
	public void goToMisDatosAndValidateData(Map<String,String> dataRegistro, String codPais) {
		if (dataTest.getPais().isMisdirecciones(app)) {
			goToMisDirecciones();
			new PageMisDireccionesSteps().checkData(dataRegistro);
		}		
		goToMisDatos(dataRegistro.get("cfEmail"));
		new PageMisDatosSteps().validaIsDataAssociatedToRegister(dataRegistro, codPais);
		checksDefault();
	}
	public void goToMisDatosAndValidateData(DataNewRegister dataNewRegister) {
		if (dataTest.getPais().isMisdirecciones(app)) {
			goToMisDirecciones();
			new PageMisDireccionesSteps().checkData();
		}
		goToMisDatos(dataNewRegister.getEmail());
		new PageMisDatosSteps().validaIsDataAssociatedToRegister(dataNewRegister);
		checksDefault();
	}	
	
	public void goToSuscripciones() {
		userMenusSteps.clickMenuMiCuenta();
		clickLinkSuscripciones();
	}

	@Step(
		description = "Seleccionar el link \"Suscripciones\"",
		expected = "Aparece la página de \"Suscripciones\"")
	private void clickLinkSuscripciones() {
		pgMiCuenta.click(SUSCRIPCIONES);
		new PageSuscripcionesSteps().validaIsPage();
		checksDefault();
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
		pgMiCuenta.click(DEVOLUCIONES);
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
		pgMiCuenta.click(REEMBOLSOS);
		new PageReembolsosSteps().checkIsPage();
	}
	
}
