package com.mng.robotest.test80.mango.test.stpv.manto;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestionarClientes;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestionarClientes.TypeThirdButton;

@SuppressWarnings("javadoc")
public class PageGestionarClientesStpV {



	public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
		String descripValidac = 
				"1) Estamos en la página \"" + PageGestionarClientes.titulo + " \"<br>" + 
						"2) Aparece el apartado de \"Buscar clientes\"<br>" +
						"3) Aparece el apartado de \"Tratar clientes\"";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!PageGestionarClientes.isPage(dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);
			//2)
			if (!PageGestionarClientes.isVisibleFormBuscarClientes(dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);
			//3)
			if (!PageGestionarClientes.isVisibleFormTratarClientes(dFTest.driver))
				fmwkTest.addValidation(3, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}




	public static void inputDniAndClickBuscar(String dni, DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep       (
				"Introducimos el DNI <b>" + dni + "</b> y pulsamos el botón \"Buscar\"", 
				"Aparece una lista de clientes válida");
		datosStep.setGrab_ErrorPageIfProblem(false);
		datosStep.setGrabImage(true);
		int waitSeconds = 20;
		try {
			PageGestionarClientes.inputDniAndClickBuscarButton(dni, waitSeconds, dFTest.driver);
			//PageGestionarClientes.inputDni(dni, dFTest.driver);
			//PageGestionarClientes.clickBuscarButtonAndWaitSeconds(waitSeconds, dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }        

		int maxSecondsToWait = 1;
		String descripValidac = 
				"1) Se muestra la tabla de información<br>" +
				"2) Aparece el DNI <b>" + dni + "</b> en la tabla<br>" +
				"3) Aparece el botón de Alta o Baja (los esperamos un máximo de " + maxSecondsToWait + " segundos)";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!PageGestionarClientes.isVisibleTablaInformacion(dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);
			//2) 
			if (!PageGestionarClientes.getDniTabla(dni, dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);            
			//3)
			if (!PageGestionarClientes.isVisibleThirdButtonUntil(TypeThirdButton.Baja, maxSecondsToWait, dFTest.driver) &&
					!PageGestionarClientes.isVisibleThirdButtonUntil(TypeThirdButton.Alta, maxSecondsToWait, dFTest.driver))
				fmwkTest.addValidation(3, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}




	public static void clickThirdButton(DataFmwkTest dFTest) throws Exception {
		TypeThirdButton typeButton = PageGestionarClientes.getTypeThirdButton(dFTest.driver);	    
		DatosStep datosStep = new DatosStep       (
				"Tras haber introducido un DNI y haber dado al botón \"Buscar\", damos click al botón \"" + typeButton + "\"", 
				"Aparece el mensaje correspondiente y el botón Alta");
		datosStep.setGrab_ErrorPageIfProblem(false);

		int waitSeconds = 3;
		try {
			PageGestionarClientes.clickThirdButtonAndWaitSeconds(typeButton, waitSeconds, dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }        

		int maxSecondsToWait = 2;
		String descripValidac = 
				"1) Aparece el mensaje \""+ typeButton.getMensaje() +"\"<br>" +
						"2) Aparece el botón \"Alta\" (lo esperamos hasta " + maxSecondsToWait + " segundos)";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!PageGestionarClientes.isVisibleMensajeClickThirdButton(typeButton, dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);
			//2) 
			TypeThirdButton buttonExpected = typeButton.buttonExpectedAfterClick();
			if (!PageGestionarClientes.isVisibleThirdButtonUntil(buttonExpected, maxSecondsToWait, dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);            

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}




	public static void clickDetallesButton(String dni, DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep       (
				"Tras haber introducido un DNI y haber dado al botón \"Buscar\", damos click al botón \"Detalles\"", 
				"Muestra los detalles del cliente correctamente");
		datosStep.setGrab_ErrorPageIfProblem(false);

		String idCliente;
		int waitSeconds = 3;
		try {
			idCliente = PageGestionarClientes.getIdClienteTablaFromDni(dni, dFTest.driver);
			PageGestionarClientes.clickDetallesButtonAndWaitSeconds(waitSeconds, dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }        

		String descripValidac = 
				"1) Aparece el id del cliente \""+ idCliente +"\"<br>" +
				"2) Aparece el dni del cliente \""+ dni +"\"";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!PageGestionarClientes.isVisibleIdClienteClickDetallesButton(idCliente, dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);
			//2) 
			if (!PageGestionarClientes.isVisibleDniClickDetallesButton(dni, dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);            

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}
}
