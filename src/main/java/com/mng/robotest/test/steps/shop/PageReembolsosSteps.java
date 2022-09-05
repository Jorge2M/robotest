package com.mng.robotest.test.steps.shop;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.micuenta.pageobjects.PageMiCuenta;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.PageReembolsos;
import com.mng.robotest.test.pageobject.shop.PageReembolsos.TypeReembolso;
import com.mng.robotest.test.steps.shop.menus.SecMenusUserSteps;

public class PageReembolsosSteps extends StepBase {

	private final PageReembolsos pageReembolsos = new PageReembolsos(); 
	
	public void gotoRefundsFromMenu(boolean paisConSaldoCta) {
		new SecMenusUserSteps().clickMenuMiCuenta();
		selectReembolsos(paisConSaldoCta);
	}
	
	@Step (
		description="Seleccionar la opción \"Reembolsos\"", 
		expected="Aparece la página de reembolsos")
	public void selectReembolsos(boolean paisConSaldoCta) {
		new PageMiCuenta().clickReembolsos();
		checkClickReembolsos(paisConSaldoCta);
	}
	
	@Validation
	private ChecksTM checkClickReembolsos(boolean paisConSaldoCta) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la página de reembolsos",
			pageReembolsos.isPage(), State.Defect);		
		
		int maxSecondsToWait = 5;
		boolean isVisibleTransferenciaSection = pageReembolsos.isVisibleTransferenciaSectionUntil(maxSecondsToWait);
		boolean isVisibleStoreCreditSection = pageReembolsos.isVisibleStorecreditSection();
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
	public void gotoRefundsFromMenuAndValidaSalCta(boolean paisConSaldoCta, float saldoCtaEsperado) throws Exception {
		gotoRefundsFromMenu(paisConSaldoCta);
		checkIsOkSaldoEnCuenta(saldoCtaEsperado);
	}
	
	@Validation (
		description="Aparece el saldo en cuenta que esperamos: <b>#{saldoCtaEsperado}</b>",
		level=State.Defect)
	private boolean checkIsOkSaldoEnCuenta(float saldoCtaEsperado) {
		float saldoCtaPage = pageReembolsos.getImporteStoreCredit();
		return (saldoCtaEsperado==saldoCtaPage);
	}

	public void testConfTransferencia() {
		selectRadioTransferencia();		
		informaDatosTransAndSave();
	}
	
	@Step (
		description="<b>Transferencias:</b> seleccionamos el radio asociado", 
		expected="Los campos de input se hacen visibles")
	public void selectRadioTransferencia() {	
		pageReembolsos.clickRadio(TypeReembolso.TRANSFERENCIA); 
		checkInputsVisiblesAfterClickTransferencia();
	}
	
	@Validation (
		description="Los campos de input Banco, Titular e IBAN se hacen visibles",
		level=State.Defect)
	private boolean checkInputsVisiblesAfterClickTransferencia() {
	   return pageReembolsos.isVisibleInputsTransf();
	}
	
	static final String BANCO = "Banco de crédito Balear";
	static final String TITULAR = "Jorge Muñoz";
	static final String IBAN = "ES8023100001180000012345";
	static final String ID_PASSPORT = "11111111";

	@Step (
		description="Informar el banco: " + BANCO + "<br>titular: " + TITULAR + "<br>IBAN: " + IBAN + "<br>y pulsar el botón \"Save\"",
		expected="La modificación de datos se realiza correctamente")
	public void informaDatosTransAndSave() {
		pageReembolsos.typeInputsTransf(BANCO, TITULAR, IBAN, ID_PASSPORT);
		pageReembolsos.clickButtonSaveTransfForce();
		checkAfterModifyDataTransferencia();
	}

	@Validation
	private ChecksTM checkAfterModifyDataTransferencia() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSecondsToWait = 15;
		checks.add(
			"Aparecen establecidos los datos de banco, titular e IBAN (lo esperamos hasta " + maxSecondsToWait + " segundos)",
			pageReembolsos.isVisibleTextBancoUntil(maxSecondsToWait) &&
			pageReembolsos.isVisibleTextTitular() &&
			pageReembolsos.isVisibleTextIBAN(), State.Defect);
		
		checks.add(
			"Aparece seleccionado el radiobutton de \"Transferencia bancaria\"",
			pageReembolsos.isCheckedRadio(TypeReembolso.TRANSFERENCIA), State.Warn);
		
		return checks;
	}
	
	@Step (
		description="<b>Store Credit:</b> seleccionamos el radio asociado y ejecutamos un refresh de la página", 
		expected="El checkbox de \"Store Credit\" acaba marcado")
	public void selectRadioSalCtaAndRefresh() {
		pageReembolsos.clickRadio(TypeReembolso.STORE_CREDIT); 
		checkAfterSelectStoreCredit();
	}
	
	@Validation
	private ChecksTM checkAfterSelectStoreCredit() {
		ChecksTM checks = ChecksTM.getNew();
	   	checks.add(
			"Aparece seleccionado el radiobutton de \"Store Credit\"",
			pageReembolsos.isCheckedRadio(TypeReembolso.STORE_CREDIT), State.Warn);
	   	
	   	checks.add(
			"Aparece un saldo >= 0",
			pageReembolsos.getImporteStoreCredit()>=0, State.Defect);
	   	
	   	return checks;
	}
	
	@Step (
		description="<b>Store Credit:</b> Seleccionamos el botón \"Save\"", 
		expected="Desaparece el botón \"Save\"")
	public void clickSaveButtonStoreCredit() {
		pageReembolsos.clickSaveButtonStoreCredit(); 
		checkButtonSaveDisappears(2);
	}
	
	@Validation (
		description="Desaparece el botón \"Save\" de Store Credit (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	private boolean checkButtonSaveDisappears(int maxSeconds) {
		return !pageReembolsos.isVisibleSaveButtonStoreCreditUntil(maxSeconds);
	}
}
