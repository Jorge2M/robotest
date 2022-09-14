package com.mng.robotest.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.registro.beans.ListDataNinos;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroNinosOutlet;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;

public class PageRegistroNinosStepsOutlet extends StepBase {
	
	PageRegistroNinosOutlet pageRegistroNinos = new PageRegistroNinosOutlet();
	
	@Validation
	public ChecksTM validaIsPageWithNinos(int numNinos) {
		ChecksTM checks = ChecksTM.getNew();
		int secondsToWait = 5;
		checks.add(
			"Aparece la página de introducción de datos del niño (la esperamos un máximo de " + secondsToWait + " segundos)",
			pageRegistroNinos.isPageUntil(secondsToWait), State.Defect);
		checks.add(
			"Aparecen inputs para introducir <b>" + numNinos + "</b>",
			pageRegistroNinos.getNumInputsNameNino()==numNinos, State.Defect);
		return checks;		
	}
	
	@Step (
		description="Introducir datos de los niños: <br>#{listaNinos.getFormattedHTMLData()}<br> y finalmente pulsar el botón \"Continuar\"", 
		expected="Aparece la página de introducción de datos de la dirección")
	public void sendNinoDataAndContinue(ListDataNinos listaNinos) {
		pageRegistroNinos.setDataNinoIfNotExists(listaNinos, 2);
		pageRegistroNinos.clickContinuar();
		new PageRegistroDirecStepsOutlet().isPageFromPais();
		GenericChecks.checkDefault();
	}
}
