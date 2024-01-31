package com.mng.robotest.tests.domains.changecountry.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.changecountry.pageobjects.ModalChangeCountry;
import com.mng.robotest.tests.domains.transversal.home.steps.PageLandingSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

public class ModalChangeCountrySteps extends StepBase {
	
	private final ModalChangeCountry modalChangeCountry = ModalChangeCountry.make(app);
	
	@Validation (
		description="Aparece el modal de selección de país " + SECONDS_WAIT)
	public boolean checkIsVisible(int seconds) {
		return modalChangeCountry.isVisible(seconds);
	}
	
	@Step (
		description="Cambiamos al país <b>#{pais.getNombrePais()} (#{idioma.getCodigo().getLiteral()})</b>", 
		expected="Se accede a la shop de ese país / idioma")
	public void changeCountry(Pais pais, IdiomaPais idioma) {
		modalChangeCountry.changeToCountry(pais, idioma);
		new PageLandingSteps().checkIsCountryWithCorrectLineas(5);
	}
	
}
