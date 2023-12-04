package com.mng.robotest.tests.domains.otros.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.testslegacy.pageobject.shop.PageIniShopJapon;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageIniShopJaponSteps extends StepBase {

	@Validation 
	public ChecksTM checkPageIniJapon(int seconds) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Estamos en la página inicial de la shop de Japón " + getLitSecondsWait(seconds) + ":<br>" +
			"   - El título es \"" + PageIniShopJapon.TITLE + "\"<br>" +		
			"   - La URL contiene \"" + PageIniShopJapon.URL + "\"",
			new PageIniShopJapon().isPage(seconds), WARN);
		
		return checks;
	}
}
