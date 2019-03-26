package com.mng.robotest.test80.mango.test.stpv.manto;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorSaldosTPV;

public class PageGestorSaldosTPVStpV {

	@Validation
	public static ChecksResult validateIsPage(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Estamos en la página " + PageGestorSaldosTPV.titulo + " <br>",
			PageGestorSaldosTPV.isPage(driver), State.Defect);
	 	validations.add(
			"Aparece el input de fecha de TPV",
			PageGestorSaldosTPV.isVisibleTPVInput(driver), State.Defect);
	 	return validations;
	}
	
	public static void searchValidTPV(String tpv, DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep       (
			"Introducimos una TPV válida y damos click a \"Consultar Saldos\"", 
			"Muestra la tabla de saldos con el ID de la TPV en ella");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
		try {
			PageGestorSaldosTPV.insertTPVAndClickConsultarSaldos(tpv, dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { StepAspect.storeDataAfterStep(datosStep); }

		String descripValidac = 
			"1) Aparece la tabla de saldos<br>" +
			"2) Aparece el ID de la TPV \"" + tpv + "\" en la tabla";
		datosStep.setNOKstateByDefault();
		ChecksResult listVals = ChecksResult.getNew(datosStep);
		try {
			if (!PageGestorSaldosTPV.isTablaSaldosVisible(dFTest.driver)) {
				listVals.add(1, State.Defect);
			}
			if (!PageGestorSaldosTPV.isTPVIDVisible(tpv, dFTest.driver)) {
				listVals.add(2, State.Defect);
			}
          
			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }
	}

	
	
	public static void searchUnvalidTPV(String tpv, DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep       (
				"Introducimos una TPV no válida y damos click a \"Consultar Saldos\"", 
				"Muestra el mensaje conforme la TPV no existe");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
		try {
			PageGestorSaldosTPV.insertTPVAndClickConsultarSaldos(tpv, dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { StepAspect.storeDataAfterStep(datosStep); }

		String descripValidac = 
			"1) Aparece el mensaje \"La tpv seleccionada no existe\"";
		datosStep.setNOKstateByDefault();
		ChecksResult listVals = ChecksResult.getNew(datosStep);
		try {
			if (!PageGestorSaldosTPV.isUnvalidTPVMessageVisible(dFTest.driver)) {
				listVals.add(1, State.Defect);
			}

			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }
	}
}
