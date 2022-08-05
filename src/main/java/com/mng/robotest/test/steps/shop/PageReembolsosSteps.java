package com.mng.robotest.test.steps.shop;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.PageReembolsos;
import com.mng.robotest.test.pageobject.shop.PageReembolsos.TypeReembolso;
import com.mng.robotest.test.pageobject.shop.micuenta.PageMiCuenta;
import com.mng.robotest.test.steps.shop.menus.SecMenusUserSteps;


public class PageReembolsosSteps {

	private PageReembolsosSteps() {
	}
	
	/**
	 * Step (+validación) correspondiente a la selección del menú superior "Mi cuenta" + "Reembolsos"
	 * @param paisConSaldoCta indica si el país tiene configurado el saldo en cuenta
	 */
	public static void gotoRefundsFromMenu(
			boolean paisConSaldoCta, AppEcom app, Channel channel, WebDriver driver) {
		SecMenusUserSteps userMenusSteps = new SecMenusUserSteps(channel, app);
		userMenusSteps.clickMenuMiCuenta();
		selectReembolsos(paisConSaldoCta, driver);
	}
	
	@Step (
		description="Seleccionar la opción \"Reembolsos\"", 
		expected="Aparece la página de reembolsos")
	public static void selectReembolsos(boolean paisConSaldoCta, WebDriver driver) {
		PageMiCuenta pageMiCuenta = PageMiCuenta.getNew(driver);
		pageMiCuenta.clickReembolsos();
		checkClickReembolsos(paisConSaldoCta, driver);
	}
	
	@Validation
	private static ChecksTM checkClickReembolsos(boolean paisConSaldoCta, WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de reembolsos",
			PageReembolsos.isPage(driver), State.Defect);		
		
		int maxSecondsToWait = 5;
		boolean isVisibleTransferenciaSection = PageReembolsos.isVisibleTransferenciaSectionUntil(maxSecondsToWait, driver);
		boolean isVisibleStoreCreditSection = PageReembolsos.isVisibleStorecreditSection(driver);
		if (paisConSaldoCta) {
			checks.add(
				"El país SÍ tiene asociado Saldo en Cuenta -> Aparecen las secciones de \"Saldo en cuenta\" y \"Transferencia bancaria\"",
				isVisibleTransferenciaSection && isVisibleStoreCreditSection, State.Defect);
		} else {
			checks.add(
				"El país NO tiene asociado Saldo en Cuenta -> Aparece la sección de \"Transferencia bancaria\" y no la de \"Saldo en cuenta\"",
				isVisibleTransferenciaSection && !isVisibleStoreCreditSection, State.Defect);
		}
		
