package com.mng.robotest.domains.otros.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.otros.pageobjects.PageGoogle;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test.pageobject.shop.landing.PageLanding;

public class GoogleSteps extends PageBase {

	private final PageGoogle pageGoogle = new PageGoogle();
	
	@Step (
		description="Accedemos a la URL de Google (http://www.google.es\") y buscamos \"MANGO\"", 
		expected="Aparecen los links de Mango con contenido correcto",
		saveHtmlPage=SaveWhen.IfProblem)
	public void accessGoogleAndSearchMango() {
		pageGoogle.accessViaURL();
		pageGoogle.acceptModalCookieIfExists();
		pageGoogle.searchTextAndWait("MANGO");
		checkLinksMango();
	}
	
	@Validation
	private ChecksTM checkLinksMango() {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 3;
		checks.add(
			"El 1er link no-anuncio contiene \"MANGO\" (lo esperamos " + seconds + " segundos)",
			pageGoogle.validaFirstLinkContainsUntil("Mango", seconds), State.Defect);	
		
		checks.add(
			"El 1er link no-anuncion no contiene \"robots.txt\"",
			!pageGoogle.validaFirstLinkContainsUntil("robots.txt", 0), State.Warn);
		
		return checks;
	}

	@Step (
		description="Seleccionamos el 1er link normal (sin publicidad)", 
		expected="Aparece la página inicial de la shop de Mango")
	public void selectFirstLinkSinPublicidad() { 
		pageGoogle.clickFirstLinkNoPubli();
		checkInitialPageShop();
	}
	
	@Validation (
		description="Aparece la página de <b>Landing</b> o <b>Prehome</b>",
		level=State.Defect)	
	private boolean checkInitialPageShop() {
		boolean isPageLanding = (new PageLanding()).isPage();
		boolean isPagePrehome = new PagePrehome().isPage();
		return (isPageLanding || isPagePrehome);
	}
}
