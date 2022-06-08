package com.mng.robotest.test.stpv.shop;

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
import com.mng.robotest.test.stpv.shop.menus.SecMenusUserStpV;

/**
 * Clase que engloba los pasos/validaciones relacionados con la página de configuración de los Reembolsos
 * @author jorge.munoz
 *
 */
public class PageReembolsosStpV {

	/**
	 * Step (+validación) correspondiente a la selección del menú superior "Mi cuenta" + "Reembolsos"
	 * @param paisConSaldoCta indica si el país tiene configurado el saldo en cuenta
	 */
	public static void gotoRefundsFromMenu(boolean paisConSaldoCta, AppEcom app, Channel channel, WebDriver driver) 
	throws Exception {
		SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(channel, app, driver);
		userMenusStpV.clickMenuMiCuenta();
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
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Aparece la página de reembolsos",
			PageReembolsos.isPage(driver), State.Defect);		
		
		int maxSecondsToWait = 5;
		boolean isVisibleTransferenciaSection = PageReembolsos.isVisibleTransferenciaSectionUntil(maxSecondsToWait, driver);
		boolean isVisibleStoreCreditSection = PageReembolsos.isVisibleStorecreditSection(driver);
		if (paisConSaldoCta) {
			validations.add(
				"El país SÍ tiene asociado Saldo en Cuenta -> Aparecen las secciones de \"Saldo en cuenta\" y \"Transferencia bancaria\"",
				isVisibleTransferenciaSection && isVisibleStoreCreditSection, State.Defect);
		} else {
			validations.add(
				"El país NO tiene asociado Saldo en Cuenta -> Aparece la sección de \"Transferencia bancaria\" y no la de \"Saldo en cuenta\"",
				isVisibleTransferenciaSection && !isVisibleStoreCreditSection, State.Defect);
		}
		
		return validations;
	}
	
	/**
	/* Step (+validación) correspondiente a la selección del menú superior "Mi cuenta" + "Reembolsos" y finalmente valida que el saldo que aparece es el que se espera 
	 * @param webdriver
	 * @param saldoEsperado saldo que validaremos exista en el apartado de "Saldo en cuenta" de la página de configuración del reembolso
	 */
	public static void gotoRefundsFromMenuAndValidaSalCta(boolean paisConSaldoCta, float saldoCtaEsperado, AppEcom app, Channel channel, WebDriver driver) 
	throws Exception {
		PageReembolsosStpV.gotoRefundsFromMenu(paisConSaldoCta, app, channel, driver);
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
	public static void testConfTransferencia(WebDriver driver) throws Exception {
		PageReembolsosStpV.selectRadioTransferencia(driver);		
		PageReembolsosStpV.informaDatosTransAndSave(driver);
	}
	
	@Step (
		description="<b>Transferencias:</b> seleccionamos el radio asociado", 
		expected="Los campos de input se hacen visibles")
	public static void selectRadioTransferencia(WebDriver driver) throws Exception {	
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
	
	final static String banco = "Banco de crédito Balear";
	final static String titular = "Jorge Muñoz";
	final static String IBAN = "ES8023100001180000012345";
	final static String idPassport = "11111111";

	@Step (
		description="Informar el banco: " + banco + "<br>titular: " + titular + "<br>IBAN: " + IBAN + "<br>y pulsar el botón \"Save\"",
		expected="La modificación de datos se realiza correctamente")
	public static void informaDatosTransAndSave(WebDriver driver) throws Exception {
		PageReembolsos.typeInputsTransf(driver, banco, titular, IBAN, idPassport);
		PageReembolsos.clickButtonSaveTransfForce(driver);
		checkAfterModifyDataTransferencia(driver);
	}

	@Validation
	private static ChecksTM checkAfterModifyDataTransferencia(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		int maxSecondsToWait = 15;
		validations.add(
			"Aparecen establecidos los datos de banco, titular e IBAN (lo esperamos hasta " + maxSecondsToWait + " segundos)",
			PageReembolsos.isVisibleTextBancoUntil(maxSecondsToWait, driver) &&
			PageReembolsos.isVisibleTextTitular(driver) &&
			PageReembolsos.isVisibleTextIBAN(driver), State.Defect);
		validations.add(
			"Aparece seleccionado el radiobutton de \"Transferencia bancaria\"",
			PageReembolsos.isCheckedRadio(TypeReembolso.Transferencia, driver), State.Warn);
		return validations;
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
		ChecksTM validations = ChecksTM.getNew();
	   	validations.add(
			"Aparece seleccionado el radiobutton de \"Store Credit\"",
			PageReembolsos.isCheckedRadio(TypeReembolso.StoreCredit, driver), State.Warn);
	   	validations.add(
			"Aparece un saldo >= 0",
			PageReembolsos.getImporteStoreCredit(driver) >= 0, State.Defect);
	   	return validations;
	}
	
	/**
	 * En ocasiones (principalmente en el casdo del mock) existe un botón "Guardar" que hay que seleccionar para que se active el saldo en cuenta
	 */
	@Step (
		description="<b>Store Credit:</b> Seleccionamos el botón \"Save\"", 
		expected="Desaparece el botón \"Save\"")
	public static void clickSaveButtonStoreCredit(WebDriver driver) throws Exception {
		PageReembolsos.clickSaveButtonStoreCredit(driver); 
		
		//Validaciones
		int maxSeconds = 2;
		checkButtonSaveDisappears(maxSeconds, driver);
	}
	
	@Validation (
		description="Desaparece el botón \"Save\" de Store Credit (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	private static boolean checkButtonSaveDisappears(int maxSeconds, WebDriver driver) {
		return (!PageReembolsos.isVisibleSaveButtonStoreCreditUntil(maxSeconds, driver));
	}
}