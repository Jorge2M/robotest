package com.mng.robotest.tests.domains.otros.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.landings.pageobjects.PageLanding;
import com.mng.robotest.tests.domains.otros.pageobjects.PageGoogle;
import com.mng.robotest.tests.domains.transversal.prehome.pageobjects.PagePrehome;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class GoogleSteps extends StepBase {

	private final PageGoogle pgGoogle = new PageGoogle();
	
	@Step (
		description="Accedemos a la URL de Google (http://www.google.es\") y buscamos \"MANGO\"", 
		expected="Aparecen los links de Mango con contenido correcto",
		saveHtmlPage=IF_PROBLEM)
	public void accessGoogleAndSearchMango() {
		pgGoogle.accessViaURL();
		pgGoogle.acceptModalCookieIfExists();
		pgGoogle.searchTextAndWait("MANGO");
		checkLinksMango();
	}
	
	@Validation
	private ChecksTM checkLinksMango() {
		var checks = ChecksTM.getNew();
		int seconds = 3;
		checks.add(
			"El 1er link no-anuncio contiene \"MANGO\" " + getLitSecondsWait(seconds),
			pgGoogle.validaFirstLinkContainsUntil("Mango", seconds));	
		
		checks.add(
			"El 1er link no-anuncion no contiene \"robots.txt\"",
			!pgGoogle.validaFirstLinkContainsUntil("robots.txt", 0), WARN);
		
		return checks;
	}

	@Step (
		description="Seleccionamos el 1er link normal (sin publicidad)", 
		expected="Aparece la página inicial de la shop de Mango")
	public void selectFirstLinkSinPublicidad() { 
		pgGoogle.clickFirstLinkNoPubli();
		checkInitialPageShop();
	}
	
	@Validation (description="Aparece la página de <b>Landing</b> o <b>Prehome</b>")
	private boolean checkInitialPageShop() {
		boolean isPageLanding = (new PageLanding()).isLandingMultimarca();
		boolean isPagePrehome = new PagePrehome().isPage();
		return (isPageLanding || isPagePrehome);
	}
}
