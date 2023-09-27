package com.mng.robotest.domains.bolsa.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.bolsa.pageobjects.PageIniciarSesionBolsaMobile;
import com.mng.robotest.domains.compra.steps.CheckoutSteps;

public class PageIniciarSesionBolsaMobileSteps extends StepBase {

	private final PageIniciarSesionBolsaMobile pageIdentificacionBolsa = new PageIniciarSesionBolsaMobile();
	
	@Validation(description="Se carga la página de identificación desde la bolsa " + SECONDS_WAIT)
	public boolean checkIsPage(int seconds) {
		return pageIdentificacionBolsa.isPage(seconds);
	}
	
	public void login() {
		login(dataTest.getUserConnected(), dataTest.getPasswordUser());
	}
	
	@Step (
		description="Nos identificamos con el usuario <b>#{user}</b> y password <b>#{password}</b>",
		expected="Aparece la página de checkout")
	public void login(String user, String password) {
		pageIdentificacionBolsa.login(user, password);
		new CheckoutSteps().validateIsFirstPage(true);
	}

}
