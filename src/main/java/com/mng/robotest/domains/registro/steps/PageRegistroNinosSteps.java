package com.mng.robotest.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import java.util.Arrays;

import com.mng.robotest.domains.registro.pageobjects.PageRegistroNinos;
import com.mng.robotest.domains.registro.pageobjects.beans.ListDataNinos;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;


public class PageRegistroNinosSteps {
	
	PageRegistroNinos pageRegistroNinos = new PageRegistroNinos();
	
	@Validation
	public ChecksTM validaIsPageWithNinos(int numNinos) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSecondsToWait = 5;
		checks.add(
			"Aparece la página de introducción de datos del niño (la esperamos un máximo de " + maxSecondsToWait + " segundos)",
			pageRegistroNinos.isPageUntil(maxSecondsToWait), State.Defect);
		checks.add(
			"Aparecen inputs para introducir <b>" + numNinos + "</b>",
			pageRegistroNinos.getNumInputsNameNino()==numNinos, State.Defect);
		return checks;		
	}
	
	@Step (
		description="Introducir datos de los niños: <br>#{listaNinos.getFormattedHTMLData()}<br> y finalmente pulsar el botón \"Continuar\"", 
		expected="Aparece la página de introducción de datos de la dirección")
	public void sendNinoDataAndContinue(ListDataNinos listaNinos, Pais pais) {
		pageRegistroNinos.setDataNinoIfNotExists(listaNinos, 2);
		pageRegistroNinos.clickContinuar();
		new PageRegistroDirecSteps().isPageFromPais(pais);
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.SEO, 
				GenericCheck.TextsTraduced,
				GenericCheck.Analitica)).checks(pageRegistroNinos.driver);
	}
}
