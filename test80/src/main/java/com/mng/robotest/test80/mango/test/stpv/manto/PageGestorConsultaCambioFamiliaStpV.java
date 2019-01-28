package com.mng.robotest.test80.mango.test.stpv.manto;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorConsultaCambioFamilia;

@SuppressWarnings("javadoc")
public class PageGestorConsultaCambioFamiliaStpV {



	public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
		String descripValidac = 
				"1) Estamos en la página \"" + PageGestorConsultaCambioFamilia.titulo + " \"<br>" +
				"2) Aparece la tabla de \"Consulta\"" +
				"3) El botón \"Consulta está\" \"disabled\"";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!PageGestorConsultaCambioFamilia.isPage(dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);
			//2)
			if (!PageGestorConsultaCambioFamilia.isVisibleConsultaTable(dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);
			//3)
			if (!PageGestorConsultaCambioFamilia.isDisabledConsultaButton(dFTest.driver))
				fmwkTest.addValidation(3, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}
	
	
	
	
	
	public static void selectAccesoriosAndClickConsultaPorFamiliaButton(DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep       (
				"Buscamos productos por la familia \"Accesorios\" ", 
				"Muestra la tabla con productos que corresponden con esta familia");
		datosStep.setGrab_ErrorPageIfProblem(false);

		try {
			PageGestorConsultaCambioFamilia.selectAccesoriosAndClickConsultaPorFamiliaButton(dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		String descripValidac = 
				"1) Aparece la tabla con los productos<br>" +
				"2) El campo de la tabla \"Traducción familia principal\" de la primera fila contiene el atributo \"Accesorios\"";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!PageGestorConsultaCambioFamilia.isTablaProductosVisible(dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);
			//2)
			if (!PageGestorConsultaCambioFamilia.checkFirstRowProductIsRight(dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);
          
			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}

	
	
	public static void clickCambioFamiliaButton(DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep       (
				"Damos click al botón \"Cambio Familia\"", 
				"Muestra la página que permite gestionar los cambios de familia");
		datosStep.setGrab_ErrorPageIfProblem(false);

		try {
			PageGestorConsultaCambioFamilia.clickCambioFamiliaButton(dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		String descripValidac = 
				"1) Aparece la tabla con las opciones para los cambios de familia<br>" + 
				"2) El botón \"Consulta\" ya no está \"disabled\"";
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!PageGestorConsultaCambioFamilia.isTablaCambioFamiliaVisible(dFTest.driver))
				fmwkTest.addValidation(1, State.Defect, listVals);
			//2)
			if (PageGestorConsultaCambioFamilia.isDisabledConsultaButton(dFTest.driver))
				fmwkTest.addValidation(2, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} 
		finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
	}

	
	
	
	


}
