package com.mng.robotest.tests.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroFinOutlet;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabeceraMostFrequent;
import com.mng.robotest.tests.domains.transversal.menus.steps.SecMenusUserSteps;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageRegistroFinStepsOutlet extends StepBase {
	
	private final PageRegistroFinOutlet pageRegistroFin = new PageRegistroFinOutlet();
	
	@Validation(
		description="Aparece la página final del proceso de registro " + SECONDS_WAIT,
		level=Warn)
	public boolean isPageUntil(int seconds) {
		return pageRegistroFin.isPageUntil(seconds);
	}
	
	@Step (
		description="Seleccionar el botón \"Ir de shopping\" y finalmente el icono de Mango", 
		expected="Se accede a la shop correctamente")
	public void clickIrDeShoppingButton() {
		pageRegistroFin.clickIrDeShopping();
		new SecCabeceraMostFrequent().clickLogoMango();
		validateWeAreLogged();
	}
	
	public void validateWeAreLogged() {
		validateLogoGoesToPaisIdioma();
		new SecMenusUserSteps().checkIsVisibleLinkCerrarSesion();
	}
	
	@Validation
	public ChecksTM validateLogoGoesToPaisIdioma() {
		var checks = ChecksTM.getNew();
		checks.add(
			"El logo de Mango redirige al país/idioma origen: " + dataTest.getIdioma().getAcceso(),
			new SecCabeceraMostFrequent()
				.validaLogoMangoGoesToIdioma(dataTest.getIdioma()), Warn);
		return checks;		
	}
}