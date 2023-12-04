package com.mng.robotest.tests.domains.transversal.home.steps;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.transversal.home.pageobjects.PageLanding;
import com.mng.robotest.tests.domains.transversal.menus.steps.MenuSteps;
import com.mng.robotest.testslegacy.beans.Pais;

public class PageLandingSteps extends StepBase {

	private final PageLanding pageLanding;
	
	public PageLandingSteps() {
		pageLanding = new PageLanding();
	}
	
	@Validation (description="Aparece la página de Landing " + SECONDS_WAIT)
	public boolean checkIsPage(int seconds) {
		return pageLanding.isPage(seconds);
	}
	
	public void checkIsPageWithCorrectLineas() {
		checkMainContentPais(dataTest.getPais());
		checkIsPageOk();
		if (!channel.isDevice()) {
			new MenuSteps().checkLineasCountry();
		}
	}

	@Validation (
		description="Aparece el div de contenido asociado al país #{pais.getNombrePais()} (#{pais.getCodigoPais()})",
		level=WARN)
	public boolean checkMainContentPais(Pais pais) {
		return pageLanding.isPresentMainContent();
	}
	
	@Validation
	public ChecksTM checkIsPageOk() {
		var checks = ChecksTM.getNew();
		if (app!=AppEcom.outlet) {
			checks.add(
				"Aparece la home de marcas/multimarcas según el país",
				pageLanding.isPageDependingCountry(), WARN);	
		}
		checks.add(
			"No aparece ningún tag de error",
			!state(PRESENT, By.xpath("//error"), driver).check(), WARN);
		
		return checks;
	}	
	
	@Validation (description="No es visible el comms header banner de Loyalty")
	public boolean isInvisibleCommsHeaderBannerLoyalty(int seconds) {
		return !pageLanding.isVisibleCommsHeaderBannerLoyalty(seconds);
	}	

	@Validation (description="No es visible ningún elemento de Loyalty")
	public boolean isInvisibleAnyElementLoyalty() {
		return !pageLanding.isVisibleAnyElementLoyalty();
	}	

}
