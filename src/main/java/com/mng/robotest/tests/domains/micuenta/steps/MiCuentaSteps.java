package com.mng.robotest.tests.domains.micuenta.steps;

import java.util.List;
import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageInfoNewMisComprasMovil;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageMiCuenta;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.utils.UtilsTest;

import static com.mng.robotest.tests.domains.micuenta.pageobjects.LinkMiCuenta.*;

public class MiCuentaSteps extends StepBase {
	
	private final PageMiCuenta pgMiCuenta = PageMiCuenta.make(dataTest.getPais(), app);
	private final MenusUserSteps userMenusSteps = new MenusUserSteps();
	
	public void goTo() {
		goToPortada();
		new MenusUserSteps().clickMenuMiCuenta();
	}
	
	@Validation
	public ChecksTM checkIsPage(State state, int seconds) {
		var checks = ChecksTM.getNew();
	  	checks.add(
	  		"Aparece la página de \"Mi cuenta\" " + getLitSecondsWait(seconds),
			pgMiCuenta.isPage(seconds), state);
	  	return checks;
	}	

	public void checkIsPedido(DataPago dataPago) {
		isVisiblePedido(dataPago.getDataPedido().getCodpedido(), 3);
	}
	
	@Validation(description="El primer pedido es el <b>#{idOrder}</b> " + SECONDS_WAIT)
	private boolean isVisiblePedido(String idOrder, int seconds) {
		return pgMiCuenta.isPurchase(idOrder, seconds);
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
		var resultChecks = new MisDatosSteps().checkIsPage(usuarioReg);
		workAroundMisDatosProblem(usuarioReg, !resultChecks.isAvoidEvidences());
		checksDefault();
	}
	
	private void workAroundMisDatosProblem(String usuarioReg, boolean apply) {
		//TODO workaround 06-08-2024 para corregir el problema de login->mis datos
		if (UtilsTest.todayBeforeDate("2024-09-06") && apply) {
			back();
			pgMiCuenta.click(MIS_DATOS);
			new MisDatosSteps().checkIsPage(usuarioReg);			
		}
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
		new MisDatosSteps().validaIsDataAssociatedToRegister(dataRegistro, codPais);
		checksDefault();
	}
	public void goToMisDatosAndCheckData(DataNewRegister dataNewRegister) {
		if (dataTest.getPais().isMisdirecciones(app)) {
			goToMisDirecciones();
			new PageMisDireccionesSteps().checkData();
		}
		goToMisDatos(dataNewRegister.getEmail());
		new MisDatosSteps().checkIsDataAssociatedToRegister(dataNewRegister);
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
		new PageSuscripcionesSteps().checkIsDataAssociatedToRegister(linesMarked);
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
		new PageDevolucionesSteps().checkIsPage();
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
