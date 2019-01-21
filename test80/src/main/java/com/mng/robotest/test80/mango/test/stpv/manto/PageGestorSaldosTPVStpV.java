package com.mng.robotest.test80.mango.test.stpv.manto;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorSaldosTPV;

@SuppressWarnings("javadoc")
public class PageGestorSaldosTPVStpV {



	public static void validateIsPage(datosStep datosStep, DataFmwkTest dFTest) {
		String descripValidac = 
				"1) Estamos en la página \"" + PageGestorSaldosTPV.titulo + " \"<br>" +
				"2) Aparece el input de fecha de TPV";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!PageGestorSaldosTPV.isPage(dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);
			//2)
			if (!PageGestorSaldosTPV.isVisibleTPVInput(dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}
	
	
	
	
	
	public static void searchValidTPV(String tpv, DataFmwkTest dFTest) throws Exception {
		datosStep datosStep = new datosStep       (
				"Introducimos una TPV válida y damos click a \"Consultar Saldos\"", 
				"Muestra la tabla de saldos con el ID de la TPV en ella");
		datosStep.setGrab_ErrorPageIfProblem(false);

		try {
			PageGestorSaldosTPV.insertTPVAndClickConsultarSaldos(tpv, dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		String descripValidac = 
				"1) Aparece la tabla de saldos<br>" +
				"2) Aparece el ID de la TPV \"" + tpv + "\" en la tabla";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!PageGestorSaldosTPV.isTablaSaldosVisible(dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);
			//2)
			if (!PageGestorSaldosTPV.isTPVIDVisible(tpv, dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);
          
			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}

	
	
	public static void searchUnvalidTPV(String tpv, DataFmwkTest dFTest) throws Exception {
		datosStep datosStep = new datosStep       (
				"Introducimos una TPV no válida y damos click a \"Consultar Saldos\"", 
				"Muestra el mensaje conforme la TPV no existe");
		datosStep.setGrab_ErrorPageIfProblem(false);

		try {
			PageGestorSaldosTPV.insertTPVAndClickConsultarSaldos(tpv, dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		String descripValidac = 
				"1) Aparece el mensaje \"La tpv seleccionada no existe\"";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!PageGestorSaldosTPV.isUnvalidTPVMessageVisible(dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}

	
	
	
	


}
