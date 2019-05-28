package com.mng.robotest.test80.mango.test.stpv.manto;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.arq.webdriverwrapper.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorCheques;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorCheques.ButtonsCheque;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorCheques.TablaCheque;

public class PageGestorChequesStpV {

	public static void validateIsPage(WebDriver driver) {
		validatePage(driver);
		validateButtons(driver);
	}

	@Validation(
		description="1) Estamos en la página Gestord de Cheques",
		level=State.Defect)
	public static boolean validatePage(WebDriver driver) {
		return (PageGestorCheques.isPage(driver));
	}

	@Step(
		description="Introducimos el email <b>#{mail}</b> y damos click al botón \"Correo del cliente\"",
		expected="Muestra los cheques asociados al mail correctamente",
		saveErrorPage=SaveWhen.Never)
	public static void inputMailAndClickCorreoCliente(String mail, WebDriver driver) throws Exception {
		PageGestorCheques.inputMailAndClickCorreoReceptorButton(mail, driver);
		validateInitData(100, mail, driver);
	}

	@Validation
	public static ChecksResult validateInitData(int numPedidos, String mail, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		validations.add(
			"Aparecen más de \"" + numPedidos + "\" pedidos",
			PageGestorCheques.comprobarNumeroPedidos(numPedidos, driver), State.Defect);
		validations.add(
			"La columna correo de la primera línea es \""+ mail +"\"",
			PageGestorCheques.isMailCorrecto(mail, driver), State.Defect);
		return validations;
	}

	@Step(
		description="Damos click al pedido de la #{numFila}a fila",
		expected="Muestra la página de detalles del pedido",
		saveErrorPage=SaveWhen.Never)
	public static void clickPedido(int numFila, String mail, WebDriver driver) throws Exception {
		String pedido;
		pedido = PageGestorCheques.clickPedido(numFila, mail, driver);
		validateDetailsCheques(pedido, mail, driver);
		validateDataFromCheque (driver);
	}

	@Validation
	public static ChecksResult validateDetailsCheques(String pedido, String mail, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		validations.add(
			"Aparece la página de" + PageGestorCheques.tituloDetalles,
			PageGestorCheques.isPageDetalles(driver), State.Defect);
		validations.add(
			"Como email del apartado \"Cheque número\" aparece" + mail,
			PageGestorCheques.comprobarMailDetallesCheque(mail, driver), State.Defect);
		validations.add("Como id del pedido aparece\"" + pedido  + "\"",
			PageGestorCheques.comprobarPedidoDetallesCheque(pedido, driver), State.Defect);
		return validations;
	}

	@Step(
		description="Damos click a <b>Volver a cheques</b>",
		expected="Muestra la página de información sobre los cheques",
		saveErrorPage=SaveWhen.Never)
	public static void volverCheques (WebDriver driver) throws Exception {
		PageGestorCheques.clickAndWait(ButtonsCheque.volverCheques, driver);
		validateButtons(driver);
	}

