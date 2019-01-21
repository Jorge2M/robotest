package com.mng.robotest.test80.mango.test.stpv.manto;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorCheques;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorCheques.ButtonsCheque;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorCheques.TablaCheque;

@SuppressWarnings("javadoc")
public class PageGestorChequesStpV {

	public static void validateIsPage(datosStep datosStep, DataFmwkTest dFTest) {
		String descripValidac = 
				"1) Estamos en la página \"" + PageGestorCheques.titulo + " \"";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!PageGestorCheques.isPage(dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }

		validateButtons(datosStep, dFTest);
	}

	public static void inputMailAndClickCorreoCliente(String mail, DataFmwkTest dFTest) throws Exception {
		datosStep datosStep = new datosStep       (
				"Introducimos el email <b>" + mail + "</b> y damos click al botón \"Correo del cliente\"", 
				"Muestra los cheques asociados al mail correctamente");
		datosStep.setGrab_ErrorPageIfProblem(false);

		try {
			PageGestorCheques.inputMailAndClickCorreoReceptorButton(mail, dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		int numPedidosEsther = 100;
		String descripValidac = 
				"1) Aparecen más de \"" + numPedidosEsther + "\" pedidos<br>" +
				"2) La columna correo de la primera línea es \""+ mail +"\"";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!PageGestorCheques.comprobarNumeroPedidos(numPedidosEsther, dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);
			//2) 
			if (!PageGestorCheques.isMailCorrecto(mail, dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);            

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}
	
	public static void clickPedido(int numFila, String cheque, String mail, DataFmwkTest dFTest) throws Exception {
		datosStep datosStep = new datosStep       (
				"Damos click al pedido de la " + numFila + "a fila", 
				"Muestra la página de detalles del pedido");
		datosStep.setGrab_ErrorPageIfProblem(false);

		String pedido;
		try {
			pedido = PageGestorCheques.clickPedido(numFila, mail, dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		String descripValidac = 
				"1) Aparece la página de \"" + PageGestorCheques.tituloDetalles + "\"<br>" +
				"2) Como email del apartado \"Cheque número\" aparece \"" + mail + "\"<br>" +
				"3) Como id del pedido aparece \"" + pedido  + "\"";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!PageGestorCheques.isPageDetalles(dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);
			//2)
			if (!PageGestorCheques.comprobarMailDetallesCheque(mail, dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);       
			//3)
			if (!PageGestorCheques.comprobarPedidoDetallesCheque(pedido, dFTest.driver))
				fmwkTest.addValidation(3, State.Defect, listVals); 

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }

		validateDataFromCheque (datosStep, dFTest);

		volverCheques(dFTest);

		inputCheque(cheque, dFTest);

		chequeDetails(dFTest);
	}

	public static void volverCheques (DataFmwkTest dFTest) throws Exception {
		datosStep datosStep = new datosStep       (
				"Damos click a <b>Volver a cheques</b>",
				"Muestra la página de información sobre los cheques");
		datosStep.setGrab_ErrorPageIfProblem(false);

		try {
			PageGestorCheques.clickAndWait(ButtonsCheque.volverCheques, dFTest.driver);
			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		validateButtons(datosStep, dFTest);
	}

	public static void validateButtons (datosStep datosStep, DataFmwkTest dFTest) {
		String descripValidac =
				"1) Existe el botón de <b>Id del pedido</b><br>"+
				"2) Existe el botón de <b>Numero de cheque</b><br>"+
				"3) Existe el botón de <b>Id de compra</b><br>"+
				"4) Existe el botón de <b>Correo del receptor</b><br>"+
				"5) Existe el botón de <b>Correo del comprador</b>";

		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();

			if(!PageGestorCheques.isElementInStateUntil(ButtonsCheque.idPedido, StateElem.Clickable, 3, dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(ButtonsCheque.numCheque, StateElem.Clickable, 3, dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(ButtonsCheque.idCompra, StateElem.Clickable, 3, dFTest.driver))
				fmwkTest.addValidation(3, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(ButtonsCheque.correoReceptor, StateElem.Clickable, 3, dFTest.driver))
				fmwkTest.addValidation(4, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(ButtonsCheque.correoReceptor, StateElem.Clickable, 3, dFTest.driver))
				fmwkTest.addValidation(5, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		}
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}

	public static void validateDataFromCheque (datosStep datosStep, DataFmwkTest dFTest) {
		String descripValidac =	"1) Existe la tabla que contiene <b>Activo</b><br>"+
								"2) Existe la tabla que contiene <b>Divisa</b><br>"+
								"3) Existe la tabla que contiene <b>Pedidos Realizados</b><br>"+
								"4) Existe la tabla que contiene <b>Pedidos Eliminados</b>";

		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();

			if(!PageGestorCheques.isElementInStateUntil(TablaCheque.activo, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(TablaCheque.divisa, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(TablaCheque.pedidosRealizados, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(3, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(TablaCheque.pedidosEliminados, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(4, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		}
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }

		descripValidac = "1) En la tabla activo existe un apartado para <b>ACTIVO</b><br>" +
						 "2) En la tabla activo existe un apartado para <b>CHARGEBACK</b><br>";

		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();

			if(!PageGestorCheques.isElementInStateUntil(TablaCheque.activo, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(TablaCheque.chargeBack, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		}
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }

		descripValidac = "1) En la tabla divisa existe un apartado para <b>DIVISA</b><br>"+
						 "2) En la tabla divisa existe un apartado para <b>VALOR TOTAL</b><br>"+
						 "3) En la tabla divisa existe un apartado para <b>SALDO</b><br>"+
						 "4) En la tabla divisa existe un apartado para <b>FECHA DE COMPRA</b><br>" +
						 "5) En la tabla divisa existe un apartado para <b>VALIDEZ</b>";

		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();

			if(!PageGestorCheques.isElementInStateUntil(TablaCheque.divisa, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(TablaCheque.valorTotal, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(TablaCheque.saldo, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(3, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(TablaCheque.fechaCompra, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(4, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(TablaCheque.validez, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(5, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		}
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }

		descripValidac = "1) En la tabla pedidos realizados existe un apartado para <b>Id</b><br>"+
						 "2) En la tabla pedidos realizados existe un apartado para <b>Fecha</b><br>"+
						 "3) En la tabla pedidos realizados existe un apartado para <b>Total</b><br>"+
						 "4) En la tabla pedidos realizados existe un apartado para <b>Usuario</b><br>" +
						 "5) En la tabla pedidos realizados existe un apartado para <b>Accion</b>";

		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();

			if(!PageGestorCheques.isElementInStateUntil(TablaCheque.idPedidos, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(TablaCheque.fechaPedidos, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(TablaCheque.totalPedidos, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(3, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(TablaCheque.usuarioPedidos, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(4, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(TablaCheque.activoPedidos, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(5, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		}
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }

		descripValidac = "1) Existe el boton para <b>Modificar</b><br>"+
						 "2) Existe el boton para <b>Añadir</b><br>"+
						 "3) Existe el boton para <b>Reenviar</b><br>"+
						 "4) Existe el boton para <b>Editar</b><br>" +
						 "5) Existe el boton para <b>Desactivar</b>";

		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();

			if(!PageGestorCheques.isElementInStateUntil(ButtonsCheque.modificar, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(ButtonsCheque.add, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(ButtonsCheque.reenviar, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(3, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(ButtonsCheque.editar, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(4, State.Defect, listVals);

			if(!PageGestorCheques.isElementInStateUntil(ButtonsCheque.desactivar, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(5, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		}
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }

		descripValidac = "1) Aparece el botón para <b>Volver a cheques</b>";

		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();

			if(!PageGestorCheques.isElementInStateUntil(ButtonsCheque.volverCheques, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		}
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }

	}

	public static void inputCheque (String cheque, DataFmwkTest dFTest) throws Exception {
		datosStep datosStep = new datosStep 	 	(
				"Introducimos el numero de cheque con valor: <b>" + cheque + "</b>" ,
				"Una vez hemos buscado dicho numero, nos aparece una tabla con información");
		datosStep.setGrab_ErrorPageIfProblem(false);
		try {
			PageGestorCheques.inputChequeAndConfirm(cheque, dFTest.driver);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		String descripValidac =
				"1) Aparece el numero de cheque <b>" + cheque + "</b> en la tabla de datos";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			if(!PageGestorCheques.isElementInStateUntil(ButtonsCheque.chequeData, StateElem.Present, 3, dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}

	public static void chequeDetails (DataFmwkTest dFTest) throws Exception {
		datosStep datosStep = new datosStep		(
				"Accedemos al numero de cheque",
				"Aparece toda la información de dicho cheque pero no un email");
		datosStep.setGrab_ErrorPageIfProblem(false);
		try {
			PageGestorCheques.clickAndWait(ButtonsCheque.chequeData, dFTest.driver);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		String descripValidac =
				"1) El dato de <b>mail</b> corresponde a un registro <b>vacio</b>";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);

		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (PageGestorCheques.isMailCorrecto("", dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }

		validateDataFromCheque(datosStep, dFTest);
	}
}
