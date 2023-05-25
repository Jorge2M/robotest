package com.mng.robotest.domains.micuenta.steps;

import java.util.List;
import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.micuenta.pageobjects.PageInfoNewMisComprasMovil;
import com.mng.robotest.domains.micuenta.pageobjects.PageMiCuenta;
import com.mng.robotest.domains.registro.beans.DataNewRegister;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps;

import static com.mng.robotest.domains.micuenta.pageobjects.PageMiCuenta.Link.*;

public class PageMiCuentaSteps extends StepBase {
	
	private final PageMiCuenta pageMiCuenta = new PageMiCuenta();
	private final SecMenusUserSteps userMenusSteps = new SecMenusUserSteps();
	
	@Validation(description="Aparece la página de \"Mi cuenta\" (la esperamos hasta #{seconds} segundos)")
	public boolean validateIsPage (int seconds) {
		return pageMiCuenta.isPageUntil(seconds);
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
		pageMiCuenta.click(MIS_DATOS);
		new PageMisDatosSteps().validaIsPage(usuarioReg);
		checksDefault();
	}
	
	@Step(
		description = "Seleccionar el link <b>Mis direcciones</b>",
		expected = "Aparece la página de Mis direcciones")
	private void clickLinkMisDirecciones() {
		pageMiCuenta.click(MIS_DIRECCIONES);
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
		pageMiCuenta.click(MIS_COMPRAS);
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
		pageMiCuenta.click(SUSCRIPCIONES);
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
		pageMiCuenta.click(DEVOLUCIONES);
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
		pageMiCuenta.click(REEMBOLSOS);
		new PageReembolsosSteps().validateIsPage();
	}
}
