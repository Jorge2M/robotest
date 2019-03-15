package com.mng.robotest.test80.mango.test.stpv.manto;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestionarClientes;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestionarClientes.TypeThirdButton;


public class PageGestionarClientesStpV {



	public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
		String descripValidac = 
			"1) Estamos en la página \"" + PageGestionarClientes.titulo + " \"<br>" + 
			"2) Aparece el apartado de \"Buscar clientes\"<br>" +
			"3) Aparece el apartado de \"Tratar clientes\"";
		datosStep.setNOKstateByDefault();
		ChecksResult listVals = ChecksResult.getNew(datosStep);
		try {
			if (!PageGestionarClientes.isPage(dFTest.driver)) {
				listVals.add(1, State.Defect);
			}
			if (!PageGestionarClientes.isVisibleFormBuscarClientes(dFTest.driver)) {
				listVals.add(2, State.Defect);
			}
			if (!PageGestionarClientes.isVisibleFormTratarClientes(dFTest.driver)) {
				listVals.add(3, State.Defect);
			}

			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }
	}

	public static void inputDniAndClickBuscar(String dni, DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep       (
			"Introducimos el DNI <b>" + dni + "</b> y pulsamos el botón \"Buscar\"", 
			"Aparece una lista de clientes válida");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        datosStep.setSaveImagePage(SaveWhen.Always);
		int waitSeconds = 20;
		try {
			PageGestionarClientes.inputDniAndClickBuscarButton(dni, waitSeconds, dFTest.driver);
			//PageGestionarClientes.inputDni(dni, dFTest.driver);
			//PageGestionarClientes.clickBuscarButtonAndWaitSeconds(waitSeconds, dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { StepAspect.storeDataAfterStep(datosStep); }        

		int maxSecondsToWait = 1;
		String descripValidac = 
				"1) Se muestra la tabla de información<br>" +
				"2) Aparece el DNI <b>" + dni + "</b> en la tabla<br>" +
				"3) Aparece el botón de Alta o Baja (los esperamos un máximo de " + maxSecondsToWait + " segundos)";
		datosStep.setNOKstateByDefault();
		ChecksResult listVals = ChecksResult.getNew(datosStep);
		try {
			if (!PageGestionarClientes.isVisibleTablaInformacion(dFTest.driver)) {
				listVals.add(1, State.Defect);
			}
			if (!PageGestionarClientes.getDniTabla(dni, dFTest.driver)) {
				listVals.add(2, State.Defect);            
			}
			if (!PageGestionarClientes.isVisibleThirdButtonUntil(TypeThirdButton.Baja, maxSecondsToWait, dFTest.driver) &&
				!PageGestionarClientes.isVisibleThirdButtonUntil(TypeThirdButton.Alta, maxSecondsToWait, dFTest.driver)) {
				listVals.add(3, State.Defect);
			}

			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }
	}




	public static void clickThirdButton(DataFmwkTest dFTest) throws Exception {
		TypeThirdButton typeButton = PageGestionarClientes.getTypeThirdButton(dFTest.driver);	    
		DatosStep datosStep = new DatosStep       (
				"Tras haber introducido un DNI y haber dado al botón \"Buscar\", damos click al botón \"" + typeButton + "\"", 
				"Aparece el mensaje correspondiente y el botón Alta");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
		int waitSeconds = 3;
		try {
			PageGestionarClientes.clickThirdButtonAndWaitSeconds(typeButton, waitSeconds, dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { StepAspect.storeDataAfterStep(datosStep); }        

		int maxSecondsToWait = 2;
		String descripValidac = 
			"1) Aparece el mensaje \""+ typeButton.getMensaje() +"\"<br>" +
			"2) Aparece el botón \"Alta\" (lo esperamos hasta " + maxSecondsToWait + " segundos)";
		datosStep.setNOKstateByDefault();
		ChecksResult listVals = ChecksResult.getNew(datosStep);
		try {
			if (!PageGestionarClientes.isVisibleMensajeClickThirdButton(typeButton, dFTest.driver)) {
				listVals.add(1, State.Defect);
			}
			TypeThirdButton buttonExpected = typeButton.buttonExpectedAfterClick();
			if (!PageGestionarClientes.isVisibleThirdButtonUntil(buttonExpected, maxSecondsToWait, dFTest.driver)) {
				listVals.add(2, State.Defect);            
			}

			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }
	}

	public static void clickDetallesButton(String dni, DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep       (
			"Tras haber introducido un DNI y haber dado al botón \"Buscar\", damos click al botón \"Detalles\"", 
			"Muestra los detalles del cliente correctamente");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
		String idCliente;
		int waitSeconds = 3;
		try {
			idCliente = PageGestionarClientes.getIdClienteTablaFromDni(dni, dFTest.driver);
			PageGestionarClientes.clickDetallesButtonAndWaitSeconds(waitSeconds, dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { StepAspect.storeDataAfterStep(datosStep); }        

		String descripValidac = 
			"1) Aparece el id del cliente \""+ idCliente +"\"<br>" +
			"2) Aparece el dni del cliente \""+ dni +"\"";
		datosStep.setNOKstateByDefault();
		ChecksResult listVals = ChecksResult.getNew(datosStep);
		try {
			if (!PageGestionarClientes.isVisibleIdClienteClickDetallesButton(idCliente, dFTest.driver)) {
				listVals.add(1, State.Defect);
			}
			if (!PageGestionarClientes.isVisibleDniClickDetallesButton(dni, dFTest.driver)) {
				listVals.add(2, State.Defect);            
			}

			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }
	}
}