	@Validation
	public static ChecksResult validateButtons(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"Existe el botón de <b>Id del pedido</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.idPedido, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe el botón de <b>Numero de cheque</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.numCheque, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe el botón de <b>Id de compra</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.idCompra, StateElem.Clickable, 3, driver), State.Defect);
		validations.add(
			"Existe el botón de <b>Correo del receptor</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.correoReceptor, StateElem.Clickable, 3, driver), State.Defect);
		validations.add(
			"Existe el botón de <b>Correo del comprador</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.correoComprador, StateElem.Clickable, 3, driver), State.Defect);
		return validations;
	}

	@Validation
	public static ChecksResult validateSecondDataCheque(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"En la tabla activo existe un apartado para <b>ACTIVO</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.activo, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla activo existe un apartado para <b>CHARGEBACK</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.chargeBack, StateElem.Present, maxSecondsWait, driver), State.Defect);
		return validations;
	}

	@Validation(
		description="1) Aparece el botón para <b>Volver a cheques</b>",
		level=State.Defect)
	public static boolean validateReturnCheques(WebDriver driver) {
		return (PageGestorCheques.isElementInStateUntil(ButtonsCheque.volverCheques, StateElem.Present, 3, driver));
	}

	@Validation
	public static ChecksResult validateThirdDataCheque(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"En la tabla divisa existe un apartado para <b>DIVISA</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.divisa, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla divisa existe un apartado para <b>VALOR TOTAL</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.valorTotal, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla divisa existe un apartado para <b>SALDO</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.saldo, StateElem.Clickable, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla divisa existe un apartado para <b>FECHA DE COMPRA</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.fechaCompra, StateElem.Clickable, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla divisa existe un apartado para <b>VALIDEZ</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.validez, StateElem.Clickable, maxSecondsWait, driver), State.Defect);
		return validations;
	}

	@Validation
	public static ChecksResult validatePedidosData(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"En la tabla pedidos realizados existe un apartado para <b>Id</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.idPedidos, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla pedidos realizados existe un apartado para <b>Fecha</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.fechaPedidos, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla pedidos realizados existe un apartado para <b>Total</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.totalPedidos, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla pedidos realizados existe un apartado para <b>Usuario</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.usuarioPedidos, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"En la tabla pedidos realizados existe un apartado para <b>Accion</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.activoPedidos, StateElem.Present, maxSecondsWait, driver), State.Defect);
		return validations;
	}

	@Validation
	public static ChecksResult validateButtonsDataCheque(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"Existe el boton para <b>Modificar</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.modificar, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe el boton para <b>Añadir</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.add, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe el boton para <b>Reenviar</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.reenviar, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe el boton para <b>Editar</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.editar, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe el boton para <b>Desactivar</b>",
			PageGestorCheques.isElementInStateUntil(ButtonsCheque.desactivar, StateElem.Present, maxSecondsWait, driver), State.Defect);
		return validations;
	}

	public static void validateDataFromCheque (WebDriver driver) {
		validateInitDataCheque(driver);
		validateSecondDataCheque(driver);
		validateThirdDataCheque(driver);
		validatePedidosData(driver);
		validateButtonsDataCheque(driver);
		validateReturnCheques(driver);
	}

	@Step(
		description="Introducimos el numero de cheque con valor: <b>#{cheque}</b>",
		expected="Muestra los cheques asociados al mail correctamente",
		saveErrorPage=SaveWhen.Never)
	public static void inputCheque (String cheque, WebDriver driver) throws Exception {
		PageGestorCheques.inputChequeAndConfirm(cheque, driver);
		validateDataCheque(cheque, driver);
	}

	@Validation(
		description="1) Aparece el numero de cheque <b>#{cheque}</b> en la tabla de datos",
		level=State.Defect)
	public static boolean validateDataCheque(String cheque, WebDriver driver) {
		return (!PageGestorCheques.isElementInStateUntil(ButtonsCheque.volverCheques, StateElem.Present, 3, driver));
	}

	@Step(
		description="Accedemos al numero de cheque",
		expected="Aparece toda la información de dicho cheque pero no un email",
		saveErrorPage=SaveWhen.Never)
	public static void chequeDetails (WebDriver driver) throws Exception {
		PageGestorCheques.clickAndWait(ButtonsCheque.chequeData, driver);
		validateEmptyMail(driver);
		validateDataFromCheque(driver);
	}

	@Validation(
		description="1) El dato de <b>mail</b> corresponde a un registro <b>vacio</b>",
		level=State.Defect)
	public static boolean validateEmptyMail(WebDriver driver) {
		return (!PageGestorCheques.isMailCorrecto("", driver));
	}

	@Validation
	public static ChecksResult validateInitDataCheque( WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"Existe la tabla que contiene <b>Activo</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.activo, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe la tabla que contiene <b>Divisa</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.divisa, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe la tabla que contiene <b>Pedidos Realizados</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.pedidosRealizados, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
			"Existe la tabla que contiene <b>Pedidos Eliminados</b>",
			PageGestorCheques.isElementInStateUntil(TablaCheque.pedidosEliminados, StateElem.Present, maxSecondsWait, driver), State.Defect);
		return validations;
	}
}
