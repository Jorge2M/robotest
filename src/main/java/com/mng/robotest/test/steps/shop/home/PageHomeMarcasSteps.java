package com.mng.robotest.test.steps.shop.home;

import org.openqa.selenium.By;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.transversal.menus.steps.MenuSteps;
import com.mng.robotest.test.pageobject.shop.PageHomeMarcas;
import com.mng.robotest.test.steps.shop.AllPagesSteps;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageHomeMarcasSteps extends StepBase {
	
	private final PageHomeMarcas pageHomeMarcas = new PageHomeMarcas();
	
	public enum TypeHome { MULTIMARCA, PORTADA_LINEA }

	public void validateIsPageWithCorrectLineas() {
		new AllPagesSteps().validateMainContentPais();
		validateIsPageOk();
		if (!channel.isDevice()) {
			new MenuSteps().checkLineasCountry();
		}
	}
	
	@Validation
	public ChecksTM validateIsPageOk() {
		var checks = ChecksTM.getNew();
		if (app!=AppEcom.outlet) {
			checks.add(
				"Aparece la home de marcas/multimarcas según el país",
				pageHomeMarcas.isHomeMarcasMultimarcasDependingCountry(), Warn);	
		}
		checks.add(
			"No aparece ningún tag de error",
			!state(Present, By.xpath("//error"), driver).check(), Warn);
		return checks;
	}
}
