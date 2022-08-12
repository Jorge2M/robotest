package com.mng.robotest.domains.registro.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroFinOutlet;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.steps.shop.menus.SecMenusUserSteps;


public class PageRegistroFinStepsOutlet {
	
	private final PageRegistroFinOutlet pageRegistroFin;
	private final DataCtxShop dataTest;
	
	public PageRegistroFinStepsOutlet(DataCtxShop dataTest) {
		this.pageRegistroFin = new PageRegistroFinOutlet();
		this.dataTest = dataTest;
	}
	
	@Validation(
		description="Aparece la página final del proceso de registro (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	public boolean isPageUntil(int maxSeconds) {
		return (pageRegistroFin.isPageUntil(maxSeconds));
	}
	
	@Step (
		description="Seleccionar el botón \"Ir de shopping\" y finalmente el icono de Mango", 
		expected="Se accede a la shop correctamente")
	public void clickIrDeShoppingButton() {
		pageRegistroFin.clickIrDeShopping();
		SecCabecera.getNew(dataTest.channel, dataTest.appE).clickLogoMango();
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
			SecCabecera.getNew(dataTest.channel, dataTest.appE)
				.validaLogoMangoGoesToIdioma(dataTest.idioma), State.Warn);
		return checks;		
	}
}