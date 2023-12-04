package com.mng.robotest.tests.domains.chequeregalo.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.chequeregalo.beans.ChequeRegalo;
import com.mng.robotest.tests.domains.chequeregalo.pageobjects.PageChequeRegaloInputData;
import com.mng.robotest.tests.domains.chequeregalo.pageobjects.PageChequeRegaloInputDataNew;
import com.mng.robotest.tests.domains.chequeregalo.pageobjects.PageChequeRegaloInputData.*;
import com.mng.robotest.tests.domains.compra.steps.CheckoutSteps;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageChequeRegaloInputDataSteps extends StepBase {

	private final PageChequeRegaloInputData pgChequeRegaloInputData = PageChequeRegaloInputData.make(dataTest.getPais());
	
	public void paginaConsultarSaldo(String numTarjeta) {
		clickConsultaSaldo();
		inputDataTarjetaRegalo(numTarjeta);
	}
	
	@Step (
		description="Accedemos a la página de consultar saldo de cheque regalo con sus respectivos campos",
		expected="Aparecen todos los campos y volvemos a la operativa normal")
	public void clickConsultaSaldo() {
		((PageChequeRegaloInputDataNew)pgChequeRegaloInputData)
			.clickConsultaSaldo();	
		checkInputDataTarjeta(2);
	}

	@Validation (
		description="Aparece el cuadro de introduccion de datos determinado " + SECONDS_WAIT)
	private boolean checkInputDataTarjeta(int seconds) {
		return ((PageChequeRegaloInputDataNew)pgChequeRegaloInputData).isInputTarjetaVisible(seconds);
	}
	
	@Step (
		description="Introducimos en el campo de <b>tarjeta regalo</b>#{numTarjeta} para consultar el saldo",
		expected="Se carga la página donde salen nuevos campos visibles como el de <b>cvv</b>")
	public void inputDataTarjetaRegalo(String numTarjeta) {
		var pageNew = ((PageChequeRegaloInputDataNew)pgChequeRegaloInputData);
		pageNew.introducirTarjetaConsultaSaldo(numTarjeta);
		pageNew.clickBotonValidar();
		checkInputOtherData(2);
	}
	
	@Validation (
		description="Es visible el campo de <b>cvv</b> " + SECONDS_WAIT + "</br>")
	private boolean checkInputOtherData(int seconds) {
		var pgNew = ((PageChequeRegaloInputDataNew)pgChequeRegaloInputData);
		return pgNew.isVisibleCvv(seconds);
	}

	public void insertCVVConsultaSaldo(String cvvNumber) {
		consultarSaldoTarjeta(cvvNumber);
		backToInitPageChequeRegalo();
	}
	
	/*TODO A la espera del cvv para completar esta parte*/
	@Step (
		description="Introducimos el CVV <b>#{cvvNumber}</b> de la tarjeta",
		expected="Se vuelve a la página inicial del cheque regalo"/*Temporal*/)
	private void consultarSaldoTarjeta(String cvvNumber) {
		((PageChequeRegaloInputDataNew)pgChequeRegaloInputData)
			.introducirCvc(cvvNumber);
		checkConsultaSaldoTarjeta(4);
	}
	
	@Validation (
		description="La tarjeta introducida no tiene saldo disponible",
		level=WARN)
	private boolean checkConsultaSaldoTarjeta(int seconds) {
		return ((PageChequeRegaloInputDataNew)pgChequeRegaloInputData).isTarjetaWithoutSaldo(seconds);
	}
	
	@Step (
		description="Usamos el boton de volver",
		expected="Estamos en la página inicial de <b>Cheque Regalo</b>")
	private void backToInitPageChequeRegalo() {
		((PageChequeRegaloInputDataNew)pgChequeRegaloInputData).clickBotonVolver();
		checkIsInitPage(2);
	}

	@Validation (description="Estamos en la página inicial " + SECONDS_WAIT)
	private boolean checkIsInitPage(int seconds) {
		return ((PageChequeRegaloInputDataNew)pgChequeRegaloInputData).isPageCorrectUntil(seconds);
	}

	@Step (
		description="Seleccionamos la cantidad de <b>#{importeToClick}</b>",
		expected="Se selecciona correctamente el importe")
	public void seleccionarCantidades(Importe importeToClick) {
		pgChequeRegaloInputData.clickImporteCheque(importeToClick);
	}

	@Step (
		description="Seleccionar link \"Comprar ahora\"",
		expected="Aparece la capa para introducir los datos de la operación")
	public void clickQuieroComprarChequeRegalo() {
		pgChequeRegaloInputData.clickComprarIni();
		checkIsVisibleCapaInputCheque(3);
	}
	
	@Validation (
		description="Aparece la capa para introducir los datos del cheque regalo " + SECONDS_WAIT)
	private boolean checkIsVisibleCapaInputCheque(int seconds) {
		return pgChequeRegaloInputData.isVisibleDataInput(seconds);
	}

	@Step (
		description=
			"Introducir los datos del cheque y pulsar el botón <b>Comprar</b>:<br>" +
			"Nombre: <b>#{chequeRegalo.getNombre()}</b><br>" +
			"Apellidos: <b>#{chequeRegalo.getApellidos()}</b><br>" +
			"Email: <b>#{chequeRegalo.getEmail()}</b><br>" +
			"Importe: <b>#{chequeRegalo.getImporte()}</b><br>" +
			"Mensaje: <b>#{chequeRegalo.getMensaje()}</b>",
		expected="Aparece la página de identificación del usuario")
	public void inputDataAndClickComprar(Channel channel, AppEcom app, ChequeRegalo chequeRegalo) {
		pgChequeRegaloInputData.inputDataCheque(chequeRegalo);
		pgChequeRegaloInputData.clickComprarFin(chequeRegalo);
		new CheckoutSteps().validaIsVersionChequeRegalo(chequeRegalo);
	}
}
