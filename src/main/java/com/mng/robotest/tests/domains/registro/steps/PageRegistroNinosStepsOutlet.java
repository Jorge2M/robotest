package com.mng.robotest.tests.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.registro.beans.ListDataNinos;
import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroNinosOutlet;

public class PageRegistroNinosStepsOutlet extends StepBase {
	
	private final PageRegistroNinosOutlet pageRegistroNinos = new PageRegistroNinosOutlet();
	
	@Validation
	public ChecksTM validaIsPageWithNinos(int numNinos) {
		var checks = ChecksTM.getNew();
		int seconds = 5;
		checks.add(
			"Aparece la página de introducción de datos del niño " + getLitSecondsWait(seconds),
			pageRegistroNinos.isPageUntil(seconds));
		checks.add(
			"Aparecen inputs para introducir <b>" + numNinos + "</b>",
			pageRegistroNinos.getNumInputsNameNino()==numNinos);
		return checks;		
	}
	
	@Step (
		description="Introducir datos de los niños: <br>#{listaNinos.getFormattedHTMLData()}<br> y finalmente pulsar el botón \"Continuar\"", 
		expected="Aparece la página de introducción de datos de la dirección")
	public void sendNinoDataAndContinue(ListDataNinos listaNinos) {
		pageRegistroNinos.setDataNinoIfNotExists(listaNinos, 2);
		pageRegistroNinos.clickContinuar();
		new PageRegistroDirecStepsOutlet().isPageFromPais();
		checksDefault();
	}
}
