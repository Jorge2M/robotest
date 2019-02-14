package com.mng.robotest.test80.mango.test.stpv.manto;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorConsultaCambioFamilia;

@SuppressWarnings("javadoc")
public class PageGestorConsultaCambioFamiliaStpV {



	public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
		String descripValidac = 
			"1) Estamos en la página \"" + PageGestorConsultaCambioFamilia.titulo + " \"<br>" +
			"2) Aparece la tabla de \"Consulta\"" +
			"3) El botón \"Consulta está\" \"disabled\"";
		datosStep.setNOKstateByDefault();
		ListResultValidation listVals = ListResultValidation.getNew(datosStep);
		try {
			if (!PageGestorConsultaCambioFamilia.isPage(dFTest.driver)) {
				listVals.add(1, State.Defect);
			}
			if (!PageGestorConsultaCambioFamilia.isVisibleConsultaTable(dFTest.driver)) {
				listVals.add(2, State.Defect);
			}
			if (!PageGestorConsultaCambioFamilia.isDisabledConsultaButton(dFTest.driver)) {
				listVals.add(3, State.Defect);
			}

			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }
	}
	
	public static void selectAccesoriosAndClickConsultaPorFamiliaButton(DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep       (
			"Buscamos productos por la familia \"Accesorios\" ", 
			"Muestra la tabla con productos que corresponden con esta familia");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
		try {
			PageGestorConsultaCambioFamilia.selectAccesoriosAndClickConsultaPorFamiliaButton(dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { fmwkTest.grabStep(datosStep, dFTest); }

		String descripValidac = 
			"1) Aparece la tabla con los productos<br>" +
			"2) El campo de la tabla \"Traducción familia principal\" de la primera fila contiene el atributo \"Accesorios\"";
		datosStep.setNOKstateByDefault();
		ListResultValidation listVals = ListResultValidation.getNew(datosStep);
		try {
			if (!PageGestorConsultaCambioFamilia.isTablaProductosVisible(dFTest.driver)) {
				listVals.add(1, State.Defect);
			}
			if (!PageGestorConsultaCambioFamilia.checkFirstRowProductIsRight(dFTest.driver)) {
				listVals.add(2, State.Defect);
			}
          
			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }
	}

	
	
	public static void clickCambioFamiliaButton(DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep       (
				"Damos click al botón \"Cambio Familia\"", 
				"Muestra la página que permite gestionar los cambios de familia");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
		try {
			PageGestorConsultaCambioFamilia.clickCambioFamiliaButton(dFTest.driver);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		}
		finally { fmwkTest.grabStep(datosStep, dFTest); }

		String descripValidac = 
			"1) Aparece la tabla con las opciones para los cambios de familia<br>" + 
			"2) El botón \"Consulta\" ya no está \"disabled\"";
		datosStep.setNOKstateByDefault();
		ListResultValidation listVals = ListResultValidation.getNew(datosStep);
		try {
			if (!PageGestorConsultaCambioFamilia.isTablaCambioFamiliaVisible(dFTest.driver)) {
				listVals.add(1, State.Defect);
			}
			if (PageGestorConsultaCambioFamilia.isDisabledConsultaButton(dFTest.driver)) {
				listVals.add(2, State.Defect);
			}

			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }
	}
}
