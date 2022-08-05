package com.mng.robotest.test.steps.shop.micuenta;

import java.util.Arrays;
import java.util.Map;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.micuenta.PageInfoNewMisComprasMovil;
import com.mng.robotest.test.pageobject.shop.micuenta.PageMiCuenta;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;
import com.mng.robotest.test.steps.shop.menus.SecMenusUserSteps;
import com.mng.robotest.test.steps.shop.miscompras.PageMisComprasSteps;

import org.openqa.selenium.WebDriver;

public class PageMiCuentaSteps {
	
	private final PageMiCuenta pageMiCuenta;
	private final WebDriver driver;
	private final Channel channel;
	private final AppEcom app;
	
	private PageMiCuentaSteps(Channel channel, AppEcom app, WebDriver driver) {
		pageMiCuenta = PageMiCuenta.getNew(driver);
		this.driver = driver;
		this.channel = channel;
		this.app = app;
	}
	public static PageMiCuentaSteps getNew(Channel channel, AppEcom app, WebDriver driver) {
		return new PageMiCuentaSteps(channel, app, driver);
	}
	
	@Validation(
		description="1) Aparece la página de \"Mi cuenta\" (la esperamos hasta #{maxSecondsToWait} segundos)",
		level=State.Defect)
	public boolean validateIsPage (int maxSecondsToWait) {
		return (pageMiCuenta.isPageUntil(maxSecondsToWait));
	}

	public void goToMisDatos(String usuarioReg) {
		SecMenusUserSteps userMenusSteps = new SecMenusUserSteps(channel, app);
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
		SecMenusUserSteps userMenusSteps = new SecMenusUserSteps(channel, app);
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
		SecMenusUserSteps userMenusSteps = new SecMenusUserSteps(channel, app);
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
		SecMenusUserSteps userMenusSteps = new SecMenusUserSteps(channel, app);
		userMenusSteps.clickMenuMiCuenta();
		clickLinkDevoluciones();
	}

	@Step(
		description = "Seleccionar el link \"Devoluciones\"",
		expected = "Aparece la página de \"Devoluciones\"")
	private void clickLinkDevoluciones() {
		pageMiCuenta.clickDevoluciones();
		PageDevolucionesSteps.validaIsPage(driver);
	}

	public void goToReembolsos() {
		SecMenusUserSteps userMenusSteps = new SecMenusUserSteps(channel, app);
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
