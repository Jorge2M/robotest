package com.mng.robotest.test80.mango.test.stpv.manto;

import com.mng.robotest.test80.arq.annotations.validation.Validation;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorCheques;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorCheques.ButtonsCheque;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorCheques.TablaCheque;

@SuppressWarnings("javadoc")
public class PageGestorChequesStpV {

	public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
		validatePage(PageGestorCheques.titulo, datosStep, dFTest.driver);
		validateButtons(datosStep, dFTest.driver);
	}

	@Validation(
			description="1) Estamos en la página \"#{titulo} \"",
			level=State.Defect)
	public static boolean validatePage(String titulo, DatosStep datosStep, WebDriver driver) {
		return (PageGestorCheques.isPage(driver));
	}

	public static void inputMailAndClickCorreoCliente(String mail, DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep       (
			"Introducimos el email <b>" + mail + "</b> y damos click al botón \"Correo del cliente\"", 
			"Muestra los cheques asociados al mail correctamente");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
		try {
			PageGestorCheques.inputMailAndClickCorreoReceptorButton(mail, dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		int numPedidosEsther = 100;
		validateInitData(numPedidosEsther, mail, datosStep, dFTest.driver);
	}

	@Validation
	public static ListResultValidation validateInitData(int numPedidos, String mail, DatosStep datosStep, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew(datosStep);
		validations.add(
				"Aparecen más de \"" + numPedidos + "\" pedidos<br>",
				PageGestorCheques.comprobarNumeroPedidos(numPedidos, driver), State.Defect);
		validations.add(
				"La columna correo de la primera línea es \""+ mail +"\"",
				PageGestorCheques.isMailCorrecto(mail, driver), State.Defect);
		return validations;
	}

	public static void clickPedido(int numFila, String cheque, String mail, DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep       (
			"Damos click al pedido de la " + numFila + "a fila", 
			"Muestra la página de detalles del pedido");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
		String pedido;
		try {
			pedido = PageGestorCheques.clickPedido(numFila, mail, dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		validateDetailsCheques(pedido, mail, datosStep, dFTest.driver);

		validateDataFromCheque (datosStep, dFTest);

		volverCheques(dFTest);

		inputCheque(cheque, dFTest);

		chequeDetails(dFTest);
	}

	@Validation
	public static ListResultValidation validateDetailsCheques(String pedido, String mail, DatosStep datosStep, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew(datosStep);
		validations.add(
				"Aparece la página de" + PageGestorCheques.tituloDetalles + "<br>",
				PageGestorCheques.isPageDetalles(driver), State.Defect);
		validations.add(
				"Como email del apartado \"Cheque número\" aparece" + mail + "<br>",
				PageGestorCheques.comprobarMailDetallesCheque(mail, driver), State.Defect);
		validations.add("Como id del pedido aparece\"" + pedido  + "\"",
				PageGestorCheques.comprobarPedidoDetallesCheque(pedido, driver), State.Defect);
		return validations;
	}

	public static void volverCheques (DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep       (
				"Damos click a <b>Volver a cheques</b>",
				"Muestra la página de información sobre los cheques");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
		try {
			PageGestorCheques.clickAndWait(ButtonsCheque.volverCheques, dFTest.driver);
			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		validateButtons(datosStep, dFTest.driver);
	}

	@Validation
	public static ListResultValidation validateButtons(DatosStep datosStep, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew(datosStep);
		int maxSecondsWait = 3;
		validations.add(
				"Existe el botón de <b>Id del pedido</b><br>",
				PageGestorCheques.isElementInStateUntil(ButtonsCheque.idPedido, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
				"Existe el botón de <b>Numero de cheque</b><br>",
				PageGestorCheques.isElementInStateUntil(ButtonsCheque.numCheque, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
				"Existe el botón de <b>Id de compra</b><br>",
				PageGestorCheques.isElementInStateUntil(ButtonsCheque.idCompra, StateElem.Clickable, 3, driver), State.Defect);
		validations.add(
				"Existe el botón de <b>Correo del receptor</b><br>",
				PageGestorCheques.isElementInStateUntil(ButtonsCheque.correoReceptor, StateElem.Clickable, 3, driver), State.Defect);
		validations.add(
				"Existe el botón de <b>Correo del comprador</b>",
				PageGestorCheques.isElementInStateUntil(ButtonsCheque.correoComprador, StateElem.Clickable, 3, driver), State.Defect);
		return validations;
	}

	@Validation
	public static ListResultValidation validateSecondDataCheque(DatosStep datosStep, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew(datosStep);
		int maxSecondsWait = 3;
		validations.add(
				"En la tabla activo existe un apartado para <b>ACTIVO</b><br>",
				PageGestorCheques.isElementInStateUntil(TablaCheque.activo, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
				"En la tabla activo existe un apartado para <b>CHARGEBACK</b><br>",
				PageGestorCheques.isElementInStateUntil(TablaCheque.chargeBack, StateElem.Present, maxSecondsWait, driver), State.Defect);
		return validations;
	}

	@Validation(
			description="1) Aparece el botón para <b>Volver a cheques</b>",
			level=State.Defect)
	public static boolean validateReturnCheques(DatosStep datosStep, WebDriver driver) {
		return (PageGestorCheques.isElementInStateUntil(ButtonsCheque.volverCheques, StateElem.Present, 3, driver));
	}

	@Validation
	public static ListResultValidation validateThirdDataCheque(DatosStep datosStep, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew(datosStep);
		int maxSecondsWait = 3;
		validations.add(
				"En la tabla divisa existe un apartado para <b>DIVISA</b><br>",
				PageGestorCheques.isElementInStateUntil(TablaCheque.divisa, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
				"En la tabla divisa existe un apartado para <b>VALOR TOTAL</b><br>",
				PageGestorCheques.isElementInStateUntil(TablaCheque.valorTotal, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
				"En la tabla divisa existe un apartado para <b>SALDO</b><br>",
				PageGestorCheques.isElementInStateUntil(TablaCheque.saldo, StateElem.Clickable, maxSecondsWait, driver), State.Defect);
		validations.add(
				"En la tabla divisa existe un apartado para <b>FECHA DE COMPRA</b><br>",
				PageGestorCheques.isElementInStateUntil(TablaCheque.fechaCompra, StateElem.Clickable, maxSecondsWait, driver), State.Defect);
		validations.add(
				"En la tabla divisa existe un apartado para <b>VALIDEZ</b>",
				PageGestorCheques.isElementInStateUntil(TablaCheque.validez, StateElem.Clickable, maxSecondsWait, driver), State.Defect);
		return validations;
	}

	@Validation
	public static ListResultValidation validatePedidosData(DatosStep datosStep, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew(datosStep);
		int maxSecondsWait = 3;
		validations.add(
				"En la tabla pedidos realizados existe un apartado para <b>Id</b><br>",
				PageGestorCheques.isElementInStateUntil(TablaCheque.idPedidos, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
				"En la tabla pedidos realizados existe un apartado para <b>Fecha</b><br>",
				PageGestorCheques.isElementInStateUntil(TablaCheque.fechaPedidos, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
				"En la tabla pedidos realizados existe un apartado para <b>Total</b><br>",
				PageGestorCheques.isElementInStateUntil(TablaCheque.totalPedidos, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
				"En la tabla pedidos realizados existe un apartado para <b>Usuario</b><br>",
				PageGestorCheques.isElementInStateUntil(TablaCheque.usuarioPedidos, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
				"En la tabla pedidos realizados existe un apartado para <b>Accion</b>",
				PageGestorCheques.isElementInStateUntil(TablaCheque.activoPedidos, StateElem.Present, maxSecondsWait, driver), State.Defect);
		return validations;
	}

	@Validation
	public static ListResultValidation validateButtonsDataCheque(DatosStep datosStep, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew(datosStep);
		int maxSecondsWait = 3;
		validations.add(
				"Existe el boton para <b>Modificar</b><br>",
				PageGestorCheques.isElementInStateUntil(ButtonsCheque.modificar, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
				"Existe el boton para <b>Añadir</b><br>",
				PageGestorCheques.isElementInStateUntil(ButtonsCheque.add, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
				"Existe el boton para <b>Reenviar</b><br>",
				PageGestorCheques.isElementInStateUntil(ButtonsCheque.reenviar, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
				"Existe el boton para <b>Editar</b><br>",
				PageGestorCheques.isElementInStateUntil(ButtonsCheque.editar, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
				"Existe el boton para <b>Desactivar</b>",
				PageGestorCheques.isElementInStateUntil(ButtonsCheque.desactivar, StateElem.Present, maxSecondsWait, driver), State.Defect);
		return validations;
	}

	public static void validateDataFromCheque (DatosStep datosStep, DataFmwkTest dFTest) {
		validateInitDataCheque(datosStep, dFTest.driver);
		validateSecondDataCheque(datosStep, dFTest.driver);
		validateThirdDataCheque(datosStep, dFTest.driver);
		validatePedidosData(datosStep, dFTest.driver);
		validateButtonsDataCheque(datosStep, dFTest.driver);
		validateReturnCheques(datosStep, dFTest.driver);
	}

	public static void inputCheque (String cheque, DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep 	 	(
			"Introducimos el numero de cheque con valor: <b>" + cheque + "</b>" ,
			"Una vez hemos buscado dicho numero, nos aparece una tabla con información");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
		try {
			PageGestorCheques.inputChequeAndConfirm(cheque, dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		} 
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		validateDataCheque(cheque, datosStep, dFTest.driver);
	}

	@Validation(
			description="1) Aparece el numero de cheque <b>#{cheque}</b> en la tabla de datos",
			level=State.Defect)
	public static boolean validateDataCheque(String cheque, DatosStep datosStep, WebDriver driver) {
		return (!PageGestorCheques.isElementInStateUntil(ButtonsCheque.volverCheques, StateElem.Present, 3, driver));
	}

	public static void chequeDetails (DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep		(
			"Accedemos al numero de cheque",
			"Aparece toda la información de dicho cheque pero no un email");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
		try {
			PageGestorCheques.clickAndWait(ButtonsCheque.chequeData, dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		validateEmptyMail(datosStep, dFTest.driver);

		validateDataFromCheque(datosStep, dFTest);
	}

	@Validation(
			description="1) El dato de <b>mail</b> corresponde a un registro <b>vacio</b>",
			level=State.Defect)
	public static boolean validateEmptyMail(DatosStep datosStep, WebDriver driver) {
		return (!PageGestorCheques.isMailCorrecto("", driver));
	}

	@Validation
	public static ListResultValidation validateInitDataCheque(DatosStep datosStep, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew(datosStep);
		int maxSecondsWait = 3;
		validations.add(
				"Existe la tabla que contiene <b>Activo</b><br>",
				PageGestorCheques.isElementInStateUntil(TablaCheque.activo, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
				"Existe la tabla que contiene <b>Divisa</b><br>",
				PageGestorCheques.isElementInStateUntil(TablaCheque.divisa, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
				"Existe la tabla que contiene <b>Pedidos Realizados</b><br>",
				PageGestorCheques.isElementInStateUntil(TablaCheque.pedidosRealizados, StateElem.Present, maxSecondsWait, driver), State.Defect);
		validations.add(
				"Existe la tabla que contiene <b>Pedidos Eliminados</b>",
				PageGestorCheques.isElementInStateUntil(TablaCheque.pedidosEliminados, StateElem.Present, maxSecondsWait, driver), State.Defect);
		return validations;
	}
}
