package com.mng.robotest.test80.mango.test.stpv.manto;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.testmaker.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageGestorConsultaCambioFamilia;

public class PageGestorConsultaCambioFamiliaStpV {

	@Validation
	public static ChecksResult validateIsPage(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Estamos en la página " + PageGestorConsultaCambioFamilia.titulo,
			PageGestorConsultaCambioFamilia.isPage(driver), State.Defect);
	 	validations.add(
			"Aparece la tabla de \"Consulta\"",
			PageGestorConsultaCambioFamilia.isVisibleConsultaTable(driver), State.Defect);
	 	validations.add(
			"El botón \"Consulta está\" \"disabled\"",
			PageGestorConsultaCambioFamilia.isDisabledConsultaButton(driver), State.Defect);
		return validations;
	}
	
	@Step (
		description="Buscamos productos por la familia <b>Accesorios</b>",
		expected="Muestra la tabla con productos que corresponden con esta familia",
		saveErrorData=SaveWhen.Never)
	public static void selectAccesoriosAndClickConsultaPorFamiliaButton(WebDriver driver) throws Exception {
		PageGestorConsultaCambioFamilia.selectAccesoriosAndClickConsultaPorFamiliaButton(driver);
		checkAfterSearchProductXfamilia(driver);
	}
	
	@Validation
	private static ChecksResult checkAfterSearchProductXfamilia(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		validations.add(
			"Aparece la tabla con los productos",
			PageGestorConsultaCambioFamilia.isTablaProductosVisible(driver), State.Defect);
		validations.add(
			"El campo de la tabla \"Traducción familia principal\" de la primera fila contiene el atributo \"Accesorios\"",
			PageGestorConsultaCambioFamilia.checkFirstRowProductIsRight(driver), State.Defect);
		return validations;
	}

	@Step (
		description="Damos click al botón <b>Cambio Familia</b>",
		expected="Muestra la página que permite gestionar los cambios de familia",
		saveErrorData=SaveWhen.Never)
	public static void clickCambioFamiliaButton(WebDriver driver) throws Exception {
		PageGestorConsultaCambioFamilia.clickCambioFamiliaButton(driver);
		checkAfeterClickCambioFamilia(driver);
	}
	
	@Validation
	private static ChecksResult checkAfeterClickCambioFamilia(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		validations.add(
			"Aparece la tabla con las opciones para los cambios de familia",
			PageGestorConsultaCambioFamilia.isTablaCambioFamiliaVisible(driver), State.Defect);
		validations.add(
			"El botón \"Consulta\" ya no está \"disabled\"",
			!PageGestorConsultaCambioFamilia.isDisabledConsultaButton(driver), State.Defect);
		return validations;
	}
}
