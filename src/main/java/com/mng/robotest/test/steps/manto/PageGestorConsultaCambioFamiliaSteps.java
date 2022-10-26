package com.mng.robotest.test.steps.manto;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.manto.PageGestorConsultaCambioFamilia;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;

public class PageGestorConsultaCambioFamiliaSteps {

	private PageGestorConsultaCambioFamilia pageGestorConsultaCambioFamilia = new PageGestorConsultaCambioFamilia();
	
	@Validation
	public ChecksTM validateIsPage() {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Estamos en la página " + PageGestorConsultaCambioFamilia.TITULO,
			pageGestorConsultaCambioFamilia.isPage(), State.Defect);
	 	
	 	checks.add(
			"Aparece la tabla de \"Consulta\"",
			pageGestorConsultaCambioFamilia.isVisibleConsultaTable(), State.Defect);
	 	
	 	checks.add(
			"El botón \"Consulta está\" \"disabled\"",
			pageGestorConsultaCambioFamilia.isDisabledConsultaButton(), State.Defect);
	 	
		return checks;
	}
	
	@Step (
		description="Buscamos productos por la familia <b>Accesorios</b>",
		expected="Muestra la tabla con productos que corresponden con esta familia",
		saveErrorData=SaveWhen.Never)
	public void selectAccesoriosAndClickConsultaPorFamiliaButton() {
		pageGestorConsultaCambioFamilia.selectAccesoriosAndClickConsultaPorFamiliaButton();
		checkAfterSearchProductXfamilia();
	}
	
	@Validation
	private ChecksTM checkAfterSearchProductXfamilia() {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la tabla con los productos",
			pageGestorConsultaCambioFamilia.isTablaProductosVisible(), State.Defect);
		
		checks.add(
			"El campo de la tabla \"Traducción familia principal\" de la primera fila contiene el atributo \"Accesorios\"",
			pageGestorConsultaCambioFamilia.checkFirstRowProductIsRight(), State.Defect);
		
		return checks;
	}

	@Step (
		description="Damos click al botón <b>Cambio Familia</b>",
		expected="Muestra la página que permite gestionar los cambios de familia",
		saveErrorData=SaveWhen.Never)
	public void clickCambioFamiliaButton() {
		pageGestorConsultaCambioFamilia.clickCambioFamiliaButton();
		checkAfeterClickCambioFamilia();
	}
	
	@Validation
	private ChecksTM checkAfeterClickCambioFamilia() {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la tabla con las opciones para los cambios de familia",
			pageGestorConsultaCambioFamilia.isTablaCambioFamiliaVisible(), State.Defect);
		
		checks.add(
			"El botón \"Consulta\" ya no está \"disabled\"",
			!pageGestorConsultaCambioFamilia.isDisabledConsultaButton(), State.Defect);
		
		return checks;
	}
}