		return checks;
	}
	
	/**
	/* Step (+validación) correspondiente a la selección del menú superior "Mi cuenta" + "Reembolsos" y finalmente valida que el saldo que aparece es el que se espera 
	 * @param webdriver
	 * @param saldoEsperado saldo que validaremos exista en el apartado de "Saldo en cuenta" de la página de configuración del reembolso
	 */
	public static void gotoRefundsFromMenuAndValidaSalCta(boolean paisConSaldoCta, float saldoCtaEsperado, AppEcom app, Channel channel, WebDriver driver) 
	throws Exception {
		PageReembolsosSteps.gotoRefundsFromMenu(paisConSaldoCta, app, channel, driver);
		checkIsOkSaldoEnCuenta(saldoCtaEsperado, driver);
	}
	
	@Validation (
		description="Aparece el saldo en cuenta que esperamos: <b>#{saldoCtaEsperado}</b>",
		level=State.Defect)
	private static boolean checkIsOkSaldoEnCuenta(float saldoCtaEsperado, WebDriver driver) {
		float saldoCtaPage = PageReembolsos.getImporteStoreCredit(driver);
		return (saldoCtaEsperado==saldoCtaPage);
	}

	/**
	 * Ejecuta los pasos necesarios para validar la configuración de los reembolsos mediante transferencia
	 */
	public static void testConfTransferencia(WebDriver driver) {
		PageReembolsosSteps.selectRadioTransferencia(driver);		
		PageReembolsosSteps.informaDatosTransAndSave(driver);
	}
	
	@Step (
		description="<b>Transferencias:</b> seleccionamos el radio asociado", 
		expected="Los campos de input se hacen visibles")
	public static void selectRadioTransferencia(WebDriver driver) {	
		PageReembolsos.clickRadio(TypeReembolso.Transferencia, driver); 
		
		//Validations
		checkInputsVisiblesAfterClickTransferencia(driver);
	}
	
	@Validation (
		description="Los campos de input Banco, Titular e IBAN se hacen visibles",
		level=State.Defect)
	private static boolean checkInputsVisiblesAfterClickTransferencia(WebDriver driver) {
	   return (PageReembolsos.isVisibleInputsTransf(driver));
	}
	
	static final String BANCO = "Banco de crédito Balear";
	static final String TITULAR = "Jorge Muñoz";
	static final String IBAN = "ES8023100001180000012345";
	static final String ID_PASSPORT = "11111111";

	@Step (
		description="Informar el banco: " + BANCO + "<br>titular: " + TITULAR + "<br>IBAN: " + IBAN + "<br>y pulsar el botón \"Save\"",
		expected="La modificación de datos se realiza correctamente")
	public static void informaDatosTransAndSave(WebDriver driver) {
		PageReembolsos.typeInputsTransf(driver, BANCO, TITULAR, IBAN, ID_PASSPORT);
		PageReembolsos.clickButtonSaveTransfForce(driver);
		checkAfterModifyDataTransferencia(driver);
	}

	@Validation
	private static ChecksTM checkAfterModifyDataTransferencia(WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSecondsToWait = 15;
		checks.add(
			"Aparecen establecidos los datos de banco, titular e IBAN (lo esperamos hasta " + maxSecondsToWait + " segundos)",
			PageReembolsos.isVisibleTextBancoUntil(maxSecondsToWait, driver) &&
			PageReembolsos.isVisibleTextTitular(driver) &&
			PageReembolsos.isVisibleTextIBAN(driver), State.Defect);
		checks.add(
			"Aparece seleccionado el radiobutton de \"Transferencia bancaria\"",
			PageReembolsos.isCheckedRadio(TypeReembolso.Transferencia, driver), State.Warn);
		return checks;
	}
	
	@Step (
		description="<b>Store Credit:</b> seleccionamos el radio asociado y ejecutamos un refresh de la página", 
		expected="El checkbox de \"Store Credit\" acaba marcado")
	public static void selectRadioSalCtaAndRefresh(WebDriver driver) {
		PageReembolsos.clickRadio(TypeReembolso.StoreCredit, driver); 
		
		//Validaciones
		checkAfterSelectStoreCredit(driver);
	}
	
	@Validation
	private static ChecksTM checkAfterSelectStoreCredit(WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
	   	checks.add(
			"Aparece seleccionado el radiobutton de \"Store Credit\"",
			PageReembolsos.isCheckedRadio(TypeReembolso.StoreCredit, driver), State.Warn);
	   	checks.add(
			"Aparece un saldo >= 0",
			PageReembolsos.getImporteStoreCredit(driver) >= 0, State.Defect);
	   	return checks;
	}
	
	/**
	 * En ocasiones (principalmente en el casdo del mock) existe un botón "Guardar" que hay que seleccionar para que se active el saldo en cuenta
	 */
	@Step (
		description="<b>Store Credit:</b> Seleccionamos el botón \"Save\"", 
		expected="Desaparece el botón \"Save\"")
	public static void clickSaveButtonStoreCredit(WebDriver driver) {
		PageReembolsos.clickSaveButtonStoreCredit(driver); 
		checkButtonSaveDisappears(2, driver);
	}
	
	@Validation (
		description="Desaparece el botón \"Save\" de Store Credit (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	private static boolean checkButtonSaveDisappears(int maxSeconds, WebDriver driver) {
		return (!PageReembolsos.isVisibleSaveButtonStoreCreditUntil(maxSeconds, driver));
	}
}
