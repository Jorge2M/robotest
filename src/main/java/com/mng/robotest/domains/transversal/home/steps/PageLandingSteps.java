package com.mng.robotest.domains.transversal.home.steps;

import static com.github.jorge2m.testmaker.conf.State.Warn;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.transversal.home.pageobjects.PageLanding;
import com.mng.robotest.domains.transversal.menus.steps.MenuSteps;
import com.mng.robotest.test.beans.Pais;

public class PageLandingSteps extends StepBase {

	private final PageLanding pageLanding;
	
	public PageLandingSteps() {
		pageLanding = new PageLanding();
	}
	
	@Validation (description="Aparece la página de Landing (la esperamos hasta #{seconds} segundos)")
	public boolean checkIsPage(int seconds) {
		return (pageLanding.isPageUntil(seconds));
	}
	
	public void validateIsPageWithCorrectLineas() {
		checkMainContentPais(dataTest.getPais());
		validateIsPageOk();
		if (!channel.isDevice()) {
			new MenuSteps().checkLineasCountry();
		}
	}

	@Validation (
		description="Aparece el div de contenido asociado al país #{pais.getNombre_pais()} (#{pais.getCodigo_pais()})",
		level=Warn)
	public boolean checkMainContentPais(Pais pais) {
		return pageLanding.isPresentMainContent();
	}
	
	@Validation
	public ChecksTM validateIsPageOk() {
		var checks = ChecksTM.getNew();
		if (app!=AppEcom.outlet) {
			checks.add(
				"Aparece la home de marcas/multimarcas según el país",
				pageLanding.isPageDependingCountry(), Warn);	
		}
		checks.add(
			"No aparece ningún tag de error",
			!state(Present, By.xpath("//error"), driver).check(), Warn);
		return checks;
	}	
	
	@Validation (
		description="No es visible el comms header banner de Loyalty")
	public boolean isInvisibleCommsHeaderBannerLoyalty(int seconds) {
		return !pageLanding.isVisibleCommsHeaderBannerLoyalty(seconds);
	}	

	@Validation (
		description="No es visible ningún elemento de Loyalty")
	public boolean isInvisibleAnyElementLoyalty() {
		return !pageLanding.isVisibleAnyElementLoyalty();
	}	

}
