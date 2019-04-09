package com.mng.robotest.test80.mango.test.stpv.manto;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorConsultaCambioFamilia;

public class PageGestorConsultaCambioFamiliaStpV {

	@Validation
	public static ChecksResult validateIsPage(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Estamos en la página " + PageGestorConsultaCambioFamilia.titulo + "<br>",
			PageGestorConsultaCambioFamilia.isPage(driver), State.Defect);
	 	validations.add(
			"Aparece la tabla de \"Consulta\"",
			PageGestorConsultaCambioFamilia.isVisibleConsultaTable(driver), State.Defect);
	 	validations.add(
			"El botón \"Consulta está\" \"disabled\"",
			PageGestorConsultaCambioFamilia.isDisabledConsultaButton(driver), State.Defect);
		return validations;
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
		finally { StepAspect.storeDataAfterStep(datosStep); }

		String descripValidac = 
			"1) Aparece la tabla con los productos<br>" +
			"2) El campo de la tabla \"Traducción familia principal\" de la primera fila contiene el atributo \"Accesorios\"";
		datosStep.setNOKstateByDefault();
		ChecksResult listVals = ChecksResult.getNew(datosStep);
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

	@Step (
		description="Damos click al botón <b>Cambio Familia</b>",
		expected="Muestra la página que permite gestionar los cambios de familia",
		saveErrorPage=SaveWhen.Never)
	public static void clickCambioFamiliaButton(WebDriver driver) throws Exception {
		PageGestorConsultaCambioFamilia.clickCambioFamiliaButton(driver);
		checkAfeterClickCambioFamilia(driver);
	}
	
	@Validation
	private static ChecksResult checkAfeterClickCambioFamilia(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		validations.add(
			"Aparece la tabla con las opciones para los cambios de familia<br>",
			PageGestorConsultaCambioFamilia.isTablaCambioFamiliaVisible(driver), State.Defect);
		validations.add(
			"El botón \"Consulta\" ya no está \"disabled\"",
			!PageGestorConsultaCambioFamilia.isDisabledConsultaButton(driver), State.Defect);
		return validations;
	}
}
