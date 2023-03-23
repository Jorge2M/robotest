package com.mng.robotest.test.steps.shop.checqueregalo;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.test.generic.ChequeRegalo;
import com.mng.robotest.test.pageobject.chequeregalo.PageChequeRegaloInputData;
import com.mng.robotest.test.pageobject.chequeregalo.PageChequeRegaloInputDataNew;
import com.mng.robotest.test.pageobject.chequeregalo.PageChequeRegaloInputData.*;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageChequeRegaloInputDataSteps extends PageBase {

	private final PageChequeRegaloInputData pageChequeRegaloInputData = PageChequeRegaloInputData.make(dataTest.getPais());
	
	public void paginaConsultarSaldo(String numTarjeta) {
		clickConsultaSaldo();
		inputDataTarjetaRegalo(numTarjeta);
	}
	
	@Step (
		description="Accedemos a la página de consultar saldo de cheque regalo con sus respectivos campos",
		expected="Aparecen todos los campos y volvemos a la operativa normal")
	public void clickConsultaSaldo() {
		((PageChequeRegaloInputDataNew)pageChequeRegaloInputData)
			.clickConsultaSaldo();	
		checkInputDataTarjeta(2);
	}

	@Validation (
		description="Aparece el cuadro de introduccion de datos determinado (lo esperamos hasta #{maxSegundos} segundos)")
	private boolean checkInputDataTarjeta(int maxSegundos) {
		return ((PageChequeRegaloInputDataNew)pageChequeRegaloInputData).isInputTarjetaVisible(maxSegundos);
	}
	
	@Step (
		description="Introducimos en el campo de <b>tarjeta regalo</b>#{numTarjeta} para consultar el saldo",
		expected="Se carga la página donde salen nuevos campos visibles como el de <b>cvv</b>")
	public void inputDataTarjetaRegalo(String numTarjeta) {
		var pageNew = ((PageChequeRegaloInputDataNew)pageChequeRegaloInputData);
		pageNew.introducirTarjetaConsultaSaldo(numTarjeta);
		pageNew.clickBotonValidar();
		checkInputOtherData(2);
	}
	
	@Validation (
		description="Es visible el campo de <b>cvv</b> (lo esperamos hasta #{seconds} segundos)</br>")
	private boolean checkInputOtherData(int seconds) {
		var pageNew = ((PageChequeRegaloInputDataNew)pageChequeRegaloInputData);
		return pageNew.isVisibleCvv(seconds);
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
		((PageChequeRegaloInputDataNew)pageChequeRegaloInputData)
			.introducirCvc(cvvNumber);
		checkConsultaSaldoTarjeta(4);
	}
	
	@Validation (
		description="La tarjeta introducida no tiene saldo disponible",
		level=Warn)
	private boolean checkConsultaSaldoTarjeta(int seconds) {
		return ((PageChequeRegaloInputDataNew)pageChequeRegaloInputData).isTarjetaWithoutSaldo(seconds);
	}
	
	@Step (
		description="Usamos el boton de volver",
		expected="Estamos en la página inicial de <b>Cheque Regalo</b>")
	private void backToInitPageChequeRegalo() {
		((PageChequeRegaloInputDataNew)pageChequeRegaloInputData).clickBotonVolver();
		checkIsInitPage(2);
	}

	@Validation (description="Estamos en la página inicial (la esperamos hasta #{seconds} segundos)")
	private boolean checkIsInitPage(int seconds) {
		return ((PageChequeRegaloInputDataNew)pageChequeRegaloInputData).isPageCorrectUntil(seconds);
	}

	@Step (
		description="Seleccionamos la cantidad de <b>#{importeToClick}</b>",
		expected="Se selecciona correctamente el importe")
	public void seleccionarCantidades(Importe importeToClick) {
		pageChequeRegaloInputData.clickImporteCheque(importeToClick);
	}

	@Step (
		description="Seleccionar link \"Comprar ahora\"",
		expected="Aparece la capa para introducir los datos de la operación")
	public void clickQuieroComprarChequeRegalo() {
		pageChequeRegaloInputData.clickComprarIni();
		checkIsVisibleCapaInputCheque(3);
	}
	
	@Validation (
		description="Aparece la capa para introducir los datos del cheque regalo (la esperamos hasta #{seconds} segundos)")
	private boolean checkIsVisibleCapaInputCheque(int seconds) {
		return pageChequeRegaloInputData.isVisibleDataInput(seconds);
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
		pageChequeRegaloInputData.inputDataCheque(chequeRegalo);
		pageChequeRegaloInputData.clickComprarFin(chequeRegalo);
		new CheckoutSteps().validaIsVersionChequeRegalo(chequeRegalo);
	}
}
