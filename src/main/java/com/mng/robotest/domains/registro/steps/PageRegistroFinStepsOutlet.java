package com.mng.robotest.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroFinOutlet;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.steps.shop.menus.SecMenusUserSteps;

public class PageRegistroFinStepsOutlet extends StepBase {
	
	private final PageRegistroFinOutlet pageRegistroFin = new PageRegistroFinOutlet();
	
	@Validation(
		description="Aparece la página final del proceso de registro (la esperamos hasta #{seconds} segundos)",
		level=State.Warn)
	public boolean isPageUntil(int seconds) {
		return (pageRegistroFin.isPageUntil(seconds));
	}
	
	@Step (
		description="Seleccionar el botón \"Ir de shopping\" y finalmente el icono de Mango", 
		expected="Se accede a la shop correctamente")
	public void clickIrDeShoppingButton() {
		pageRegistroFin.clickIrDeShopping();
		SecCabecera.getNew(channel, app).clickLogoMango();
		validateWeAreLogged();
	}
	
	public void validateWeAreLogged() {
		validateLogoGoesToPaisIdioma();
		SecMenusUserSteps secMenusUserSteps = new SecMenusUserSteps();
		secMenusUserSteps.checkIsVisibleLinkCerrarSesion();
	}
	
	@Validation
	public ChecksTM validateLogoGoesToPaisIdioma() {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"El logo de Mango redirige al país/idioma origen: " + dataTest.idioma.getAcceso(),
			SecCabecera.getNew(channel, app)
				.validaLogoMangoGoesToIdioma(dataTest.idioma), State.Warn);
		return checks;		
	}
}