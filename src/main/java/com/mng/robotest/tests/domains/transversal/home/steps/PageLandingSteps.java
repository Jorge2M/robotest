package com.mng.robotest.tests.domains.transversal.home.steps;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.menus.steps.MenuSteps;
import com.mng.robotest.tests.domains.transversal.home.pageobjects.PageLanding;
import com.mng.robotest.testslegacy.beans.Pais;

public class PageLandingSteps extends StepBase {

	private final PageLanding pageLanding;
	
	public PageLandingSteps() {
		pageLanding = new PageLanding();
	}
	
	@Validation (description="Aparece la página de Landing <b>Multimarca</b> " + SECONDS_WAIT)
	public boolean checkIsPageMultimarca(int seconds) {
		return pageLanding.isPageMultimarca(seconds);
	}
	
	public void checkIsCountryWithCorrectLineas(int seconds) {
		if (isOutlet()) {
			checkUrlOfCountry(dataTest.getPais(), seconds);
		} else {
			checkMainContentPais(dataTest.getPais(), seconds);
		}
		checkIsPageOk();
		if (!channel.isDevice()) {
			new MenuSteps().checkLineasCountry();
		}
	}

	@Validation (
		description="Aparece el div de contenido asociado al país #{pais.getNombrePais()} (#{pais.getCodigoPais()}) " + SECONDS_WAIT)
	public boolean checkMainContentPais(Pais pais, int seconds) {
		return pageLanding.isPresentMainContent(seconds);
	}
	
	@Validation (
		description="En la URL figura <b>/#{pais.getCodigoPrehome().toLowerCase()}</b> " + SECONDS_WAIT)
	public boolean checkUrlOfCountry(Pais pais, int seconds) {
		return isTextInURL("/" + pais.getCodigoPrehome().toLowerCase(), seconds);
	}
	
	@Validation
	public ChecksTM checkIsPageOk() {
		var checks = ChecksTM.getNew();
		if (!isOutlet()) {
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
