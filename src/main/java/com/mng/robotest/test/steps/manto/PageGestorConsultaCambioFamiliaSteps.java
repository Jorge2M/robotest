package com.mng.robotest.test.steps.manto;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.manto.PageGestorConsultaCambioFamilia;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;

public class PageGestorConsultaCambioFamiliaSteps {

	@Validation
	public static ChecksTM validateIsPage(WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Estamos en la página " + PageGestorConsultaCambioFamilia.TITULO,
			PageGestorConsultaCambioFamilia.isPage(driver), State.Defect);
	 	
	 	checks.add(
			"Aparece la tabla de \"Consulta\"",
			PageGestorConsultaCambioFamilia.isVisibleConsultaTable(driver), State.Defect);
	 	
	 	checks.add(
			"El botón \"Consulta está\" \"disabled\"",
			PageGestorConsultaCambioFamilia.isDisabledConsultaButton(driver), State.Defect);
	 	
		return checks;
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
	private static ChecksTM checkAfterSearchProductXfamilia(WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la tabla con los productos",
			PageGestorConsultaCambioFamilia.isTablaProductosVisible(driver), State.Defect);
		checks.add(
			"El campo de la tabla \"Traducción familia principal\" de la primera fila contiene el atributo \"Accesorios\"",
			PageGestorConsultaCambioFamilia.checkFirstRowProductIsRight(driver), State.Defect);
		return checks;
	}

	@Step (
		description="Damos click al botón <b>Cambio Familia</b>",
		expected="Muestra la página que permite gestionar los cambios de familia",
		saveErrorData=SaveWhen.Never)
	public static void clickCambioFamiliaButton(WebDriver driver) {
		PageGestorConsultaCambioFamilia.clickCambioFamiliaButton(driver);
		checkAfeterClickCambioFamilia(driver);
	}
	
	@Validation
	private static ChecksTM checkAfeterClickCambioFamilia(WebDriver driver) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la tabla con las opciones para los cambios de familia",
			PageGestorConsultaCambioFamilia.isTablaCambioFamiliaVisible(driver), State.Defect);
		checks.add(
			"El botón \"Consulta\" ya no está \"disabled\"",
			!PageGestorConsultaCambioFamilia.isDisabledConsultaButton(driver), State.Defect);
		return checks;
	}
}
