package com.mng.robotest.tests.domains.transversal.acceptcookies.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.transversal.acceptcookies.pageobjects.SectionCookies;

import static com.github.jorge2m.testmaker.conf.State.*;

public class SectionCookiesSteps extends StepBase {

	private final SectionCookies sectionCookies = new SectionCookies();
	private final ModalSetCookiesSteps modalSetCookiesSteps = new ModalSetCookiesSteps();
	
	@Step (
		description="Seleccionamos el botón \"Aceptar\" de la sección inferior de configuración de cookies",
		expected="Desaparece la sección de configuración de cookies",
		saveErrorData=SaveWhen.Always)
	public void accept() {
		sectionCookies.accept();
		checkSectionInvisible();
	}
	
	@Step (
		description="Seleccionamos el botón \"Configurar cookies\" de la sección inferior de configuración de cookies",
		expected="Aparece el modal para la configuración de las cookies",
		saveHtmlPage=SaveWhen.IfProblem)
	public ModalSetCookiesSteps setCookies() {
		sectionCookies.setCookies();
		modalSetCookiesSteps.isVisible(2);
		return modalSetCookiesSteps;
	}
	
	@Validation (
		description="No es visible la sección inferior para la configuración de las cookies",
		level=Warn)
	public boolean checkSectionInvisible() {
		return (sectionCookies.isInvisible(2));
	}
	
}
